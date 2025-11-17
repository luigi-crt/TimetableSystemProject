package utility;

import model.Module;
import model.*;
import csv.*;

public class TimetableController {
    private Timetable timetable;

    public TimetableController(Timetable timetable) {
        this.timetable = timetable;
    }

    public void scheduleClass(Module module, Room room,
                              String day, String time,
                              StudentGroup group) {
        // Check: room free?
        // Check: lecturer free?
        // Check: students free?

        ScheduledClass sc = new ScheduledClass(module, room, day, time, group);
        timetable.addClass(sc);
    }
}
