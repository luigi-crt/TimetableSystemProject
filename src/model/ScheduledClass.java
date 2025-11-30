
package model;

public class ScheduledClass {
    private Module module;
    private Room room;
    private String day; // e.g. "Mon"
    private String time; // e.g. "10:00"
    private StudentGroup group;
    private String sessionType; // "lecture", "tutorial", "lab"
    private Lecturer lecturer;  // Assigned lecturer for this session

    public ScheduledClass(Module module, Room room, String day, String time, StudentGroup group, String sessionType, Lecturer lecturer) {
        this.module = module;
        this.room = room;
        this.day = day;
        this.time = time;
        this.group = group;
        this.sessionType = sessionType;
        this.lecturer = lecturer;
    }

    // Getters
    public Module getModule() { return module; }
    public Room getRoom() { return room; }
    public String getDay() { return day; }
    public String getTime() { return time; }
    public StudentGroup getGroup() { return group; }
    public String getSessionType() { return sessionType; }
    public Lecturer getLecturer() { return lecturer; }

    @Override
    public String toString() {
        return day + " " + time +
                " | Room: " + room.getRoomId() +
                " | Group: " + group.getGroupId() +
                " | Module: " + module.getCode() +
                " | Type: " + sessionType +
                " | Lecturer: " + (lecturer != null ? lecturer.getName() : "N/A");
    }
}
