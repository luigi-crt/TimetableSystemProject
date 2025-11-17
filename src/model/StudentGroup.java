package model;

public class StudentGroup {
    private String groupId;   // e.g. "CS1A", "CS1B"
    private Programme programme;
    private int year;
    private int size;

    public StudentGroup(String groupId, Programme programme, int year, int size) {
        this.groupId = groupId;
        this.programme = programme;
        this.year = year;
        this.size = size;
    }

    public String getGroupId() { return groupId; }
    public Programme getProgramme() { return programme; }
    public int getYear() { return year; }
    public int getSize() { return size; }
}
