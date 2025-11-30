
package model;

import java.util.List;

/**
 * Represents a module with lecture, lab, and tutorial requirements.
 */
public class Module {
    private String code;
    private int lectureHours;
    private int labHours;
    private int tutorialHours;
    private List<Lecturer> lecturers;

    /**
     * Constructs a Module in the university
     * @param code the unique identifier for the module
     * @param lectureHours required lecture hours for this module in a wekk
     * @param labHours required lab hours for the module in a week
     * @param tutorialHours required tutorial hours for the module in a week
     * @param lecturers lecturers that teach this module
     */
    public Module(String code, int lectureHours, int labHours, int tutorialHours, List<Lecturer> lecturers) {
        this.code = code;
        this.lectureHours = lectureHours;
        this.labHours = labHours;
        this.tutorialHours = tutorialHours;
        this.lecturers = lecturers;
    }

    /**
     * Getter method for the Code of the module
     * @return module's code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for the lecture hours in the module
     * @return lecture hours
     */
    public int getLectureHours() {
        return lectureHours;
    }
    /**
     * Getter method for the lab hours in the module
     * @return lab hours
     */
    public int getLabHours() {
        return labHours;
    }
    /**
     * Getter method for the tutorial hours in the module
     * @return tutorial hours
     */
    public int getTutorialHours() {
        return tutorialHours;
    }
    /**
     * Getter method for the lectures in the module
     * @return lectures
     */
    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    /**
     * Checks if this module requires a lab session.
     */
    public boolean requiresLab() {
        return labHours > 0;
    }

    @Override
    public String toString() {
        return code + " (Lectures: " + lectureHours + ", Labs: " + labHours + ", Tutorials: " + tutorialHours + ")";
    }
}
