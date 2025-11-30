
package model;

public class ProgrammeStructure {
    private String programmeCode;
    private int year;
    private int semester;
    private String moduleCode;

    public ProgrammeStructure(String programmeCode, int year, int semester, String moduleCode) {
        this.programmeCode = programmeCode;
        this.year = year;
        this.semester = semester;
        this.moduleCode = moduleCode;
    }

    public String getProgrammeCode() { return programmeCode; }
    public int getYear() { return year; }
    public int getSemester() { return semester; }
    public String getModuleCode() { return moduleCode; }
}
