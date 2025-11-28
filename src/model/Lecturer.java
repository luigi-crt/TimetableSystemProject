package model;

public class Lecturer {
    private String id;
    private String name;

    // 1. ADD THIS FIELD
    private Programme programme;

    // 2. MODIFY CONSTRUCTOR TO ACCEPT PROGRAMME
    public Lecturer(String id, String name, Programme programme) {
        this.id = id;
        this.name = name;
        this.programme = programme;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    // 3. ADD THIS GETTER METHOD
    public Programme getProgramme() {
        return programme;
    }
}
