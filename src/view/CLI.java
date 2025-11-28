package view;

import data.*;
import model.*;

import java.util.List;
import java.util.Scanner;

public class CLI {

    private DataManager data;
    private Scheduler scheduler;

    public CLI() {
        data = new DataManager();
        data.loadAll();
        scheduler = new Scheduler(data);
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== TIMETABLE SYSTEM =====");
            System.out.println("1. Generate Timetable");
            System.out.println("2. View Scheduled Classes Count");
            System.out.println("3. Save Timetable");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = Integer.parseInt(sc.nextLine());

            switch(choice) {
                case 1 -> generate();
                case 2 -> System.out.println("Scheduled classes: " + data.getScheduledClasses().size());
                case 3 -> save();
            }

        } while (choice != 0);
    }

    private void generate() {
        List<ScheduledClass> generated = scheduler.generateTimetable();
        data.setScheduledClasses(generated);
        System.out.println("Timetable generated.");
    }

    private void save() {
        data.saveScheduledClasses();
        System.out.println("Timetable saved to timetable.csv");
    }
}
