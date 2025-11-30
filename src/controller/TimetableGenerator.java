package controller;

import model.*;
import data.DataManager;
import java.util.*;
import model.Module;
import java.util.Collections;

/**
 * Generates a timetable for all student groups in a given semester.
 * Assigns modules to available time slots, rooms and lecturers while
 * checking for basic clashes.
 *
 * It iterates through all student groups and their modules for the semester.
 * Schedules lectures, tutorials and labs based on the hours required.
 * Randomises day and time in an attempt to distribute session over the week.
 * Checks for clashes
 */
public class TimetableGenerator {
    private DataManager dataManager;
    private List<String> days = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri");
    private List<String> times = Arrays.asList("09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00","17:00");
    private int semester;

    /**
     * Constructs a timetable generator with specified data
     * @param dataManager provides data to generate timetable
     * @param semester semster number which the timetable will be created for
     */
    public TimetableGenerator(DataManager dataManager, int semester) {
        this.dataManager = dataManager;
        this.semester = semester;
    }

    /**
     * Generates a timetable for all student groups for the specified semester.
     * The method:
     * - Gets the modules for each student group.
     * - Schedules lectures, tutorials, and labs according to module hours.
     * - Makes basic clash checks between rooms, groups and lecturers
     * @return list of ScheduledClass objects representing the timetable
     */
    public List<ScheduledClass> generateTimetable() {
        List<ScheduledClass> timetable = new ArrayList<>();
        Set<String> usedSlots = new HashSet<>();

        for (StudentGroup group : dataManager.getGroups()) {
            List<Module> modules = dataManager.getModulesForProgrammeYearSemester(
                    group.getProgramme().getCode(), group.getYear(), semester
            );
            for (Module module : modules) {
                List<Lecturer> lecturers = module.getLecturers();
                int lecturerIndex = 0;

                // Shuffle days and times for this module/group
                List<String> shuffledDays = new ArrayList<>(days);
                List<String> shuffledTimes = new ArrayList<>(times);
                Collections.shuffle(shuffledDays);
                Collections.shuffle(shuffledTimes);

                // Schedule lectures
                for (int i = 0; i < module.getLectureHours(); i++) {
                    ScheduledClass sc = scheduleSession(
                            module, group, "lecture", timetable, usedSlots,
                            shuffledDays, shuffledTimes, lecturers.get(lecturerIndex), false
                    );
                    if (sc != null) timetable.add(sc);
                    lecturerIndex = (lecturerIndex + 1) % lecturers.size();
                }
                // Schedule tutorials
                for (int i = 0; i < module.getTutorialHours(); i++) {
                    ScheduledClass sc = scheduleSession(
                            module, group, "tutorial", timetable, usedSlots,
                            shuffledDays, shuffledTimes, lecturers.get(lecturerIndex), false
                    );
                    if (sc != null) timetable.add(sc);
                    lecturerIndex = (lecturerIndex + 1) % lecturers.size();
                }
                // Schedule labs
                for (int i = 0; i < module.getLabHours(); i++) {
                    ScheduledClass sc = scheduleSession(
                            module, group, "lab", timetable, usedSlots,
                            shuffledDays, shuffledTimes, lecturers.get(lecturerIndex), true
                    );
                    if (sc != null) timetable.add(sc);
                    lecturerIndex = (lecturerIndex + 1) % lecturers.size();
                }
            }
        }
        return timetable;
    }

    /**
     * Helper method to schedule a session of a given type (lec, tut, lab)
     * for a group and module
     * @param module the module to schedule
     * @param group the student group attending the session
     * @param sessionType the type of the session
     * @param timetable the current timetable being built
     * @param usedSlots  a set of already used day/time/room slots
     * @param days the list of available days
     * @param times the list of available times
     * @param lecturer the lecturer assigned to teach
     * @param requireLab true if session requires lab room
     * @return ScheduledClass object
     */
    private ScheduledClass scheduleSession(
            Module module, StudentGroup group, String sessionType, List<ScheduledClass> timetable,
            Set<String> usedSlots, List<String> days, List<String> times, Lecturer lecturer, boolean requireLab
    ) {
        for (String day : days) {
            for (String time : times) {
                for (Room room : dataManager.getRooms()) {
                    // Room type check
                    if (requireLab && !room.isLab()) continue;
                    if (!requireLab && room.isLab()) continue; // Only use classrooms for non-labs

                    String slotKey = day + "_" + time + "_" + room.getRoomId();
                    if (usedSlots.contains(slotKey)) continue;

                    // Check for lecturer, group, and room clashes
                    boolean clash = false;
                    for (ScheduledClass sc : timetable) {
                        if (sc.getDay().equals(day) && sc.getTime().equals(time)) {
                            if (sc.getRoom().getRoomId().equals(room.getRoomId()) ||
                                    sc.getGroup().getGroupId().equals(group.getGroupId()) ||
                                    sc.getLecturer().getId().equals(lecturer.getId())) {
                                clash = true;
                                break;
                            }
                        }
                    }
                    if (clash) continue;

                    usedSlots.add(slotKey);
                    return new ScheduledClass(module, room, day, time, group, sessionType, lecturer);
                }
            }
        }
        return null;
    }
}

