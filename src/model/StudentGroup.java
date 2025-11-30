package model;

/**
 * A group of students within a programme. The group has an ID
 * and is associated with a programme, year of study and number of students
 */
public class StudentGroup {
    private String groupId;   // e.g. "CS1A", "CS1B"
    private Programme programme;
    private int year;
    private int size;

    /**
     * Constructs a StudentGroup
     * @param groupId unique identifier for the group
     * @param programme the programme the group belongs to
     * @param year the year of study for the group
     * @param size the number of students in the group
     */
    public StudentGroup(String groupId, Programme programme, int year, int size) {
        this.groupId = groupId;
        this.programme = programme;
        this.year = year;
        this.size = size;
    }

    /**
     * Getter method for the identifier of the group
     * @return the groups id
     */
    public String getGroupId() { return groupId; }

    /**
     * Getter method for the programme of the group
     * @return the programme
     */
    public Programme getProgramme() { return programme; }

    /**
     * Getter method for the academic year of the group
     * @return the academic year
     */
    public int getYear() { return year; }

    /**
     * Getter method for the size of the group
     * @return the size of the group
     */
    public int getSize() { return size; }
}
