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

                case 1:
                    addCourse();
                    break;
                case 2:
                    removeCourse();
                    break;
                case 3:
                    updateCourseInfo();
                    break;
                case 4:
                    addUser();
                    break;
                case 5:
                    removeUser();
                    break;
                case 6:
                    showSystemStatistic();
                    break;
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

    private void addCourse() {

        System.out.println("---------- Add Course ----------");

        String courseCode;
        while (true) {
            courseCode = takeInformation("Course code").trim();
            if (DataManager.findCourse(courseCode) != null) {
                System.out.println("A course with this code already exists. Please enter another code.");
            } else {
                break;
            }
        }

        String title = takeInformation("Course title").trim();
        String description = takeInformation("Description").trim();

        String instructorId;

        while (true) {

            instructorId = takeInformation("Instructor ID").trim();

            if (instructorId.equalsIgnoreCase("Yet to be defined")) {
                break;
            }

            User user = DataManager.findUser(instructorId);

            if (user instanceof Advisor) {
                break;
            }

            System.out.println("No advisor with this ID exists. Please try again.");
        }

        int capacity;

        while (true) {

            try {

                capacity = Integer.parseInt(takeInformation("Capacity").trim());

                if (capacity <= 0) {
                    System.out.println("Capacity must be greater than zero.");
                } else {
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        String schedule = takeInformation("Schedule");

        int credits;

        while (true) {

            try {

                credits = Integer.parseInt(takeInformation("Credits").trim());

                if (credits <= 0) {
                    System.out.println("Credits must be greater than zero.");
                } else {
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        ArrayList<String> prerequisites = new ArrayList<>();

        System.out.println("\nDo you want to add prerequisites?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int choice = readChoice(2, 1, input);

        while (choice == 1) {

            String prerequisite;

            while (true) {

                prerequisite = takeInformation("Prerequisite course code").trim();

                if (prerequisite.equalsIgnoreCase(courseCode)) {
                    System.out.println("A course cannot be its own prerequisite.");
                }

                else if (DataManager.findCourse(prerequisite) == null) {
                    System.out.println("Course does not exist.");
                }

                else if (prerequisites.contains(prerequisite)) {
                    System.out.println("This prerequisite has already been added.");
                }

                else {
                    prerequisites.add(prerequisite);
                    System.out.println("Prerequisite added successfully.");
                    break;
                }
            }

            System.out.println("\nAdd another prerequisite?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            choice = readChoice(2, 1, input);
        }

        Course course = new Course(courseCode, title, description, instructorId, capacity, 0, schedule ,credits, prerequisites);
        admin.addCourse(course);

        DataManager.saveAllData();

        System.out.println("Course was added successfully.");
        System.out.println("---------------------------------");
    }
    
    private void removeCourse() {
        System.out.println("-------------- Remove course --------------");
        int choice;
        do {
            Course course = DataManager.findCourse(takeInformation("Course Code"));
            if (course == null) {
                System.out.println("Course Not Found!");
            } else {
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
        } while (true);
        System.out.println("-------------------------------------------");
    }

    private void updateCourseInfo() {

        System.out.println("-------------- Update Course Information --------------");

        int choice;

        do {

            Course course = DataManager.findCourse(takeInformation("Course Code"));

            if (course == null) {

                System.out.println("Course Not Found!");

            } else {

                do {

                    System.out.println();
                    System.out.println(course);

                    System.out.print("Prerequisites: ");

                    if (course.getPrerequisites().isEmpty()) {
                        System.out.println("None");
                    } else {
                        for (String prerequisite : course.getPrerequisites()) {
                            System.out.print(prerequisite + " ");
                        }
                        System.out.println();
                    }

                    System.out.println();
                    System.out.println("0. Finish Updating");
                    System.out.println("1. Update Course Code");
                    System.out.println("2. Update Title");
                    System.out.println("3. Update Description");
                    System.out.println("4. Update Instructor ID");
                    System.out.println("5. Update Capacity");
                    System.out.println("6. Update Schedule");
                    System.out.println("7. Update Credits");
                    System.out.println("8. Add Prerequisite");
                    System.out.println("9. Remove Prerequisite");

                    choice = readChoice(9, 0, input);

                    if (choice == 0) {
                        break;
                    }

                    String info;

                    switch (choice) {

                        case 1:
                            System.out.println("Current Course Code: " + course.getCourseCode());
                            info = takeInformation("New Course Code");
                            if (admin.updateCourseInfo(course, info, 1)){
                            System.out.println("Course Code Updated Successfully!");
                            }else {
                                System.out.println("Course Code already exist! new Course Code must be unique");
                            }
                            break;
                        case 2:
                            System.out.println("Current Title: " + course.getCourseTitle());
                            info = takeInformation("New Title");
                            if (admin.updateCourseInfo(course, info, 2)) System.out.println("Course Title Updated Successfully!");
                            break;
                        case 3:
                            System.out.println("Current Description: " + course.getCourseDescription());
                            info = takeInformation("New Description");
                            if (admin.updateCourseInfo(course, info, 3)) System.out.println("Course Description Updated Successfully!");
                            break;
                        case 4:
                            System.out.println("Current Instructor ID: " + course.getInstructorId());
                            info = takeInformation("New Instructor ID");
                            if (admin.updateCourseInfo(course, info, 4)) {
                                System.out.println("Instructor Updated Successfully!");
                            }else {
                                System.out.println("Instructor Id was not found!");
                            }
                            break;
                        case 5:
                            System.out.println("Current Capacity: " + course.getCapacity());
                            info = takeInformation("New Capacity");
                            if (admin.updateCourseInfo(course, info, 5)) {
                                System.out.println("Capacity Updated Successfully!");
                            } else {
                                System.out.println("Invalid Capacity!, should be grater than enrolled student!");
                            }
                            break;
                        case 6:
                            System.out.println("Current Schedule: " + course.getSchedule());
                            info = takeInformation("New Schedule");
                            if (admin.updateCourseInfo(course, info, 6)) System.out.println("Schedule Updated Successfully!");
                            break;
                        case 7:
                            System.out.println("Current Credits: " + course.getCredits());
                            info = takeInformation("New Credits");
                            if (admin.updateCourseInfo(course, info, 7)) System.out.println("Credits Updated Successfully!");
                            break;
                        case 8:
                            info = takeInformation("Prerequisite To Add");
                            if (admin.updateCourseInfo(course, info, 8)) {
                                System.out.println("Prerequisite Added Successfully!");
                            }else {
                                System.out.println("Adding Prerequisite Failed!, can not add course that; do not exit or already a prerequisite or the same course");
                            }
                            break;
                        case 9:
                            info = takeInformation("Prerequisite To Remove");
                            if (admin.updateCourseInfo(course, info, 9)){
                                System.out.println("Prerequisite Removed Successfully!");
                            }else {
                                System.out.println("Removing Prerequisite Failed!, should enter an existing prerequisite");
                            }
                            break;
                    }

                    DataManager.saveAllData();

                } while (true);

            }

            System.out.println();
            System.out.println("0. Return");
            System.out.println("1. Update Another Course");

            choice = readChoice(1, 0, input);

        } while (choice == 1);

        System.out.println("----------------------------------------");
    }
    private void addUser() {

        System.out.println("---------- Add User ----------");
        System.out.println("0. Return");
        System.out.println("1. Add Student");
        System.out.println("2. Add Advisor");
        int choice = readChoice(2, 0, input);
        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                addAdvisor();
                break;
            case 0:
                return;
        }
        System.out.println("------------------------------");
    }
    private void removeUser() {

        System.out.println("---------- Remove User ----------");

        User user;

        while (true) {

            String id = takeInformation("Enter user ID: ").trim();

            user = DataManager.findUser(id);

            if (user == null) {
                System.out.println("No user with this ID exists. Please try again.");
            }
            else {
                break;
            }
        }

        System.out.println("\nUser Information:");
        System.out.println(user);

        System.out.println("\nAre you sure?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int choice = readChoice(2, 1, input);

        if (choice == 2) {
            System.out.println("Operation cancelled.");
            System.out.println("---------------------------------");
            return;
        }

        admin.removeUser(user);

        DataManager.saveAllData();

        System.out.println("User was removed successfully.");
        System.out.println("---------------------------------");
    }
    private void addStudent() {

        System.out.println("---------- Add Student ----------");
        String id;
        while (true) {

            id = takeInformation("Enter student ID: ").trim();

            if (DataManager.findUser(id) != null) {
                System.out.println("A user with this ID already exists. Please enter another ID.");
            } else {
                break;
            }
        }

        String name = takeInformation("Student name: ").trim();
        String phone = takeInformation("Phone number: ").trim();
        String address = takeInformation("Address: ").trim();
        String major = takeInformation("Major: ").trim();

        System.out.println("Is an advisor currently defined for this student?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int choice = readChoice(2, 1, input);
        String advisorId;
        Advisor advisor = null;

        if (choice == 1) {

            while (true) {

                advisorId = takeInformation("Enter advisor ID: ").trim();
                User user = DataManager.findUser(advisorId);
                if (user == null) {
                    System.out.println("No user with this ID exists. Please try again.");

                } else if (!(user instanceof Advisor)) {
                    System.out.println("The entered ID does not belong to an advisor. Please try again.");
                } else {
                    advisor = (Advisor) user;
                    break;
                }
            }
        } else {
            advisorId = "Yet to be defined";
        }

        Student student = new Student(id, name, phone, address, major, advisorId);

        admin.addUser(student);

        if (advisor != null) {
            advisor.getStudentList().add(student);
        }
        DataManager.saveAllData();
        System.out.println("Student was added successfully.");
        System.out.println("---------------------------------");
    }
    private void addAdvisor() {

        System.out.println("---------- Add Advisor ----------");
        String id;
        while (true) {
            id = takeInformation("Enter advisor ID: ").trim();

            if (DataManager.findUser(id) != null) {
                System.out.println("A user with this ID already exists. Please enter another ID.");
            }
            else {
                break;
            }
        }

        String name = takeInformation("Advisor name: ").trim();
        String phone = takeInformation("Phone number: ").trim();
        String address = takeInformation("Address: ").trim();

        Advisor advisor = new Advisor(id, name, phone, address);

        admin.addUser(advisor);

        DataManager.saveAllData();

        System.out.println("Advisor was added successfully.");
        System.out.println("---------------------------------");
    }
    private void showSystemStatistic() {
        System.out.println("-------------- System Statistic --------------");
        admin.viewAllUsers();
        System.out.println("Total users: " + DataManager.userList.size());
        admin.viewAllCourses();
        System.out.println("Total courses: " + DataManager.courseList.size());
        System.out.println("----------------------------------------------");
    }

    private String takeInformation(String requiredInfo) {
        while (true) {
            System.out.print(requiredInfo + ": ");
            String info = input.nextLine().trim();
            System.out.println();
            if (!info.isEmpty()) return info;
            System.out.println("This field cannot be empty. Please try again.");
        }
    }
}
