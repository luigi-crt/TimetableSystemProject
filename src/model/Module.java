package model;

import java.util.List;

public class Module {
    private String code;
    private int lectureHours;
    private int labHours;
    private int tutorialHours;
    private List<Lecturer> lecturers;

    public Module(String code, int lecture, int lab, int tutorial,
                  List<Lecturer> lecturers) {
        this.code = code;
        this.lectureHours = lecture;
        this.labHours = lab;
        this.tutorialHours = tutorial;
        this.lecturers = lecturers;
    }

    public String getCode() { return code; }
}
