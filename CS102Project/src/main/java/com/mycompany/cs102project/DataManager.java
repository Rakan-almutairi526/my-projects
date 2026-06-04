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
                    String[] prereqArray = parts[7].split("|");
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

                        String[] regcourses = parts[6].split("|");

                        for(String coures: regcourses){
                            
                        }
                    }







                    userList.add(student);
                } else if (role.equalsIgnoreCase("Advisor")) {
                    userList.add(new Advisor(id, name, phone, address));
                } else if (role.equalsIgnoreCase("Admin")) {
                    userList.add(new Admin(id, name, phone, address));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
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
