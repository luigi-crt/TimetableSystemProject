
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

    public Module(String code, int lectureHours, int labHours, int tutorialHours, List<Lecturer> lecturers) {
        this.code = code;
        this.lectureHours = lectureHours;
        this.labHours = labHours;
        this.tutorialHours = tutorialHours;
        this.lecturers = lecturers;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public int getLectureHours() {
        return lectureHours;
    }

    public int getLabHours() {
        return labHours;
    }

    public int getTutorialHours() {
        return tutorialHours;
    }

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
