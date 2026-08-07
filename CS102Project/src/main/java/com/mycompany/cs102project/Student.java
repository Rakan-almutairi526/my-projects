package com.mycompany.cs102project;

import java.util.ArrayList;

public class Student extends User {

    private String major;
    private String advisorId;
    private ArrayList<Course> registeredCourses;
    private ArrayList<SpecialRequest> requests;
    private ArrayList<String> completedCourses;

    public Student(String id, String name, String phone, String address, String major, String advisorId) {
        super(id, name, phone, address);
        this.major = major;
        this.advisorId = advisorId;
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

    public ArrayList<String> getCompletedCourses() {
        return completedCourses;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }

    private boolean hasScheduleConflicts(Course course) {


        for (Course regCourse : registeredCourses) {
            if (regCourse.getSchedule().equalsIgnoreCase(course.getSchedule())) {
                return true;
            }
        }
        return false;
    }

    public String getScheduleConflicts(String courseCode) {

        Course course = DataManager.findCourse(courseCode);
        String courses = "";

        for (Course c : getRegisteredCourses()) {

            if (c.getSchedule().equalsIgnoreCase(course.getSchedule())) {
                courses += " " + c.getCourseCode();
            }
        }

        return courses.trim();
    }

    private boolean hasCompletedPrerequisites(Course course) {


        boolean found;
        for (String prerequist : course.getPrerequisites()) {

            found = false;
            for (String completedCourse : getCompletedCourses()) {
                if (completedCourse.equalsIgnoreCase(prerequist)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    public String getMissingPrerequisite(String courseCode) {


        Course course = DataManager.findCourse(courseCode);
        String missingPre = "";
        boolean found;

        for (String prereq : course.getPrerequisites()) {

            found = false;
            for (String c : getCompletedCourses()) {
                if (c.equalsIgnoreCase(prereq)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                missingPre += " " + prereq;
            }
        }
        return missingPre.trim();
    }

    private boolean isCourseRegistered(Course course) {

        for (Course c : getRegisteredCourses()) {
            if (c.getCourseCode().equalsIgnoreCase(course.getCourseCode())) return true;
        }

        return false;
    }
    private boolean hasCompletedCourse(Course course) {

        for (String completedCourseCode : completedCourses) {

            if (completedCourseCode.equalsIgnoreCase(
                    course.getCourseCode()
            )) {
                return true;
            }
        }

        return false;
    }
    public String registerForCourse(String courseCode) {

        Course course = DataManager.findCourse(courseCode);

        if (course == null) {
            return "NOT_FOUND";
        }
        if (hasCompletedCourse(course)) {
            return "ALREADY_COMPLETED";
        }
        if (isCourseRegistered(course)) {
            return "ALREADY_REGISTERED";
        }
        if (!hasCompletedPrerequisites(course)) {
            return "MISSING_PREREQUISITE";
        }
        if (!course.hasAvailableSeat()) {
            return "NOT_ENOUGH_SEATS";
        }
        if (hasScheduleConflicts(course)) {
            return "SCHEDULE_CONFLICT";
        }


        course.enrollOneStudent(this);
        registeredCourses.add(course);
        return "SUCCESS";
    }

    public String dropCourse(String courseCode) {

        Course course = DataManager.findCourse(courseCode);

        if (course == null) return "NOT_FOUND";

        if (isCourseRegistered(course)) {
            registeredCourses.remove(course);
            course.dropOneStudent(this);
            return "SUCCESS";
        }
        return "NOT_REGISTERED";
    }

    public void viewRegisteredCourses() {
        for (Course course : registeredCourses) {
            System.out.println(course.getCourseCode() + " - " + course.getCourseTitle());
        }
    }


    public void viewSchedule() {
        for (Course course : registeredCourses) {
            System.out.println(course.getCourseTitle() + " - " + course.getSchedule());
        }
    }

    @Override
    public void showRoleSummary() {
        System.out.println("---- Student Account ---");
        System.out.println("Name: " + getName());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Address: " + getAddress());
        System.out.println("ID: " + getId());
        System.out.println("Major: " + getMajor());
        System.out.println("Advisor Id: " + getAdvisorId());
        System.out.println("--------------------------------");
    }


    public void viewAvailableCourses() {
        for (Course course : DataManager.courseList) {
            System.out.print(course);
            if (course.hasAvailableSeat()) {
                System.out.println(" available seats");
            } else {
                System.out.println(" no available seats");
            }
        }
    }

    public void submitSpecialReq(String courseCode, String message) {
        SpecialRequest request = new SpecialRequest(getId(), courseCode, message);
        requests.add(request);
        DataManager.specialRequestsList.add(request);
    }

    public void ShowSpecialRequest() {

        if (requests.isEmpty()) {
            System.out.println("No special request Found");
        } else {
            for (SpecialRequest request : getRequests()) {
                System.out.println(request);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + " Role:Student " + " Major:" + getMajor() + " Registered Course:" + registeredCourses.size();
    }
}
