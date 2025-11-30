package controller;
import model.*;
import java.util.*;

/**
 * Class that detects clashes scheduling conflicts in the timetable
 *
 * Checks for room double bookings, lecturer clashes, group clashes, room capacity
 * and room type mismsatches.
 *
 * It is run after the timetable is generated to fully ensure there are no clashes
 *
 */
public class ClashDetector {

    public static class Clash {
        private String type;
        private String description;
        private ScheduledClass scheduledClass1;
        private ScheduledClass scheduledClass2;

        /**
         * Creates a Clash object. Giving details about what caused it
         * @param type type of clash
         * @param description readable description of the clash
         * @param sc1 the first scheduled class involved in the clash
         * @param sc2 the second scheduled class involved in the clash
         */
        public Clash(String type, String description, ScheduledClass sc1, ScheduledClass sc2) {
            this.type = type;
            this.description = description;
            this.scheduledClass1 = sc1;
            this.scheduledClass2 = sc2;
        }

        /**
         * Getter method for the type of clash
         * @return clash type
         */
        public String getType() { return type; }

        /**
         * Getter method for the description of the conflict
         * @return clash description
         */
        public String getDescription() { return description; }

        /**
         * Getter method for the first scheduled class involved in a clash
         * @return first scheduled class involved
         */
        public ScheduledClass getScheduledClass1() { return scheduledClass1; }

        /**
         * Getter method for the second scheduled class involved in a clash
         * @return first scheduled class involved
         */
        public ScheduledClass getScheduledClass2() { return scheduledClass2; }

        @Override
        public String toString() {
            return "[" + type + "] " + description;
        }
    }

    /**
     * Detects clashes in the provided timetable.
     * Checks for room double bookings, lecturer clashes, group clashes, room capacity
     * and room type mismsatches.
     * @param timetable the list of ScheduledClass objects to check for clashes
     * @return a list of Clash objects representing the conflicts
     */
    public List<Clash> detectClashes(List<ScheduledClass> timetable) {
        List<Clash> clashes = new ArrayList<>();

        for (int i = 0; i < timetable.size(); i++) {
            ScheduledClass sc1 = timetable.get(i);

            for (int j = i + 1; j < timetable.size(); j++) {
                ScheduledClass sc2 = timetable.get(j);

                // Same day and time
                if (sc1.getDay().equals(sc2.getDay()) && sc1.getTime().equals(sc2.getTime())) {
                    // Room clash
                    if (sc1.getRoom().getRoomId().equals(sc2.getRoom().getRoomId())) {
                        clashes.add(new Clash(
                                "Room Clash",
                                "Room " + sc1.getRoom().getRoomId() + " is double-booked at " + sc1.getDay() + " " + sc1.getTime(),
                                sc1, sc2
                        ));
                    }
                    // Lecturer clash
                    for (Lecturer lec1 : sc1.getModule().getLecturers()) {
                        if (sc2.getModule().getLecturers().contains(lec1)) {
                            clashes.add(new Clash(
                                    "Lecturer Clash",
                                    "Lecturer " + lec1.getName() + " is scheduled for two classes at " + sc1.getDay() + " " + sc1.getTime(),
                                    sc1, sc2
                            ));
                        }
                    }
                    // Group clash
                    if (sc1.getGroup().getGroupId().equals(sc2.getGroup().getGroupId())) {
                        clashes.add(new Clash(
                                "Group Clash",
                                "Group " + sc1.getGroup().getGroupId() + " has two classes at " + sc1.getDay() + " " + sc1.getTime(),
                                sc1, sc2
                        ));
                    }
                }
            }

            // Room capacity clash
            if (sc1.getGroup().getSize() > sc1.getRoom().getCapacity()) {
                clashes.add(new Clash(
                        "Room Capacity Clash",
                        "Group " + sc1.getGroup().getGroupId() + " (" + sc1.getGroup().getSize() + " students) exceeds room " + sc1.getRoom().getRoomId() + " capacity (" + sc1.getRoom().getCapacity() + ")",
                        sc1, null
                ));
            }

            // Room type clash (lab required)
            if (sc1.getModule().requiresLab() && !sc1.getRoom().isLab()) {
                clashes.add(new Clash(
                        "Room Type Clash",
                        "Module " + sc1.getModule().getCode() + " requires a lab, but room " + sc1.getRoom().getRoomId() + " is not a lab.",
                        sc1, null
                ));
            }
        }
        return clashes;
    }
}
