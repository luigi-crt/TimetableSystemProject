package view;

import controller.ClashDetector;
import controller.TimetableGenerator;
import data.DataManager;
import model.ScheduledClass;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private final DataManager dataManager;
    private final Scanner scanner;

    public CLI(DataManager dataManager) {
        this.dataManager = dataManager;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== University Timetable System ===");
        dataManager.loadAll();
        System.out.println("Data loaded from CSV files.");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Generate Timetable");
            System.out.println("2. Detect Clashes");
            System.out.println("3. View Timetable");
            System.out.println("4. Save Timetable");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    TimetableGenerator generator = new TimetableGenerator(dataManager);
                    List<ScheduledClass> timetable = generator.generateTimetable();
                    dataManager.setScheduledClasses(timetable);
                    System.out.println("Timetable generated.");
                    break;
                case "2":
                    ClashDetector detector = new ClashDetector();
                    List<ClashDetector.Clash> clashes = detector.detectClashes(dataManager.getScheduledClasses());
                    if (clashes.isEmpty()) {
                        System.out.println("No clashes detected!");
                    } else {
                        System.out.println("Clashes found:");
                        for (ClashDetector.Clash clash : clashes) {
                            System.out.println(clash);
                        }
                    }
                    break;
                case "3":
                    List<ScheduledClass> classes = dataManager.getScheduledClasses();
                    if (classes.isEmpty()) {
                        System.out.println("No timetable generated yet.");
                    } else {
                        for (ScheduledClass sc : classes) {
                            System.out.println(
                                    sc.getDay() + " " + sc.getTime() +
                                            " | Room: " + sc.getRoom().getRoomId() +
                                            " | Group: " + sc.getGroup().getGroupId() +
                                            " | Module: " + sc.getModule().getCode()
                            );
                        }
                    }
                    break;
                case "4":
                    dataManager.saveScheduledClasses();
                    System.out.println("Timetable saved to CSV.");
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
