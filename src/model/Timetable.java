package model;

import java.util.ArrayList;
import java.util.List;

public class Timetable {

    private List<ScheduledClass> classes = new ArrayList<>();

    public Timetable() {}

    public Timetable(List<ScheduledClass> classes) {
        this.classes = classes;
    }

    public List<ScheduledClass> getClasses() {
        return classes;
    }

    public void addClass(ScheduledClass sc) {
        classes.add(sc);
    }

    /**
     * Checks if adding this class would cause a clash.
     * Clashes:
     *  - Same group at same day/time
     *  - Same lecturer at same day/time
     *  - Same room at same day/time
     */
    public boolean hasClash(ScheduledClass newClass) {
        for (ScheduledClass existing : classes) {

            boolean sameSlot =
                    existing.getDay().equals(newClass.getDay()) &&
                            existing.getTime().equals(newClass.getTime());

            if (!sameSlot) continue;

            // Room clash
            if (existing.getRoom().getRoomId().equals(newClass.getRoom().getRoomId())) {
                return true;
            }

            // Group clash
            if (existing.getGroup().getGroupId().equals(newClass.getGroup().getGroupId())) {
                return true;
            }

            // Lecturer clash (modules may have multiple lecturers)
            for (Lecturer lec1 : existing.getModule().getLecturers()) {
                for (Lecturer lec2 : newClass.getModule().getLecturers()) {
                    if (lec1.getId().equals(lec2.getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
