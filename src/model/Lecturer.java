package model;

/**
 * Represents a lecturer in the university. They can appear in one or more modules.
 */
public class Lecturer {
    private String id;
    private String name;
    private Programme p;

    /**
     * Constructs a Lecturer
     * @param id the unique identifier of the lecturer
     * @param name the full name of the lecturer
     * @param p the programme the lecturer belongs to
     */
    public Lecturer(String id, String name, Programme p) {
        this.id = id;
        this.name = name;
        this.p = p;
    }

    /**
     * Getter method for the unique identifier of the lectuer
     * @return the lecturer's id
     */
    public String getId() { return id; }

    /**
     * Getter method for the name of the lecturer
     * @return the lecturers full name
     */
    public String getName() { return name; }

    /**
     * Getter method for the programme of the lecturer
     * @return the lecturer's programme
     */
    public Programme getProgramme() { return p; }
}
