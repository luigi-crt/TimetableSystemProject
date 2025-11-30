package model;

public class Lecturer {
    private String id;
    private String name;
    private Programme p;

    public Lecturer(String id, String name, Programme p) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Programme getProgramme() { return p; }
}
