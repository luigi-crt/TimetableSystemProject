package model;

import model.Module;
import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private List<ScheduledClass> scheduled = new ArrayList<>();

    public void addClass(ScheduledClass sc) {
        // Later: check room clashes, lecturer clashes, student clashes
        scheduled.add(sc);
    }

    public List<ScheduledClass> getClassesForLecturer(String lecturerId) {
        return scheduled.stream()
                .filter(sc -> sc.getModule().getLecturers()
                        .stream().anyMatch(l -> l.getId().equals(lecturerId)))
                .toList();
    }
}
