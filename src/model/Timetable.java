
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a timetable containing scheduled classes.
 * Handles clash detection and filtering.
 */
public class Timetable {

    private final List<ScheduledClass> classes = new ArrayList<>();

    /**
     * Get all scheduled classes.
     */
    public List<ScheduledClass> getAll() {
        return classes;
    }

    /**
     * Attempts to add a class with clash detection.
     * @return true if added successfully, false if a clash exists.
     */
    public boolean addClass(ScheduledClass sc) {
        if (hasClash(sc)) {
            return false;
        }
        classes.add(sc);
        return true;
    }

    /**
     * Checks for clashes based on day, time, room, lecturer, and student group.
     */
    private boolean hasClash(ScheduledClass newClass) {
        for (ScheduledClass existing : classes) {
            // Only compare classes on the same day and time
            if (!existing.getDay().equalsIgnoreCase(newClass.getDay())) continue;
            if (!existing.getTime().equals(newClass.getTime())) continue;

            // Room clash
            if (existing.getRoom().getRoomId().equals(newClass.getRoom().getRoomId())) return true;

            // Lecturer clash
            boolean lecturerUsed = existing.getModule().getLecturers().stream()
                    .anyMatch(l -> newClass.getModule().getLecturers().contains(l));
            if (lecturerUsed) return true;

            // Student group clash
            if (existing.getGroup().getGroupId().equals(newClass.getGroup().getGroupId())) return true;
        }
        return false;
    }

    // ---------------- Filtering ----------------

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
