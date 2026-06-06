package com.mycompany.cs102project;

import java.util.ArrayList;

public class Student extends User {

    private String major;
    private ArrayList<Course> registeredCourses;
    private ArrayList<SpecialRequest> requests;
    private ArrayList<Course> completedCourses;

    public Student(String id, String name, String phone, String address, String major) {
        super(id, name, phone, address);
        this.major = major;
        this.registeredCourses = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.completedCourses = new ArrayList<>();
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public ArrayList<SpecialRequest> getRequests() {
        return requests;
    }

    public String getMajor() {
        return major;
    }

    public ArrayList<Course> getCompletedCourses() {
        return completedCourses;
    }

    // private bc only needed here not in any other class
    private boolean hasScheduleConflicts(Course newCourse) {
        for (Course course : registeredCourses) {
            if (course.getSchedule().equalsIgnoreCase(newCourse.getSchedule())) {
                return true;
            }
        }
        return false;
    }

    // same thing here
    private boolean hasCompletedPrerequists(Course course) {
        for (String prerequist : course.getPrerequisites()) {
            if (!completedCourses.contains(prerequist)) {
                return false;
            }
        }
        return true;
    }

    public boolean registerForCourse(Course course) {
        if (getRegisteredCourses().contains(course)) {
            return false;
        }
        if (!course.hasAvailableSeat()) {
            return false;
        }

        if (hasScheduleConflicts(course)) {
            return false;
        }

        if (!hasCompletedPrerequists(course)) {
            return false;
        }

        if (course.enrollOneStudent()) {
            registeredCourses.add(course);
            DataManager.saveUsersToFile("users_file.csv");
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropOneStudent();
            DataManager.saveUsersToFile("users_file.csv");
            return true;
        }
        return false;
    }

    public void viewRegisteredCourses() {
        System.out.println("Student" + getName() + "Courses");
        for (Course course : registeredCourses) {
            System.out.println(course.getCourseCode() + " - " + course.getCourseTitle());
        }
    }


    public void viewSchedule() {
        System.out.println("Student" + getName() + "Schedule");
        for (Course course : registeredCourses) {
            System.out.println(course.getCourseTitle() + " - " + course.getSchedule());
        }
    }

    @Override
    public void showRoleSummary() {
        System.out.println("---- Student Account Summary ---");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Major: " + getMajor());
        System.out.println("Registered Courses: " + registeredCourses.size());
        System.out.println("--------------------------------");
    }


    public void viewAvailableCourses() {
        try {
            for (Course course : DataManager.courseList) {
                System.out.println(course);
            }
        } catch (Exception e) {
            System.out.println("System does not support this yet..");
        }
    }

    public void submitSpecialReq(Course course, String message) {
        SpecialRequest request = new SpecialRequest(getId(), course.getCourseCode(), message);
        requests.add(request);
    }


    public void showPrerequisitesRecursive(String courseCode) {
        System.out.println("prerequisites for " + courseCode);
        listPrerequisitesRecursive(courseCode);
    }

    private void listPrerequisitesRecursive(String courseCode) {
        Course course = DataManager.findCourse(courseCode);
        if (course == null) {
            return;
        }
        for (String prerequisite : course.getPrerequisites()) {
            System.out.println("- " + prerequisite);
            showPrerequisitesRecursive(prerequisite);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nMajor: " + getMajor() + "\nRegistered Course: " + registeredCourses.size();
    }

    @Override
    public void viewMenu() {
    }

    @Override
    public void performAction(int choice) {

    }
}
