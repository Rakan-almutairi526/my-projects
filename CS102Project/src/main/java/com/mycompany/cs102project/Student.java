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

    private boolean hasScheduleConflicts(Course course) {


        for (Course regCourse : registeredCourses) {
            if (regCourse.getSchedule().equalsIgnoreCase(course.getSchedule())) {
               return true;
            }
        }
        return false;
    }

    private String getScheduleConflicts(Course course){

        String courses = "";

        for (Course c : getRegisteredCourses()){

            if (c.getSchedule().equalsIgnoreCase(course.getSchedule())){
                courses += " " + c.getCourseCode();
            }
        }

        return courses;
    }

    private boolean hasCompletedPrerequisites(Course course) {


        boolean found;
        for (String prerequist : course.getPrerequisites()) {

            found = false;
            for (Course completedCourse : getCompletedCourses()){
                if (completedCourse.getCourseCode().equalsIgnoreCase(prerequist)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    private String getMissingPrerequisite(Course course){

        String missingPre = "";
        boolean found;

        for (String prereq : course.getPrerequisites()){

            found = false;
            for (Course c : getCompletedCourses()){
                if (c.getCourseCode().equalsIgnoreCase(prereq)){
                   found = true;
                   break;
                }
            }
            if (!found){
                missingPre += " " + prereq;
            }
        }
        return missingPre;
    }

    public boolean registerForCourse(String courseCode) {

        Course course = DataManager.findCourse(courseCode);

        if (course == null){
            System.out.println("invalid input, make sure you inter a correct course code");
            System.out.println("Registration Failed");
            return false;
        }
        if (getRegisteredCourses().contains(course)) {
            System.out.println("you are already registered in this course");
            System.out.println("Registration Failed");
            return false;
        }
        if (!course.hasAvailableSeat()) {
            System.out.println("course does not have a seat");
            System.out.println("Registration Failed");
            return false;
        }

        if (hasScheduleConflicts(course)) {
            System.out.println("Registration Failed");
            return false;
        }

        if (!hasCompletedPrerequisites(course)) {
            System.out.println("Registration Failed");
            return false;
        }

        course.enrollOneStudent();
        registeredCourses.add(course);
        DataManager.saveUsersToFile("users.txt");

        return true;
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
