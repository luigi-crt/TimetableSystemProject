package model;

import java.util.List;

public class Programme {
    private String code;              // e.g. "CS", "ECE"
    private String name;              // e.g. "Computer Systems"
    private List<Module> yearModules; // modules for a given year (simplified)

    public Programme(String code, String name, List<Module> yearModules) {
        this.code = code;
        this.name = name;
        this.yearModules = yearModules;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public List<Module> getYearModules() { return yearModules; }
}
