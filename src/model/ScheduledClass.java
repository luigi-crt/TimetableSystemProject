package model;

public class ScheduledClass {
    private Module module;
    private Room room;
    private String day;       // Mon-Fri
    private String time;      // e.g. "10:00"
    private StudentGroup group;

    public ScheduledClass(Module module, Room room,
                          String day, String time,
                          StudentGroup group) {
        this.module = module;
        this.room = room;
        this.day = day;
        this.time = time;
        this.group = group;
    }
}
