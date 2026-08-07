package com.mycompany.cs102project;

import java.util.Scanner;

public class StudentMenu implements Menu {

    private Student student;
    private Scanner input;


    public StudentMenu(Student student, Scanner input) {
        this.student = student;
        this.input = input;
    }

    @Override
    public void start() {
        student.showRoleSummary();
        System.out.println();
        System.out.println();
        int choice;
        do {
            menu();
            choice = readChoice(9, 0, this.input);
            switch (choice) {
                case 0:
                    break;
                case 1:
                    registerForCourse();
                    break;
                case 2:
                    dropCourse();
                    break;
                case 3:
                    viewRegisteredCourses();
                    break;
                case 4:
                    viewSchedule();
                    break;
                case 5:
                    viewAvailableCourses();
                    break;
                case 6:
                    submitSpecialRequest();
                    break;
                case 7:
                    ShowPrerequisite();
                    break;
                case 8:
                    updatePersonalInfo();
                    break;
                case 9:
                    showSpecialRequest();
                    break;
            }
        } while (choice != 0);
    }

    @Override
    public void menu() {
        System.out.println("-------------- Student Menu --------------");
        System.out.println("Please choose an option:");
        System.out.println("1. Register for a Course");
        System.out.println("2. Drop a Course");
        System.out.println("3. View Registered Courses");
        System.out.println("4. View Schedule");
        System.out.println("5. View Available Courses");
        System.out.println("6. Submit a Special Request");
        System.out.println("7. Show Prerequisites Of a Course");
        System.out.println("8. Update Personal Information");
        System.out.println("9. Show Special Requests Submitted");
        System.out.println("0. Logout");
        System.out.println("------------------------------------------");
        System.out.print("Enter your choice: ");
    }

    private void showSpecialRequest() {
        System.out.println("-------------- Special Requests --------------");
        student.ShowSpecialRequest();
        System.out.println("----------------------------------------------");
    }

    private void updatePersonalInfo() {
        System.out.println("-------------- Update Personal Information --------------");
        int choice;
        do {
            student.showRoleSummary();
            System.out.println("0. return");
            System.out.println("1. change name");
            System.out.println("2. change phone number");
            System.out.print("Enter your choice: ");
            choice = readChoice(2, 0, this.input);
            if (choice == 1) {
                System.out.print("Enter name: ");
                String name = input.nextLine().trim();
                System.out.println();
                student.setName(name);
                System.out.println("Operation was successful\n");
                DataManager.saveUsersToFile("users.txt");
            }
            if (choice == 2) {
                System.out.print("Enter phone: ");
                String phone = input.nextLine().trim();
                System.out.println();
                student.setPhone(phone);
                System.out.println("Operation was successful\n");
                DataManager.saveUsersToFile("users.txt");
            }
        } while (choice != 0);
        System.out.println("---------------------------------------------------------");
    }

    private void ShowPrerequisite() {
        System.out.println("-------------- Show Course's Prerequisites --------------");
        Course course = null;
        String courseCode = "";
        int choice;
        do {
            System.out.println("0. return");
            System.out.print("Enter course code: ");
            courseCode = input.nextLine().trim();
            System.out.println();
            if (courseCode.equalsIgnoreCase("0")) break;
            course = DataManager.findCourse(courseCode);
            if (course == null) {
                System.out.println("Course not found\n");
            } else {
                course.showAllPrerequisites();
            }
        } while (true);
        System.out.println("---------------------------------------------------------");
    }

    private void submitSpecialRequest() {
        System.out.println("-------------- Submit Special Request --------------");
        System.out.println("Submitting special request require course code and message");
        Course course = null;
        String courseCode = "";
        while (true) {
            System.out.println("0. return");
            System.out.print("Enter course Code: ");
            courseCode = input.nextLine().trim();
            System.out.println();
            if (courseCode.equalsIgnoreCase("0")) break;
            course = DataManager.findCourse(courseCode);
            if (course == null) {
                System.out.println("Course not found\n");
            } else {
                break;
            }
        }
        if (course != null) {
            System.out.print("Enter the message: ");
            String message = input.nextLine().trim();
            System.out.println();
            student.submitSpecialReq(courseCode, message);
            System.out.println("Submitting special request was successful");
            DataManager.saveSpecialRequestsToFile("specialRequests.txt");
        }
        System.out.println("----------------------------------------------------");
    }

    private void viewAvailableCourses() {
        System.out.println("-------------- Available Courses --------------");
        student.viewAvailableCourses();
        System.out.println("------------------------------------------------");
    }

    private void viewSchedule() {
        System.out.println("-------------- Schedule --------------");
        student.viewSchedule();
        System.out.println("--------------------------------------");
    }

    private void registerForCourse() {
        System.out.println("-------------- Registration --------------");
        do {
            System.out.println("0. return");
            System.out.print("Enter course Code: ");
            String courseCode = input.nextLine().trim();
            System.out.println();
            if (courseCode.equalsIgnoreCase("0")) break;
            String registerResult = student.registerForCourse(courseCode);
            switch (registerResult) {
                case "NOT_FOUND":
                    System.out.println("The course is not found\n");
                    break;
                case "ALREADY_REGISTERED":
                    System.out.println("The course is already registered\n");
                    break;
                case "ALREADY_COMPLETED":
                    System.out.println("You have already completed this course.");
                    break;
                case "MISSING_PREREQUISITE":
                    System.out.println("Prerequisites missing: " + student.getMissingPrerequisite(courseCode) + "\n");
                    break;
                case "NOT_ENOUGH_SEATS":
                    System.out.println("The course does not have available seats\n");
                    break;
                case "SCHEDULE_CONFLICT":
                    System.out.println("Schedule conflicts with: " + student.getScheduleConflicts(courseCode) + "\n");
                    break;
                case "SUCCESS":
                    System.out.println("Registration successful");
                    System.out.println("You are registered in: " + courseCode + "\n");
                    DataManager.saveEnrollments("enrollments.txt");
                    DataManager.saveCoursesToFile("courses.txt");
                    break;
            }

        } while (true);
        System.out.println("------------------------------------------");
    }

    private void dropCourse() {

        System.out.println("-------------- Drop Course --------------");
        do {
            System.out.println("0. return");
            System.out.print("Enter course Code: ");
            String courseCode = input.nextLine().trim();
            System.out.println();
            if (courseCode.equalsIgnoreCase("0")) break;
            String dropResult = student.dropCourse(courseCode);

            switch (dropResult) {

                case "NOT_FOUND":
                    System.out.println("The course is not found\n");
                    break;
                case "NOT_REGISTERED":
                    System.out.println("The course " + courseCode + " is not registered. therefore, can not drop\n");
                    break;
                case "SUCCESS":
                    System.out.println("The course " + courseCode + " was successfully dropped");
                    System.out.println("*Registered courses*");
                    student.viewRegisteredCourses();
                    System.out.println("********************\n");
                    DataManager.saveEnrollments("enrollments.txt");
                    DataManager.saveCoursesToFile("courses.txt");
                    break;
            }
        } while (true);
        System.out.println("-----------------------------------------");
    }

    private void viewRegisteredCourses() {
        System.out.println("-------------- Registered Courses --------------");
        student.viewRegisteredCourses();
        System.out.println("------------------------------------------------");
    }
}
