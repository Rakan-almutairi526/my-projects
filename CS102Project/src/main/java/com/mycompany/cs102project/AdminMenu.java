package com.mycompany.cs102project;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminMenu implements Menu {

    private Admin admin;
    private Scanner input;

    public AdminMenu(Admin admin, Scanner input) {
        this.admin = admin;
        this.input = input;
    }

    @Override
    public void start() {
        admin.showRoleSummary();
        System.out.println();
        System.out.println();

        int choice;
        do {
            menu();
            choice = readChoice(6, 0, input);

            switch (choice) {

                case 1: addCourse(); break;
                case 2: removeCourse();break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6: break;
            }
        } while (choice != 0);
    }

    @Override
    public void menu() {
        System.out.println("-------------- Admin Menu --------------");
        System.out.println("Please choose an option:");
        System.out.println("1. Add Course");
        System.out.println("2. Remove Course");
        System.out.println("3. Update Course");
        System.out.println("4. Add User");
        System.out.println("5. Remove User");
        System.out.println("6. Show System statistic");
        System.out.println("0. Logout");
        System.out.println("----------------------------------------");
        System.out.print("Enter your choice: ");
    }

    private void addCourse(){
        System.out.println("-------------- Add course --------------");
        do {
            System.out.println("Enter Course Information\n");
            String courseCode = takeInformation("Course Code");
            String courseTitle = takeInformation("Course Title");
            String courseDescription = takeInformation("Course Description");
            String instructorId = takeInformation("Instructor Id");
            int capacity = Integer.parseInt(takeInformation("Capacity"));
            int enrolledStudentCount = 0;
            String schedule = takeInformation("Schedule");
            int credits = Integer.parseInt(takeInformation("Credits"));
            ArrayList<String> prerequisites = new ArrayList<>();
            while (true){
                System.out.println("0. Stop Add Prerequisite");
                String prerequisite = takeInformation("Prerequisite");
                if (prerequisite.equalsIgnoreCase("0")) break;
                prerequisites.add(prerequisite);
            }
            Course course = new Course(courseCode, courseTitle, courseDescription, instructorId, capacity, enrolledStudentCount, schedule, credits, prerequisites);
            admin.addCourse(course);
            DataManager.saveCoursesToFile("courses.txt");
            System.out.println("Course was added successfully!");
            System.out.println("Course details:");
            System.out.println(course.toString());
            System.out.print("Prerequisites: ");
            if (prerequisites.isEmpty()) System.out.print("None");
            for (String pre : prerequisites){
                System.out.print(pre + " ");
            }
            System.out.println("0. Return");
            System.out.println("1. Add another course");
            int choice = readChoice(1, 0, input);
            if (choice == 0) break;
        }while (true);
        System.out.println("----------------------------------------");
    }

    private void removeCourse(){
        System.out.println("-------------- Remove course --------------");
        int choice;
        do {
            Course course = DataManager.findCourse(takeInformation("Course Code"));
            if (course == null) {
                System.out.println("Course Not Found!");
            }else {
                System.out.println(course);
                System.out.println("Are you sure you want to remove this course?");
                System.out.println("0. no");
                System.out.println("1. yes");
                choice = readChoice(1, 0, input);
                if (choice == 1) {
                    admin.removeCourse(course);
                    DataManager.saveAllData();
                    System.out.println("Course was removed successfully!");
                } else {
                    System.out.println("Removal stopped!");
                }
            }
            System.out.println("0. return");
            System.out.println("1. continue in remove course");
            choice = readChoice(1, 0, input);
            if (choice == 0) break;
        }while (true);
        System.out.println("-------------------------------------------");
    }
    private void updateCourseInfo(){
        System.out.println("-------------- Update Course Information --------------");
        int choice;
        do {
            Course course = DataManager.findCourse(takeInformation("Course Code"));
            if (course == null){
                System.out.println("Course Not Found!");
            }else {
                System.out.print(course);
                System.out.print(" Prerequisite: ");
                for (String prerequisite : course.getPrerequisites()) {
                    System.out.print(prerequisite + " ");
                }
                do {

                    System.out.println();
                    System.out.println("0. Finish Updating");
                    System.out.println("1. Update Code");
                    System.out.println("2. Update Title");
                    System.out.println("3. Update Description");
                    System.out.println("4. Update Instructor Id");
                    System.out.println("5. Update Capacity");
                    System.out.println("6. Update Schedule");
                    System.out.println("7. Update Credits");
                    System.out.println("8. Add Prerequisite");
                    System.out.println("9. Remove Prerequisite");
                    System.out.print("Enter your choice: ");
                    choice = readChoice(9, 0, input);
                    switch (choice){

                        case 0:
                            break;
                        case 1:
                            System.out.println("Current Course Code: " + course.getCourseCode());
                            course.setCourseCode(takeInformation("Course Code"));
                            System.out.println("Course Code was ");

                    }
                }while (choice != 0);
            }
        }

    }
    private String takeInformation(String requiredInfo){
        System.out.print(requiredInfo + ": ");
        String info = input.nextLine();
        System.out.println();
        return info;
    }
    private void updateAttribute(String attribute, Course course){
        System.out.println("Current Course " + attribute + ": " + course.getCourseCode());
        course.setCourseCode(takeInformation(attribute));
        System.out.println("Course " + attribute + " was update to " + course.get);
    }
}
