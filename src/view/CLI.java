package view;

import controller.ClashDetector;
import data.DataManager;
import model.ScheduledClass;
import java.util.List;
import java.util.Scanner;

/**
 * The command-line interface for interacting with the timetable system.
 * Allows users to generate a timetable and the view it as a student or lecturer by inserting ID.
 * Also allows to save the timetable as a CSV
 */
public class CLI {
    private final DataManager dataManager;
    private final Scanner scanner;

    /**
     * Constructs a CLI
     * @param dataManager the data manager responisble for loading, saving the timetable data
     */
    public CLI(DataManager dataManager) {
        this.dataManager = dataManager;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the command line interface, displaying a menu and handling user input.
     */
    public void start() {
        System.out.println("=== University Timetable System ===");
        dataManager.loadAll();
        System.out.println("Data loaded from CSV files.");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Generate Timetable");
            System.out.println("2. View Timetable");
            System.out.println("3. Save Timetable");
            System.out.println("4. View Student Timetable");
            System.out.println("5. View Lecturer Timetable");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter semester (e.g., 1 or 2): ");
                    int semester;
                    try {
                        semester = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid semester. Please enter a number.");
                        break;
                    }
                    dataManager.generateAndSaveTimetable(semester);
                    System.out.println("Timetable generated for semester " + semester + ".");
                    break;
                case "2":
                    List<ScheduledClass> classes = dataManager.getScheduledClasses();
                    if (classes.isEmpty()) {
                        System.out.println("No timetable generated yet.");
                    } else {
                        for (ScheduledClass sc : classes) {
                            System.out.println(
                                    sc.getDay() + " " + sc.getTime() +
                                            " | Room: " + sc.getRoom().getRoomId() +
                                            " | Group: " + sc.getGroup().getGroupId() +
                                            " | Module: " + sc.getModule().getCode() +
                                            " | Type: " + sc.getSessionType() +
                                            " | Lecturer: " + (sc.getLecturer() != null ? sc.getLecturer().getName() : "N/A")
                            );
                        }
                    }
                    break;
                case "3":
                    dataManager.saveScheduledClasses();
                    System.out.println("Timetable saved to CSV.");
                    break;
                case "4":
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    List<ScheduledClass> studentTimetable = dataManager.getTimetableForStudent(studentId);
                    if (studentTimetable.isEmpty()) {
                        System.out.println("No timetable found for this student.");
                    } else {
                        for (ScheduledClass sc : studentTimetable) {
                            System.out.println(
                                    sc.getDay() + " " + sc.getTime() +
                                            " | Room: " + sc.getRoom().getRoomId() +
                                            " | Module: " + sc.getModule().getCode() +
                                            " | Type: " + sc.getSessionType() +
                                            " | Lecturer: " + (sc.getLecturer() != null ? sc.getLecturer().getName() : "N/A")
                            );
                        }
                    }
                    break;
                case "5":
                    System.out.print("Enter Lecturer ID: ");
                    String lecturerId = scanner.nextLine();
                    List<ScheduledClass> lecturerTimetable = dataManager.getTimetableForLecturer(lecturerId);
                    if (lecturerTimetable.isEmpty()) {
                        System.out.println("No timetable found for this lecturer.");
                    } else {
                        for (ScheduledClass sc : lecturerTimetable) {
                            System.out.println(
                                    sc.getDay() + " " + sc.getTime() +
                                            " | Room: " + sc.getRoom().getRoomId() +
                                            " | Group: " + sc.getGroup().getGroupId() +
                                            " | Module: " + sc.getModule().getCode() +
                                            " | Type: " + sc.getSessionType()
                            );
                        }
                    }
                    break;
                case "0":
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
