package data;

import model.*;
import java.util.*;

public class Scheduler {

    private DataManager data;

    private static final String[] DAYS = {"Mon", "Tue", "Wed", "Thu", "Fri"};
    private static final String[] TIMES = {"09:00", "10:00", "11:00", "12:00",
            "14:00", "15:00", "16:00"};

    public Scheduler(DataManager data) {
        this.data = data;
    }

    /**
     * Generates a timetable for all groups and returns the completed list.
     */
    public List<ScheduledClass> generateTimetable() {

        Timetable timetable = new Timetable();

        List<Room> rooms = data.getRooms();
        List<StudentGroup> groups = data.getGroups();

        // For every group, assign their modules
        for (StudentGroup g : groups) {

            // Filter modules belonging to this programme + year
            List<Module> modules = data.getModules().stream()
                    .filter(m -> g.getProgramme().equals(m.getLecturers().get(0).getProgramme()))
                    .toList();

            for (Module m : modules) {

                // TOTAL SESSIONS REQUIRED
                int totalSessions = m.getLectureHours()
                        + m.getLabHours()
                        + m.getTutorialHours();

                for (int i = 0; i < totalSessions; i++) {

                    ScheduledClass placed = tryPlace(m, g, rooms, timetable);

                    if (placed != null) {
                        timetable.addClass(placed);
                    } else {
                        System.out.println("Could not place class for module " + m.getCode());
                    }
                }
            }
        }

        return timetable.getClasses();
    }

    private ScheduledClass tryPlace(Module m, StudentGroup g,
                                    List<Room> rooms, Timetable timetable) {

        for (String day : DAYS) {
            for (String time : TIMES) {

                for (Room r : rooms) {
                    ScheduledClass sc = new ScheduledClass(m, r, day, time, g);

                    if (!timetable.hasClash(sc)) {
                        return sc;
                    }
                }
            }
        }
        return null; // no valid slot
    }
}
