package model;

public class Programme {
    private String code;              // e.g. "CS", "ECE"
    private String name;              // e.g. "Computer Systems"

    public Programme(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
}
