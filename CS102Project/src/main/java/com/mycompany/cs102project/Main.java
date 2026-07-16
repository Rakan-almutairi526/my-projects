package com.mycompany.cs102project;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        DataManager.loadCoursesFromFile("courses.txt");
        DataManager.loadUsersFromFile("users.txt");
        DataManager.loadEnrollments("enrollments.txt");
        DataManager.loadSpecialRequestFromFile("specialRequests.txt");

        Scanner input = new Scanner(System.in);
        Menu menu;
        while (true) {

            System.out.println("Welcome to the system");;
            System.out.print("Enter your ID (or type EXIT to quit): ");
            String id = input.nextLine();
            System.out.println();

            if (id.equalsIgnoreCase("EXIT")) {
                System.out.println("Goodbye!");
                break;
            }

            User loggedUser = DataManager.findUser(id);

            if (loggedUser == null) {
                System.out.println("User not found!");
                continue;
            }

            if (loggedUser instanceof Student student) {
                menu = new StudentMenu(student, input);
                menu.start();
            } else if (loggedUser instanceof Advisor advisor) {
                menu = new AdvisorMenu(advisor, input);
                menu.start();
            } else if (loggedUser instanceof Admin) {
            }

        }

    }
/*

    private static void showAdminMenu(Admin admin) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-------------- Admin Menu --------------");
            System.out.println("Welcome, " + admin.getName() + " (ID: " + admin.getId() + ")");
            System.out.println("Please choose an option:");
            System.out.println("1. Add Course");
            System.out.println("2. Remove Course");
            System.out.println("3. Add User");
            System.out.println("4. Remove User");
            System.out.println("5. View All Users");
            System.out.println("6. View All Courses");
            System.out.println("7. Logout");
            System.out.println("----------------------------------------");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter course code: ");
                    String code = input.nextLine();

                    System.out.print("Enter title: ");
                    String title = input.nextLine();

                    System.out.print("Enter description: ");
                    String desc = input.nextLine();

                    System.out.print("Enter instructor ID: ");
                    String instructor = input.nextLine();

                    System.out.print("Enter capacity: ");
                    int capacity = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter schedule: ");
                    String schedule = input.nextLine();

                    System.out.print("Enter credits: ");
                    int credits = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter prerequisites separated by ';' (or None): ");
                    String pre = input.nextLine();

                    ArrayList<String> prereqs = new ArrayList<>();
                    if (!pre.equalsIgnoreCase("None")) {
                        String[] arr = pre.split(";");
                        for (String p : arr) {
                            prereqs.add(p.trim());
                        }
                    }

                    Course newCourse = new Course(
                            code, title, desc, instructor, capacity,
                            0, schedule, credits, prereqs
                    );

                    admin.addCourse(newCourse);
                    System.out.println("Course added successfully!");
                    break;

                case 2:
                    System.out.print("Enter course code to remove: ");
                    String rCode = input.nextLine();

                    Course toRemove = DataManager.findCourse(rCode);

                    if (toRemove == null) {
                        System.out.println("Course not found.");
                    } else {
                        admin.removeCourse(toRemove);
                        System.out.println("Course removed.");
                    }
                    break;

                case 3:
                    System.out.println("Select user type:");
                    System.out.println("1. Student");
                    System.out.println("2. Advisor");
                    System.out.println("3. Admin");
                    int type = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter ID: ");
                    String uid = input.nextLine();

                    System.out.print("Enter name: ");
                    String uname = input.nextLine();

                    System.out.print("Enter phone: ");
                    String uphone = input.nextLine();

                    System.out.print("Enter address: ");
                    String uaddress = input.nextLine();

                    User newUser = null;

                    if (type == 1) {
                        System.out.print("Enter major: ");
                        String major = input.nextLine();
                        newUser = new Student(uid, uname, uphone, uaddress, major);

                    } else if (type == 2) {
                        newUser = new Advisor(uid, uname, uphone, uaddress);

                    } else if (type == 3) {
                        newUser = new Admin(uid, uname, uphone, uaddress);

                    } else {
                        System.out.println("Invalid type.");
                        break;
                    }

                    admin.addUser(newUser);

                    DataManager.saveUsersToFile("users_file.csv");

                    System.out.println("User added successfully!");
                    break;

                case 4:
                    System.out.print("Enter user ID to remove: ");
                    String remId = input.nextLine();

                    User urem = DataManager.findUser(remId);

                    if (urem == null) {
                        System.out.println("User not found.");
                    } else {
                        admin.removeUser(urem);

                        DataManager.saveUsersToFile("users_file.csv");

                        System.out.println("User removed.");
                    }
                    break;

                case 5:
                    admin.viewAllUsers();
                    break;
                case 6:
                    admin.viewAllCourses();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 7);
    }
*/
}
