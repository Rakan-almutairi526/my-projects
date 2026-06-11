package com.mycompany.cs102project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManager {

    public static ArrayList<Course> courseList = new ArrayList<>();
    public static ArrayList<User> userList = new ArrayList<>();
    public static ArrayList<SpecialRequest> specialRequestsList = new ArrayList<>();

    public static void loadCoursesFromFile(String fileName) {

        try {
            Scanner InFile = new Scanner(new File(fileName));
            InFile.nextLine();
            while (InFile.hasNextLine()) {
                String line = InFile.nextLine();
                String[] parts = line.split(",");

                String code = parts[0];
                String title = parts[1];
                String desc = parts[2];
                String instructorID = parts[3];

                int capacity = Integer.parseInt(parts[4]);
                String schedule = parts[5];

                ArrayList<String> prereqList = new ArrayList<>();

                if (!parts[6].equalsIgnoreCase("None")) {
                    String[] prereqArray = parts[6].split("\\|");
                    for (String p : prereqArray) {
                        prereqList.add(p.trim());
                    }
                }

                int enrolled = Integer.parseInt(parts[7]);
                int credits = Integer.parseInt(parts[8]);

                Course course = new Course(code, title, desc, instructorID, capacity, enrolled, schedule, credits, prereqList);

                courseList.add(course);

            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }

    }

    public static Course findCourse(String code) {
        for (Course course : courseList) {
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    public static User findUser(String id) {
        for (User user : userList) {
            if (user.getId().equalsIgnoreCase(id)) {
                return user;
            }
        }
        return null;
    }

    public static SpecialRequest findRequest(String Id){

        for (SpecialRequest request : specialRequestsList){
            if (request.getId().equalsIgnoreCase(Id)) return request;
        }
        return null;
    }

    public static void loadUsersFromFile(String fileName) {
        try {
            Scanner inFile = new Scanner(new File(fileName));

            while (inFile.hasNextLine()) {
                String line = inFile.nextLine();

                if (line.trim().isEmpty() || line.startsWith("-") || line.startsWith("Role")) {
                    continue;
                }

                String[] parts = line.split(",");

                String role = parts[0];
                String id = parts[1];
                String name = parts[2];
                String phone = parts[3];
                String address = parts[4];

                if (role.equalsIgnoreCase("Student")) {
                    String major = parts[5];

                    Student student = new Student(id, name, phone, address, major);

                    if (!parts[6].equalsIgnoreCase("None")) {

                        String[] regcourses = parts[6].split("\\|");

                        for (String regcourse : regcourses) {
                            student.getRegisteredCourses().add(findCourse(regcourse));
                        }

                    }

                    if (!parts[7].equalsIgnoreCase("None")) {
                        String[] completedCourses = parts[7].split("\\|");

                        for (String compcourse : completedCourses) {
                            student.getCompletedCourses().add(findCourse(compcourse));
                        }
                    }

                    userList.add(student);
                } else if (role.equalsIgnoreCase("Advisor")) {

                    Advisor advisor = new Advisor(id, name, phone, address);

                    if (!parts[5].equalsIgnoreCase("None")) {

                        String[] studentIds = parts[5].split("\\|");

                        for (String studentId : studentIds) {
                            advisor.addStudentToList((Student) findUser(studentId));
                        }
                    }

                    userList.add(advisor);

                } else if (role.equalsIgnoreCase("Admin")) {
                    userList.add(new Admin(id, name, phone, address));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    public static void loadSpecialRequestFromFile(String fileName) {

        try {
            Scanner inFile = new Scanner(new File(fileName));
            inFile.nextLine();

            while (inFile.hasNextLine()) {

                String line = inFile.nextLine();

                String[] parts = line.split(",");

                String requestId = parts[0];
                String studentId = parts[1];
                String courseCode = parts[2];
                String message = parts[3];
                String status = parts[4];
                String advisorComment = parts[5];

                SpecialRequest temp = new SpecialRequest(requestId, studentId, courseCode, message, status, advisorComment);

                specialRequestsList.add(temp);
                Student s = (Student) findUser(studentId);
                s.getRequests().add(temp);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    public static void loadEnrollments(String fileName) {

        try {

            Scanner inFile = new Scanner(fileName);
            inFile.nextLine();

            while (inFile.hasNextLine()) {

                String line = inFile.nextLine();
                String[] parts = line.split("\\|");

                Student s = (Student) findUser(parts[1]);

                if (parts[2].equalsIgnoreCase("None")) continue;

                String[] coursesCode = parts[2].split(",");

                for (String courseCode : coursesCode) {

                    Course course = findCourse(courseCode);
                    s.getRegisteredCourses().add(course);
                    course.getStudentList().add(s);
                }
            }

        } catch (Exception e) {
            System.out.println("File not found");
            ;
        }
    }

    public static void saveEnrollments(String fileName) {

        try {
            PrintWriter Pr = new PrintWriter(new File(fileName));

            Pr.println("StudentIds|Registered Courses");

            int i = 1;
            for (User user : userList) {

                if (user instanceof Student) {
                    Student temp = (Student) user;
                    Pr.print(temp.getId() + "|");

                    if (temp.getRegisteredCourses().isEmpty()) {
                        Pr.println("None");
                        continue;
                    }

                    for (Course course : temp.getRegisteredCourses()) {
                        Pr.print(course.getCourseCode());

                        if (i < temp.getRegisteredCourses().size()) Pr.print(",");
                        i++;
                    }
                    Pr.println();
                }

            }
            Pr.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public static void saveCoursesToFile(String fileName) {

        try {
            PrintWriter Pr = new PrintWriter(new File(fileName));
            Pr.println("Course code, course title, course description, instructor ID, capacity, schedule, prerequisites, enrolled students, credits");
            for (Course course : courseList) {
                Pr.print(course.getCourseCode() + "," + course.getCourseTitle() + "," + course.getCourseDescription() + "," + course.getInstructorId() + "," + course.getCapacity() + "," + course.getSchedule() + ",");

                if (course.getPrerequisites().isEmpty()) Pr.print("None");

                int i = 1;
                for (String prereq : course.getPrerequisites()) {
                    Pr.print(prereq);
                    if (i < course.getPrerequisites().size()) Pr.print("|");
                    i++;
                }
                Pr.println("," + course.getEnrolledStudentCount() + "," + course.getCredits());

            }
            Pr.close();
        } catch (FileNotFoundException e) {
            System.out.println("error occurred");
        }

    }

    public static void saveUsersToFile(String filename) {
        try {
            PrintWriter Pr = new PrintWriter(new File(filename));

            Pr.println("-Student-");
            Pr.println("Role, Id, Name, phone, address, major, registered courses, completed courses");

            for (User user : userList) {
                if (user instanceof Student) {

                    Student s = (Student) user;

                    Pr.print("Student," + s.getId() + "," + s.getName() + "," + s.getPhone() + "," + s.getAddress() + "," + s.getMajor() + ",");

                    if (s.getRegisteredCourses().isEmpty()) Pr.print("None");
                    int i = 1;
                    for (Course regcourse : s.getRegisteredCourses()) {
                        Pr.print(regcourse.getCourseCode());
                        if (i < s.getRegisteredCourses().size()) Pr.print("|");
                        i++;
                    }

                    Pr.print(",");

                    if (s.getCompletedCourses().isEmpty()) Pr.print("None");
                    int i2 = 1;
                    for (Course compcourse : s.getCompletedCourses()) {
                        Pr.print(compcourse.getCourseCode());
                        if (i2 < s.getCompletedCourses().size()) Pr.print("|");
                        i2++;
                    }
                    Pr.println();
                }
            }

            Pr.println("-Advisor-");
            Pr.println("Role, Id, Name, Phone, Address, Assigned Students Ids");

            for (User user : userList) {
                if (user instanceof Advisor) {

                    Advisor a = (Advisor) user;
                    Pr.print("Advisor," + a.getId() + "," + a.getName() + "," + a.getPhone() + "," + a.getAddress() + ",");

                    if (a.getStudentList().isEmpty()) Pr.print("None");

                    int i = 1;
                    for (Student s : a.getStudentList()) {
                        Pr.print(s.getId());
                        if (i < a.getStudentList().size()) Pr.print("|");
                        i++;
                    }
                    Pr.println();

                }
            }

            Pr.println("-Admin-");
            Pr.println("Role, Id, Name, Phone, Address");

            for (User user : userList) {
                if (user instanceof Admin) {
                    Admin a = (Admin) user;
                    Pr.println("Admin," + a.getId() + "," + a.getName() + "," + a.getPhone() + "," + a.getAddress());
                }
            }
            Pr.close();
        } catch (Exception e) {
            System.out.println("Error saving users file.");
        }
    }

    public static void saveSpecialRequestsToFile(String fileName) {

        try {
            PrintWriter Pr = new PrintWriter(new File(fileName));
            Pr.println("Request Id,Student Id, course code, message, status, advisor comment");
            for (SpecialRequest sp : specialRequestsList) {
                Pr.println( sp.getId() + "," + sp.getStudentId() + "," + sp.getCourseCode() + "," + sp.getMessage() + "," + sp.getStatus() + "," + sp.getAdvisorComment());
            }
            Pr.close();
        } catch (FileNotFoundException e) {
            System.out.println("error occurred");
        }

    }
}
