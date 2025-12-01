TIMETABLE SYSTEM
A java app for creating and querying timetables. Developed for the CS4013 Software Development Project @ University of Limerick.

FEATURES
Timetable generation: Automatically shecdules lectures, tutorial and labs for all student groups.
Clash Detection: Identifies scheduling conflicts(room, lecturer, group,capacity,room type).
Data Management: Loads and saves data from CSV files.
User Interface: Command Line Interface (CLI) for generating, viewing and saving timetables.
Architecture: Designed using MVC for ease of adding new features

RUNNING THE APP

PREREQUISITES
Java Dev Kit (JDK)
Sample CSV data files

CLONE REPOSITORY
git clone https://github.com/luigi-crt/TimetableSystemProject.git
cd TimetableSystemProject

COMPILE CODE
javac -d bin src/**/*.java

PREPARE CSV FILES
Place/ input CSV files with the data you wish in programmes.csv, lecturers.csv, rooms.csv, modules.csv, student_groups.csv, students.csv, programme_structure.csv in the resources folder

RUN THE APP
java -cp bin view.Main

CLI OPTIONS 
Generate Timetable: Create a new timetable for a selected semester.
View Timetable: Display the full timetable.
Save Timetable: Export the timetable to CSV.
View Student Timetable: Enter a student ID to view their timetable.
View Lecturer Timetable: Enter a lecturer ID to view their timetable.
Exit: Quit the application.

AUTHOR
Luigi Curotto
University of Limerick

This project is for academic use only.








