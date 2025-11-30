
package model;

/**
 * A scheduled class contains information about the module being taught,
 * the room in which it is held, the day and time of the session,
 */
public class ScheduledClass {
    private Module module;
    private Room room;
    private String day;
    private String time;
    private StudentGroup group;
    private String sessionType; // "lecture", "tutorial", "lab"
    private Lecturer lecturer;

    /**
     * Constructs a ScheduledClass
     * @param module the module being taught
     * @param room the room where the class takes place
     * @param day the day of the week
     * @param time the start time of the class
     * @param group student group attending
     * @param sessionType the type of session: lab, tutorial, lecture
     * @param lecturer lecturer teaching session
     */
    public ScheduledClass(Module module, Room room, String day, String time, StudentGroup group, String sessionType, Lecturer lecturer) {
        this.module = module;
        this.room = room;
        this.day = day;
        this.time = time;
        this.group = group;
        this.sessionType = sessionType;
        this.lecturer = lecturer;
    }

    /**
     * Getter method for the module for the class
     * @return the module
     */
    public Module getModule() { return module; }

    /**
     * Getter method for the room where the class is held
     * @return the room
     */
    public Room getRoom() { return room; }

    /**
     * The day of the week for this class
     * @return the day
     */
    public String getDay() { return day; }

    /**
     * Getter for the start time of this class
     * @return the time
     */
    public String getTime() { return time; }

    /**
     * Getter method for the student group assigned to this class
     * @return the student group
     */
    public StudentGroup getGroup() { return group; }

    /**
     * Getter method for type of the session: tut, lab, lec
     * @return the session type
     */
    public String getSessionType() { return sessionType; }

    /**
     *Getter method for the lecturer teaching this class
     * @return the lecturer
     */
    public Lecturer getLecturer() { return lecturer; }

    /**
     * Readable string of the scheduled class including all of its parameters
     * @return string describing this scheduled class
     */
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
