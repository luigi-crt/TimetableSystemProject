
package model;

/**
 * Porgramme structure specifies which module belongs to which programme, year of study and semester
 */
public class ProgrammeStructure {
    private String programmeCode;
    private int year;
    private int semester;
    private String moduleCode;

    /**
     * Constructs a ProgrammeStructure
     * @param programmeCode the code of the programme
     * @param year the year of study the module belongs to
     * @param semester which semester the module is taught
     * @param moduleCode the code of the module
     */
    public ProgrammeStructure(String programmeCode, int year, int semester, String moduleCode) {
        this.programmeCode = programmeCode;
        this.year = year;
        this.semester = semester;
        this.moduleCode = moduleCode;
    }

    /**
     * Getter method for the programme's code
     * @return the programme code
     */
    public String getProgrammeCode() { return programmeCode; }

    /**
     * Getter method for the year of study in the programme structure
     * @return the year of study
     */
    public int getYear() { return year; }

    /**
     * The semsester which the module is taught
     * @return the semester number
     */
    public int getSemester() { return semester; }

    /**
     * the module for this structure
     * @return the module code
     */
    public String getModuleCode() { return moduleCode; }
}
