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

    private List<Programme> programmes = new ArrayList<>();
    private List<Lecturer> lecturers = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Module> modules = new ArrayList<>();
    private List<StudentGroup> groups = new ArrayList<>();
    private List<ScheduledClass> scheduledClasses = new ArrayList<>();

    // ---------------- Load All ----------------
    public void loadAll() {
        loadProgrammes();
        loadLecturers();
        loadRooms();
        loadModules();
        loadGroups();
        loadScheduledClasses();
    }

    private static final String DATA_PATH = "src/resources/";

    // ---------------- Load Programmes ----------------
    private void loadProgrammes() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "programmes.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            programmes.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                programmes.add(new Programme(cols[0].trim(), cols[1].trim()));
            }
        } catch (Exception e) {
            System.err.println("Error loading programmes: " + e.getMessage());
        }
    }

    // ---------------- Load Lecturers ----------------
    private void loadLecturers() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "lecturers.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            lecturers.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                String id = cols[0].trim();
                String name = cols[1].trim();
                lecturers.add(new Lecturer(id, name, null));
            }
        } catch (Exception e) {
            System.err.println("Error loading lecturers: " + e.getMessage());
        }
    }

    // ---------------- Load Rooms ----------------
    private void loadRooms() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "rooms.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            rooms.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                String id = cols[0].trim();
                int capacity = Integer.parseInt(cols[1].trim());
                boolean isLab = cols[2].trim().equalsIgnoreCase("lab");
                rooms.add(new Room(id, capacity, isLab));
            }
        } catch (Exception e) {
            System.err.println("Error loading rooms: " + e.getMessage());
        }
    }

    // ---------------- Load Modules ----------------
    private void loadModules() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "modules.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            modules.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                String code = cols[0].trim();
                int lecHours = Integer.parseInt(cols[1].trim());
                int labHours = Integer.parseInt(cols[2].trim());
                int tutHours = Integer.parseInt(cols[3].trim());

                // Lecturer IDs (could be multiple, separated by ;)
                String[] lecturerIds = cols[4].trim().split(";");
                List<Lecturer> moduleLecturers = new ArrayList<>();
                for (String id : lecturerIds) {
                    Lecturer lec = getLecturer(id.trim());
                    if (lec != null) {
                        moduleLecturers.add(lec);
                    }
                }

                modules.add(new Module(code, lecHours, labHours, tutHours, moduleLecturers));
            }
        } catch (Exception e) {
            System.err.println("Error loading modules: " + e.getMessage());
        }
    }

    // ---------------- Load Student Groups ----------------
    private void loadGroups() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "student_groups.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            groups.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
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

    // ---------------- Load Timetable ----------------
    private void loadScheduledClasses() {
        try (CSVReader reader = new CSVReader(DATA_PATH + "timetable.csv")) {
            ArrayList<String[]> rows = reader.readToMatrix();
            scheduledClasses.clear();
            for (String[] cols : rows.subList(1, rows.size())) {
                Module m = getModule(cols[0].trim());
                Room r = getRoom(cols[1].trim());
                String day = cols[2].trim();
                String time = cols[3].trim();
                StudentGroup g = getGroup(cols[4].trim());
                scheduledClasses.add(new ScheduledClass(m, r, day, time, g));
            }
        } catch (Exception e) {
            System.err.println("Error loading timetable: " + e.getMessage());
        }
    }

    // ---------------- Save Timetable ----------------
    public void saveScheduledClasses() {
        try (CSVWriter writer = new CSVWriter(DATA_PATH + "timetable.csv")) {
            writer.writeRecord("Module,Room,Day,Time,Group");
            List<String[]> rows = new ArrayList<>();
            for (ScheduledClass sc : scheduledClasses) {
                rows.add(new String[]{
                        sc.getModule().getCode(),
                        sc.getRoom().getRoomId(),
                        sc.getDay(),
                        sc.getTime(),
                        sc.getGroup().getGroupId()
                });
            }
            writer.writeMatrix(rows);
        } catch (Exception e) {
            System.err.println("Error saving timetable: " + e.getMessage());
        }
    }

    public void generateAndSaveTimetable() {
        TimetableGenerator generator = new TimetableGenerator(this);
        List<ScheduledClass> generated = generator.generateTimetable();
        setScheduledClasses(generated);
        saveScheduledClasses();
    }

    public List<ClashDetector.Clash> detectClashes() {
        ClashDetector detector = new ClashDetector();
        return detector.detectClashes(getScheduledClasses());
    }


    // ---------------- Lookup Helpers ----------------
    public Programme getProgramme(String code) {
        return programmes.stream().filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
    }

    public Lecturer getLecturer(String id) {
        return lecturers.stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
    }

    public Room getRoom(String id) {
        return rooms.stream().filter(r -> r.getRoomId().equals(id)).findFirst().orElse(null);
    }

    public Module getModule(String code) {
        return modules.stream().filter(m -> m.getCode().equals(code)).findFirst().orElse(null);
    }

    public StudentGroup getGroup(String id) {
        return groups.stream().filter(g -> g.getGroupId().equals(id)).findFirst().orElse(null);
    }

    // ---------------- Getters for Lists ----------------
    public List<Module> getModules() { return modules; }
    public List<Room> getRooms() { return rooms; }
    public List<StudentGroup> getGroups() { return groups; }
    public List<Lecturer> getLecturers() { return lecturers; }
    public List<ScheduledClass> getScheduledClasses() { return scheduledClasses; }

    public void setScheduledClasses(List<ScheduledClass> classes) {
        this.scheduledClasses = classes;
    }
}

