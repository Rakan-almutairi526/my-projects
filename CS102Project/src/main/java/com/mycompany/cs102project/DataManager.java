package com.mycompany.cs102project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManager {

    public static ArrayList<Course> courseList = new ArrayList<>();
    public static ArrayList<User> userList = new ArrayList<>();

    public static void loadCoursesFromFile(String filename) {

        try {
            Scanner InFile = new Scanner(new File(filename));
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
                    String[] prereqArray = parts[7].split("\\|");
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
            System.out.println("File not found: " + filename);
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

    public static void loadUsersFromFile(String filename) {
        try {
            Scanner inFile = new Scanner(new File(filename));

            while (inFile.hasNextLine()) {
                String line = inFile.nextLine();

                if (line.trim().isEmpty() || line.startsWith("\"") || line.startsWith("Role")) {
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
            System.out.println("File not found: " + filename);
        }
    }

    public static void loadSpecialRequestFromFile(String fileName) throws FileNotFoundException {

        Scanner inFile = new Scanner(new File(fileName));
        inFile.nextLine();

        while (inFile.hasNextLine()) {

            String line = inFile.nextLine();

            String[] parts = line.split(",");

            String studentId = parts[0];
            String courseCode = parts[1];
            String message = parts[2];
            String status = parts[3];
            String advisorComment = parts[4];

            SpecialRequest temp = new SpecialRequest(studentId, courseCode, message);

            temp.setAdvisorcomment(advisorComment);
            temp.setStatus(status);

            Student s = (Student) findUser(studentId);
            s.getRequests().add(temp);
        }

    }

    public static void saveCourses(String fileName) {

        try {
            PrintWriter Pr = new PrintWriter(new File(fileName));
            Pr.println("Course code, course title, course description, instructor ID, capacity, schedule, prerequisites, enrolled students, credits");
            for (Course course : courseList) {
                Pr.print(course.getCourseCode() + "," + course.getCourseTitle() + "," + course.getCourseDescription() + "," + course.getInstructorId() + "," + course.getCapacity() + "," + course.getSchedule() + ",");

                if (course.getPrerequisites().isEmpty()) Pr.print("None");

                int i = 1;
                for (String prereq : course.getPrerequisites()){
                    Pr.print(prereq);
                    if (i < course.getPrerequisites().size()) Pr.print("|");
                    i++;
                }
                Pr.println( "," + course.getEnrolledStudentCount() + "," + course.getCredits());

            }
            Pr.close();
        } catch (FileNotFoundException e) {
            System.out.println("error occurred");
        }


    }


    public static void saveUsersToFile(String filename) {
        try {
            PrintWriter Pr = new PrintWriter(new File(filename));

            for (User user : userList) {

                if (user instanceof Student) {
                    Student s = (Student) user;


                    StringBuilder reg = new StringBuilder("REG:");
                    if (s.getRegisteredCourses().isEmpty()) {
                        reg.append("None");
                    } else {
                        for (int i = 0; i < s.getRegisteredCourses().size(); i++) {
                            reg.append(s.getRegisteredCourses().get(i).getCourseCode());
                            if (i < s.getRegisteredCourses().size() - 1) {
                                reg.append(";");
                            }
                        }
                    }

                    Pr.println("Student," + s.getId() + "," + s.getName() + "," + s.getPhone() + ","
                            + s.getAddress() + "," + s.getMajor() + "," + reg.toString());
                } else if (user instanceof Advisor) {
                    Advisor a = (Advisor) user;
                    Pr.println("Advisor," + a.getId() + "," + a.getName() + "," + a.getPhone() + "," + a.getAddress());
                } else if (user instanceof Admin) {
                    Admin a = (Admin) user;
                    Pr.println("Admin," + a.getId() + "," + a.getName() + "," + a.getPhone() + "," + a.getAddress());
                }
            }

            Pr.close();
        } catch (Exception e) {
            System.out.println("Error saving users file.");
        }
    }

}
