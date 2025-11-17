package model;

import model.Module;
import java.util.ArrayList;
import java.util.List;

public class Timetable {

    private List<ScheduledClass> classes = new ArrayList<>();

    // Public getter
    public List<ScheduledClass> getAll() {
        return classes;
    }

    /**
     * Attempts to add a class with clash detection.
     * Returns true if added, false if a clash is found.
     */
    public boolean addClass(ScheduledClass sc) {
        if (hasClash(sc)) {
            return false;
        }
        classes.add(sc);
        return true;
    }

    /**
     * Check for clashes against existing classes.
     */
    private boolean hasClash(ScheduledClass newClass) {
        for (ScheduledClass existing : classes) {

            // Only compare classes on the same day
            if (!existing.getDay().equalsIgnoreCase(newClass.getDay()))
                continue;

            // If time matches → possible clash
            if (existing.getTime().equals(newClass.getTime())) {

                // 1. Room clash
                if (existing.getRoom().getRoomId().equals(newClass.getRoom().getRoomId()))
                    return true;

                // 2. Lecturer clash
                boolean lecturerUsed =
                        existing.getModule().getLecturers().stream()
                                .anyMatch(l -> newClass.getModule().getLecturers()
                                        .contains(l));
                if (lecturerUsed) return true;

                // 3. Student group clash
                if (existing.getGroup().getGroupId()
                        .equals(newClass.getGroup().getGroupId()))
                    return true;
            }
        }

        return false;
    }

    // Filtering --------------------------------------------------

    public List<ScheduledClass> getForStudentGroup(String groupId) {
        return classes.stream()
                .filter(c -> c.getGroup().getGroupId().equals(groupId))
                .toList();
    }

    public List<ScheduledClass> getForLecturer(String lecturerId) {
        return classes.stream()
                .filter(c -> c.getModule().getLecturers().stream()
                        .anyMatch(l -> l.getId().equals(lecturerId)))
                .toList();
    }

    public List<ScheduledClass> getForRoom(String roomId) {
        return classes.stream()
                .filter(c -> c.getRoom().getRoomId().equals(roomId))
                .toList();
    }
}
