package utility;

import model.Module;
import model.*;
import java.util.*;

public class DataManager {

    // In-memory lists
    public List<Programme> programmes = new ArrayList<>();
    public List<StudentGroup> groups = new ArrayList<>();
    public List<Student> students = new ArrayList<>();
    public List<Lecturer> lecturers = new ArrayList<>();
    public List<Room> rooms = new ArrayList<>();
    public List<Module> modules = new ArrayList<>();
    public List<ScheduledClass> scheduledClasses = new ArrayList<>();

    // Load everything
    public void loadAll() {
        loadProgrammes();
        loadLecturers();
        loadRooms();
        loadModules();
        loadGroups();
        loadStudents();
        loadScheduledClasses();  // timetable
    }

    // ---------- Programmes ----------
    private void loadProgrammes() {
        programmes = CSVMapper.load("programmes.csv", cols ->
                new Programme(cols[0].trim(), cols[1].trim(), new ArrayList<>())
        );
    }

    // ---------- Lecturers ----------
    private void loadLecturers() {
        lecturers = CSVMapper.load("lecturers.csv", cols ->
                new Lecturer(cols[0].trim(), cols[1].trim())
        );
    }

    // ---------- Rooms ----------
    private void loadRooms() {
        rooms = CSVMapper.load("rooms.csv", cols -> {
            String id = cols[0].trim();
            int cap = Integer.parseInt(cols[1].trim());
            boolean isLab = cols[2].trim().equalsIgnoreCase("lab");
            return new Room(id, cap, isLab);
        });
    }

    // ---------- Modules ----------
    private void loadModules() {
        modules = CSVMapper.load("modules.csv", cols -> {
            String code = cols[0].trim();
            int lec = Integer.parseInt(cols[1].trim());
            int lab = Integer.parseInt(cols[2].trim());
            int tut = Integer.parseInt(cols[3].trim());

            String lecturerId = cols[4].trim();
            Lecturer lecObj = getLecturer(lecturerId);

            return new Module(code, lec, lab, tut, List.of(lecObj));
        });
    }

    // ---------- Student Groups ----------
    private void loadGroups() {
        groups = CSVMapper.load("student_groups.csv", cols -> {
            String id = cols[0].trim();
            Programme p = getProgramme(cols[1].trim());
            int year = Integer.parseInt(cols[2].trim());
            int size = Integer.parseInt(cols[3].trim());
            return new StudentGroup(id, p, year, size);
        });
    }

    // ---------- Students ----------
    private void loadStudents() {
        students = CSVMapper.load("students.csv", cols -> {
            String id = cols[0].trim();
            String name = cols[1].trim();
            Programme p = getProgramme(cols[2].trim());
            int year = Integer.parseInt(cols[3].trim());
            StudentGroup g = getGroup(cols[4].trim());
            return new Student(id, name, p, year, g);
        });
    }

    // ---------- Timetable / Scheduled Classes ----------
    private void loadScheduledClasses() {
        scheduledClasses = CSVMapper.load("timetable.csv", cols -> {
            Module m = getModule(cols[0].trim());
            Room r = getRoom(cols[1].trim());
            String day = cols[2].trim();
            String time = cols[3].trim();
            StudentGroup g = getGroup(cols[4].trim());

            return new ScheduledClass(m, r, day, time, g);
        });
    }

    // ------------------- Lookup Helpers ------------------- //

    public Programme getProgramme(String code) {
        return programmes.stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst().orElse(null);
    }

    public StudentGroup getGroup(String id) {
        return groups.stream()
                .filter(g -> g.getGroupId().equals(id))
                .findFirst().orElse(null);
    }

    public Student getStudent(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Lecturer getLecturer(String id) {
        return lecturers.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Room getRoom(String id) {
        return rooms.stream()
                .filter(r -> r.getRoomId().equals(id))
                .findFirst().orElse(null);
    }

    public Module getModule(String code) {
        return modules.stream()
                .filter(m -> m.getCode().equals(code))
                .findFirst().orElse(null);
    }
}
