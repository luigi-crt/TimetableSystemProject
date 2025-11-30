package data;

import controller.ClashDetector;
import controller.TimetableGenerator;
import model.*;
import java.util.*;
import java.io.*;
import model.Module;

/**
 * Handles loading and saving all data from CSV files.
 * Uses CSVReader and CSVWriter for file operations.
 */

public class DataManager {
    private static final String DATA_PATH = "src/resources/";

    private List<Programme> programmes = new ArrayList<>();
    private List<Lecturer> lecturers = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Module> modules = new ArrayList<>();
    private List<StudentGroup> groups = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<ScheduledClass> scheduledClasses = new ArrayList<>();
    private List<ProgrammeStructure> programmeStructures = new ArrayList<>();

    // ---------------- Load All ----------------
    public void loadAll() {
        loadProgrammes();
        loadLecturers();
        loadRooms();
        loadModules();
        loadGroups();
        loadStudents();
        loadProgrammeStructures();
        loadScheduledClasses();
    }

    // ---------------- Loaders ----------------
    private void loadProgrammes() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "programmes.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            programmes.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                if (cols.length < 2) continue;
                programmes.add(new Programme(cols[0].trim(), cols[1].trim()));
            }
        } catch (Exception e) {
            System.err.println("Error loading programmes: " + e.getMessage());
        }
    }

    private void loadLecturers() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "lecturers.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            lecturers.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                if (cols.length < 3) continue;
                String id = cols[0].trim();
                String name = cols[1].trim();
                Programme p = getProgramme(cols[2].trim());
                lecturers.add(new Lecturer(id, name, p));
            }
        } catch (Exception e) {
            System.err.println("Error loading lecturers: " + e.getMessage());
        }
    }

    private void loadRooms() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "rooms.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            rooms.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                if (cols.length < 3) continue;
                String id = cols[0].trim();
                int capacity = Integer.parseInt(cols[1].trim());
                boolean isLab = cols[2].trim().equalsIgnoreCase("lab");
                rooms.add(new Room(id, capacity, isLab));
            }
        } catch (Exception e) {
            System.err.println("Error loading rooms: " + e.getMessage());
        }
    }

    private void loadModules() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "modules.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            modules.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                if (cols.length < 5) continue;
                String code = cols[0].trim();
                int lecHours = Integer.parseInt(cols[1].trim());
                int tutHours = Integer.parseInt(cols[2].trim());
                int labHours = Integer.parseInt(cols[3].trim());
                String[] lecturerIds = cols[4].trim().split(";");
                List<Lecturer> moduleLecturers = new ArrayList<>();
                for (String id : lecturerIds) {
                    Lecturer lec = getLecturer(id.trim());
                    if (lec != null) moduleLecturers.add(lec);
                }
                modules.add(new Module(code, lecHours, labHours, tutHours, moduleLecturers));
            }
        } catch (Exception e) {
            System.err.println("Error loading modules: " + e.getMessage());
        }
    }

    private void loadGroups() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "student_groups.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            groups.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                if (cols.length < 4) continue;
                String id = cols[0].trim();
                Programme p = getProgramme(cols[1].trim());
                int year = Integer.parseInt(cols[2].trim());
                int size = Integer.parseInt(cols[3].trim());
                groups.add(new StudentGroup(id, p, year, size));
            }
        } catch (Exception e) {
            System.err.println("Error loading student groups: " + e.getMessage());
        }
    }

    private void loadStudents() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "students.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            students.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                if (cols.length < 5) continue;
                String id = cols[0].trim();
                String name = cols[1].trim();
                Programme p = getProgramme(cols[2].trim());
                int year = Integer.parseInt(cols[3].trim());
                StudentGroup group = getGroup(cols[4].trim());
                students.add(new Student(id, name, p, year, group));
            }
        } catch (Exception e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
    }

    private void loadProgrammeStructures() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "programme_structure.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            programmeStructures.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                if (cols.length < 4) continue;
                String programmeCode = cols[0].trim();
                int year = Integer.parseInt(cols[1].trim());
                int semester = Integer.parseInt(cols[2].trim());
                String moduleCode = cols[3].trim();
                programmeStructures.add(new ProgrammeStructure(programmeCode, year, semester, moduleCode));
            }
        } catch (Exception e) {
            System.err.println("Error loading programme structure: " + e.getMessage());
        }
    }

    private void loadScheduledClasses() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "timetable.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            scheduledClasses.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                if (cols.length < 7) continue;
                Module m = getModule(cols[0].trim());
                Room r = getRoom(cols[1].trim());
                String day = cols[2].trim();
                String time = cols[3].trim();
                StudentGroup g = getGroup(cols[4].trim());
                String sessionType = cols[5].trim();
                Lecturer lecturer = getLecturer(cols[6].trim());
                scheduledClasses.add(new ScheduledClass(m, r, day, time, g, sessionType, lecturer));
            }
        } catch (Exception e) {
            System.err.println("Error loading timetable: " + e.getMessage());
        }
    }

    // ---------------- Save Timetable ----------------
    public void saveScheduledClasses() {
        try (CSVWriter writer = new CSVWriter(DATA_PATH + "timetable.csv")) {
            writer.writeRecord("Module,Room,Day,Time,Group,SessionType,LecturerID");
            List<String[]> rows = new ArrayList<>();
            for (ScheduledClass sc : scheduledClasses) {
                rows.add(new String[]{
                        sc.getModule().getCode(),
                        sc.getRoom().getRoomId(),
                        sc.getDay(),
                        sc.getTime(),
                        sc.getGroup().getGroupId(),
                        sc.getSessionType(),
                        sc.getLecturer().getId()
                });
            }
            writer.writeMatrix(rows);
        } catch (Exception e) {
            System.err.println("Error saving timetable: " + e.getMessage());
        }
    }

    // ---------------- Programme Structure Lookup ----------------
    public List<Module> getModulesForProgrammeYearSemester(String programmeCode, int year, int semester) {
        List<Module> result = new ArrayList<>();
        for (ProgrammeStructure ps : programmeStructures) {
            if (ps.getProgrammeCode().equals(programmeCode) &&
                    ps.getYear() == year &&
                    ps.getSemester() == semester) {
                Module m = getModule(ps.getModuleCode());
                if (m != null) result.add(m);
            }
        }
        return result;
    }

    // ---------------- Timetable Generation ----------------
    public void generateAndSaveTimetable(int semester) {
        TimetableGenerator generator = new TimetableGenerator(this, semester);
        List<ScheduledClass> generated = generator.generateTimetable();
        setScheduledClasses(generated);
        saveScheduledClasses();
    }

    // ---------------- Student & Lecturer Timetable Lookup ----------------
    public Student getStudent(String studentId) {
        for (Student s : students) {
            if (s.getId().equals(studentId)) return s;
        }
        return null;
    }

    public Lecturer getLecturer(String lecturerId) {
        for (Lecturer l : lecturers) {
            if (l.getId().equals(lecturerId)) return l;
        }
        return null;
    }

    public List<ScheduledClass> getTimetableForStudent(String studentId) {
        Student student = getStudent(studentId);
        if (student == null) return Collections.emptyList();
        StudentGroup group = student.getGroup();
        List<ScheduledClass> result = new ArrayList<>();
        for (ScheduledClass sc : scheduledClasses) {
            if (sc.getGroup().getGroupId().equals(group.getGroupId())) {
                result.add(sc);
            }
        }
        return result;
    }

    public List<ScheduledClass> getTimetableForLecturer(String lecturerId) {
        List<ScheduledClass> result = new ArrayList<>();
        for (ScheduledClass sc : scheduledClasses) {
            if (sc.getLecturer().getId().equals(lecturerId)) {
                result.add(sc);
            }
        }
        return result;
    }

    // ---------------- Getters & Setters ----------------
    public Programme getProgramme(String code) {
        for (Programme p : programmes) {
            if (p.getCode().equals(code)) return p;
        }
        return null;
    }

    public Room getRoom(String id) {
        for (Room r : rooms) {
            if (r.getRoomId().equals(id)) return r;
        }
        return null;
    }

    public Module getModule(String code) {
        for (Module m : modules) {
            if (m.getCode().equals(code)) return m;
        }
        return null;
    }

    public StudentGroup getGroup(String id) {
        for (StudentGroup g : groups) {
            if (g.getGroupId().equals(id)) return g;
        }
        return null;
    }

    public List<Module> getModules() { return modules; }
    public List<Room> getRooms() { return rooms; }
    public List<StudentGroup> getGroups() { return groups; }
    public List<Lecturer> getLecturers() { return lecturers; }
    public List<ScheduledClass> getScheduledClasses() { return scheduledClasses; }
    public void setScheduledClasses(List<ScheduledClass> classes) { this.scheduledClasses = classes; }
}

