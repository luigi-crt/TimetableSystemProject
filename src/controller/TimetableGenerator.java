package controller;

import model.*;
import data.DataManager;
import java.util.*;
import model.Module;

public class TimetableGenerator {
    private DataManager dataManager;
    private List<String> days = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri");
    private List<String> times = Arrays.asList("09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00");

    public TimetableGenerator(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<ScheduledClass> generateTimetable() {
        List<ScheduledClass> timetable = new ArrayList<>();
        Set<String> usedSlots = new HashSet<>(); // Format: day_time_room

        for (StudentGroup group : dataManager.getGroups()) {
            for (Module module : dataManager.getModules()) {
                // Assign lectures, labs, tutorials
                int totalSessions = module.getLectureHours() + module.getLabHours() + module.getTutorialHours();
                int sessionsScheduled = 0;

                for (String day : days) {
                    for (String time : times) {
                        if (sessionsScheduled >= totalSessions) break;

                        // Find a suitable room
                        for (Room room : dataManager.getRooms()) {
                            // Check if lab required and room is lab
                            if (module.requiresLab() && !room.isLab()) continue;

                            String slotKey = day + "_" + time + "_" + room.getRoomId();
                            if (usedSlots.contains(slotKey)) continue; // Room clash

                            // Check for lecturer clash
                            boolean lecturerBusy = false;
                            for (Lecturer lecturer : module.getLecturers()) {
                                for (ScheduledClass sc : timetable) {
                                    if (sc.getDay().equals(day) && sc.getTime().equals(time)) {
                                        if (sc.getModule().getLecturers().contains(lecturer)) {
                                            lecturerBusy = true;
                                            break;
                                        }
                                    }
                                }
                                if (lecturerBusy) break;
                            }
                            if (lecturerBusy) continue;

                            // Check for group clash
                            boolean groupBusy = false;
                            for (ScheduledClass sc : timetable) {
                                if (sc.getDay().equals(day) && sc.getTime().equals(time)) {
                                    if (sc.getGroup().equals(group)) {
                                        groupBusy = true;
                                        break;
                                    }
                                }
                            }
                            if (groupBusy) continue;

                            // All checks passed, schedule the class
                            ScheduledClass scheduled = new ScheduledClass(module, room, day, time, group);
                            timetable.add(scheduled);
                            usedSlots.add(slotKey);
                            sessionsScheduled++;
                            break; // Move to next time slot
                        }
                    }
                }
            }
        }
        return timetable;
    }
}
