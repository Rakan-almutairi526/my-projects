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
                case 2: break;
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
            Integer capacity = Integer.parseInt(takeInformation("Capacity"));
            Integer enrolledStudentCount = 0;
            String schedule = takeInformation("Schedule");
            Integer credits = Integer.parseInt(takeInformation("Credits"));
            ArrayList<String> prerequisites = new ArrayList<>();
            while (true){
                System.out.println("0. Stop Add Prerequisite");
                String prerequisite = takeInformation("Prerequisite");
                if (prerequisite.equalsIgnoreCase("0")) break;
                prerequisites.add(prerequisite);
            }
            Course course = new Course(courseCode, courseTitle, courseDescription, instructorId, capacity, enrolledStudentCount, schedule, credits, prerequisites);
            DataManager.courseList.add(course);
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
    }
    private <T> T takeInformation(String requiredInfo){

        System.out.print(requiredInfo + ": ");
        T info = (T) input.nextLine();
        System.out.println();
        return info;
    }
}
