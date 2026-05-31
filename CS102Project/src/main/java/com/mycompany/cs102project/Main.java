package com.mycompany.cs102project;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DataManager.loadCoursesFromFile("C:\\Users\\hgfdo\\OneDrive\\desktop\\CsProject\\courses_file.csv");
        DataManager.loadUsersFromFile("users_file.csv");

        Scanner input = new Scanner(System.in);

        while (true) {

            System.out.print("Enter your ID (or type EXIT to quit): ");
            String id = input.nextLine();

            if (id.equalsIgnoreCase("EXIT")) {
                System.out.println("Goodbye!");
                break;
            }

            User loggedUser = DataManager.findUser(id);

            if (loggedUser == null) {
                System.out.println("User not found!");
                continue;
            }

            if (loggedUser instanceof Student) {
                showStudentMenu((Student) loggedUser);

            } else if (loggedUser instanceof Advisor) {
                showAdvisorMenu((Advisor) loggedUser);

            } else if (loggedUser instanceof Admin) {
                showAdminMenu((Admin) loggedUser);
            }

        }

    }

    private static void showStudentMenu(Student student) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-------------- Student Menu --------------");
            System.out.println("Welcome, " + student.getName() + " (ID: " + student.getId() + ")");
            System.out.println("Please choose an option:");
            System.out.println("1. Register for a Course");
            System.out.println("2. Drop a Course");
            System.out.println("3. View My Registered Courses");
            System.out.println("4. View My Schedule");
            System.out.println("5. View All Available Courses");
            System.out.println("6. Submit a Special Request");
            System.out.println("7. Show Prerequisites (Recursive)");
            System.out.println("8. Logout");
            System.out.println("------------------------------------------");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter course code to register: ");
                    String regCode = input.nextLine();

                    Course regCourse = DataManager.findCourse(regCode);

                    if (regCourse == null) {
                        System.out.println("Course not found!");
                    } else {
                        if (student.registerForCourse(regCourse)) {
                            System.out.println("Successfully registered!");
                        } else {
                            System.out.println("Could not register. Check prerequisites, schedule, or capacity.");
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter course code to drop: ");
                    String dropCode = input.nextLine();

                    Course dropCourse = DataManager.findCourse(dropCode);

                    if (dropCourse == null) {
                        System.out.println("Course not found!");
                    } else {
                        if (student.dropCourse(dropCourse)) {
                            System.out.println("Successfully dropped!");
                        } else {
                            System.out.println("You are not registered in this course.");
                        }
                    }
                    break;

                case 3:
                    student.viewRegisteredCourses();
                    break;

                case 4:
                    student.viewSchedule();
                    break;

                case 5:
                    student.viewAvailableCourses();
                    break;

                case 6:
                    System.out.print("Enter course code for special request: ");
                    String reqCode = input.nextLine();
                    System.out.print("Enter your message: ");
                    String message = input.nextLine();

                    Course reqCourse = DataManager.findCourse(reqCode);

                    if (reqCourse == null) {
                        System.out.println("Course not found!");
                    } else {
                        student.submitSpecialReq(reqCourse, message);
                        System.out.println("Special request submitted!");
                    }
                    break;

                case 7:
                    System.out.print("Enter course code to view prerequisites: ");
                    String preCode = input.nextLine();
                    student.showPrerequisitesRecursive(preCode);
                    break;

                case 8:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 8);
    }

    private static void showAdvisorMenu(Advisor advisor) {

        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-------------- Advisor Menu --------------");
            System.out.println("Welcome, " + advisor.getName() + " (ID: " + advisor.getId() + ")");
            System.out.println("Please choose an option:");
            System.out.println("1. View All Assigned Students");
            System.out.println("2. View a Student's Courses");
            System.out.println("3. Approve a Special Request");
            System.out.println("4. Deny a Special Request");
            System.out.println("5. Logout");
            System.out.println("------------------------------------------");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    advisor.viewAllStudent();
                    break;

                case 2:
                    System.out.print("Enter student ID: ");
                    String sid = input.nextLine();

                    User u = DataManager.findUser(sid);

                    if (u instanceof Student) {
                        advisor.viewStudnetCourses((Student) u);
                    } else {
                        System.out.println("Student not found or not a student account.");
                    }
                    break;

                case 3:
                    System.out.print("Enter student ID: ");
                    String sid2 = input.nextLine();

                    User u2 = DataManager.findUser(sid2);

                    if (!(u2 instanceof Student)) {
                        System.out.println("Student not found.");
                        break;
                    }

                    Student target = (Student) u2;

                    if (target.getRequests().isEmpty()) {
                        System.out.println("This student has no requests.");
                        break;
                    }

                    System.out.println("\nSpecial Requests:");
                    for (int i = 0; i < target.getRequests().size(); i++) {
                        System.out.println(i + ": " + target.getRequests().get(i));
                    }

                    System.out.print("Enter request number to approve: ");
                    int reqNum = input.nextInt();
                    input.nextLine();

                    if (reqNum < 0 || reqNum >= target.getRequests().size()) {
                        System.out.println("Invalid request number.");
                        break;
                    }

                    SpecialRequest req = target.getRequests().get(reqNum);

                    System.out.print("Enter approval comment: ");
                    String comment = input.nextLine();

                    advisor.approveSpecialRequisite(req, comment);

                    System.out.println("Request approved!");
                    break;

                case 4:

                    System.out.print("Enter student ID: ");
                    String sid3 = input.nextLine();

                    User u3 = DataManager.findUser(sid3);

                    if (!(u3 instanceof Student)) {
                        System.out.println("Student not found.");
                        break;
                    }

                    Student target2 = (Student) u3;

                    if (target2.getRequests().isEmpty()) {
                        System.out.println("This student has no requests.");
                        break;
                    }

                    System.out.println("\nSpecial Requests:");
                    for (int i = 0; i < target2.getRequests().size(); i++) {
                        System.out.println(i + ": " + target2.getRequests().get(i));
                    }

                    System.out.print("Enter request number to deny: ");
                    int reqNum2 = input.nextInt();
                    input.nextLine();

                    if (reqNum2 < 0 || reqNum2 >= target2.getRequests().size()) {
                        System.out.println("Invalid request number.");
                        break;
                    }

                    SpecialRequest req2 = target2.getRequests().get(reqNum2);

                    System.out.print("Enter denial reason: ");
                    String comment2 = input.nextLine();

                    advisor.denySpecialRequisite(req2, comment2);

                    System.out.println("Request denied!");
                    break;

                case 5:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);
    }

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

}
