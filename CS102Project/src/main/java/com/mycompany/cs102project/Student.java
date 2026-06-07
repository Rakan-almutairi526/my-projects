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

    private String getScheduleConflicts(Course course) {

        String courses = "";

        for (Course c : getRegisteredCourses()) {

            if (c.getSchedule().equalsIgnoreCase(course.getSchedule())) {
                courses += " " + c.getCourseCode();
            }
        }

        return courses;
    }

    private boolean hasCompletedPrerequisites(Course course) {


        boolean found;
        for (String prerequist : course.getPrerequisites()) {

            found = false;
            for (Course completedCourse : getCompletedCourses()) {
                if (completedCourse.getCourseCode().equalsIgnoreCase(prerequist)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    private String getMissingPrerequisite(Course course) {

        String missingPre = "";
        boolean found;

        for (String prereq : course.getPrerequisites()) {

            found = false;
            for (Course c : getCompletedCourses()) {
                if (c.getCourseCode().equalsIgnoreCase(prereq)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                missingPre += " " + prereq;
            }
        }
        return missingPre;
    }

    private boolean isCourseRegistered(Course course) {

        for (Course c : getRegisteredCourses()) {
            if (c.getCourseCode().equalsIgnoreCase(course.getCourseCode())) return true;
        }

        return false;
    }

    private String registerForCourse(String courseCode) {

        Course course = DataManager.findCourse(courseCode);

        if (course == null) {
            return "NOT_FOUND";
        }
        if (isCourseRegistered(course)) {
            return "ALREADY_REGISTERED";
        }
        if (!course.hasAvailableSeat()) {
            return "NOT_ENOUGH_SEATS";
        }

        if (hasScheduleConflicts(course)) {
            return "SCHEDULE_CONFLICT";
        }

        if (!hasCompletedPrerequisites(course)) {
            return "MISSING_PREREQUISITE";
        }

        course.enrollOneStudent();
        registeredCourses.add(course);
        DataManager.saveUsersToFile("users.txt");
        DataManager.saveCoursesToFile("courses.txt");
        return "SUCCESS";
    }

    private String dropCourse(String courseCode) {

        Course course = DataManager.findCourse(courseCode);

        if (course == null) return "NOT_FOUND";

        if (isCourseRegistered(course)) {
            registeredCourses.remove(course);
            course.dropOneStudent();
            DataManager.saveUsersToFile("users.txt");
            DataManager.saveCoursesToFile("courses.txt");
            return "SUCCESS";
        }
        return "NOT_REGISTERED";
    }

    private void viewRegisteredCourses() {
        System.out.println("Student" + getName() + "Courses");
        for (Course course : registeredCourses) {
            System.out.println(course.getCourseCode() + " - " + course.getCourseTitle());
        }
    }


    private void viewSchedule() {
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


    private void viewAvailableCourses() {
        for (Course course : DataManager.courseList) {
            System.out.print(course);
            if (course.hasAvailableSeat()) {
                System.out.println("no available seats");
            } else {
                System.out.println("available seats");
            }
        }
    }

    private String submitSpecialReq(String courseCode, String message) {

        Course course = DataManager.findCourse(courseCode);

        if (course == null) return "NOT_FOUND";


        SpecialRequest request = new SpecialRequest(getId(), course.getCourseCode(), message);
        requests.add(request);
        return "SUCCESS";
    }

    private void ShowSpecialRequest(){

        if (requests.isEmpty()){
            System.out.println("No special Found");
        }else {

            for (SpecialRequest request : getRequests()){

                System.out.println(request);
                if (request.getAdvisorcomment() == null){
                    System.out.println("Advisor has not replied yet");
                }else {
                    System.out.println(request.getAdvisorcomment());
                }
            }

        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nMajor: " + getMajor() + "\nRegistered Course: " + registeredCourses.size();
    }

    @Override
    public void viewMenu() {
        System.out.println("\n-------------- Student Menu --------------");
        System.out.println("Welcome, " + getName() + " (ID: " + getId() + ")");
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

    @Override
    public void performAction(int choice) {

    }
}
