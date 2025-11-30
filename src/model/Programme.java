package model;

/**
 * Programme/Course in the University
 */
public class Programme {
    private String code;
    private String name;

    /**
     * Constructor for a programme
     * @param code course unique identifier e.g. CS, MA, etc
     * @param name course name e.g. Computer Systems, Economics, Engineering.
     */
    public Programme(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Getter method for the Code of the programme/course
     * @return programmes code
     */
    public String getCode() { return code; }

    /**
     * Getter method for the name of the programme
     * @return programme's name
     */
    public String getName() { return name; }
}
