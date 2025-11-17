package model;

public class Student {
    private String id;
    private String name;
    private Programme programme;
    private int year;                 // e.g. 1, 2, 3, 4
    private StudentGroup group;       // assigned group

    public Student(String id, String name, Programme programme, int year, StudentGroup group) {
        this.id = id;
        this.name = name;
        this.programme = programme;
        this.year = year;
        this.group = group;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Programme getProgramme() { return programme; }
    public int getYear() { return year; }
    public StudentGroup getGroup() { return group; }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }
}
