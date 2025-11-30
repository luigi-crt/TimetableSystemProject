package model;

/**
 * A student in the Unversity
 */
public class Student {
    private String id;
    private String name;
    private Programme programme;
    private int year;                 // e.g. 1, 2, 3, 4
    private StudentGroup group;       // assigned group

    /**
     * Constructor for the Student
     * @param id the identifier of the student
     * @param name the full name of the student
     * @param programme the programme of the student
     * @param year the academic year the student is in
     * @param group the student group the student is part of
     */
    public Student(String id, String name, Programme programme, int year, StudentGroup group) {
        this.id = id;
        this.name = name;
        this.programme = programme;
        this.year = year;
        this.group = group;
    }

    /**
     * Getter method for the identifier of the student
     * @return the student's ID
     */
    public String getId() { return id; }

    /**
     * The getter method for the name of the student
     * @return the student's full name
     */
    public String getName() { return name; }

    /**
     * Getter method for the programme of the student
     * @return student's programme
     */
    public Programme getProgramme() { return programme; }

    /**
     * Getter method for the academic year of the student
     * @return student's academic year
     */
    public int getYear() { return year; }

    /**
     * Getter method for the student's group
     * @return the student's group
     */
    public StudentGroup getGroup() { return group; }
}
