package com.mycompany.cs102project;

import java.util.ArrayList;

public class Course {

    private String courseCode;
    private String courseTitle;
    private String courseDescription;
    private String instructorId;
    private int capacity;
    private int enrolledStudentCount;
    private String schedule;
    private int credits;
    private ArrayList<String> prerequisites;

    public Course(String courseCode, String courseTitle, String CourseDescription, String instructorId, int capacity, int enrolledStudentCount, String schedule, int credits, ArrayList<String> prerequisites) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseDescription = CourseDescription;
        this.instructorId = instructorId;
        this.capacity = capacity;
        this.enrolledStudentCount = enrolledStudentCount;
        this.schedule = schedule;
        this.credits = credits;
        this.prerequisites = prerequisites;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledStudentCount() {
        return enrolledStudentCount;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getCredits() {
        return credits;
    }

    public ArrayList<String> getPrerequisites() {
        return prerequisites;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourseDescription(String CourseDescription) {
        this.courseDescription = CourseDescription;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setEnrolledStudentCount(int enrolledStudentCount) {
        this.enrolledStudentCount = enrolledStudentCount;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addPrerequisites(String courseCode) {
        this.prerequisites.add(courseCode);
    }

    public boolean hasAvailableSeat() {

        if (enrolledStudentCount < capacity) {
            return true;
        }

        return false;
    }

    public boolean enrollOneStudent() {
        if (hasAvailableSeat()) {
            enrolledStudentCount++;
            return true;
        }
        return false;
    }

    public boolean dropOneStudent() {
        if (enrolledStudentCount > 0) {
            enrolledStudentCount--;
            return true;
        }
        return false;
    }

    public void showAllPrerequisites() {
        System.out.println("prerequisites for " + this.courseCode);
        listPrerequisitesRecursive(this.courseCode, 1);
    }

    private void listPrerequisitesRecursive(String courseCode, int level) {
        Course course = DataManager.findCourse(courseCode);
        if (course == null || course.getPrerequisites().isEmpty()) {
            return;
        }
        for (String prerequisite : course.getPrerequisites()) {

            for (int i = 1; i < level; i++){
                System.out.print("   ");
            }
            System.out.println("- " + prerequisite);
            listPrerequisitesRecursive(prerequisite, level + 1);
        }
    }

    @Override
    public String toString() {
        return "Course code: " + getCourseCode() + " Title: " + getCourseTitle() + "\n" + "Instructor ID: " + getInstructorId() + "\n" + "Credits: " + getCredits() + "\n" + "Capacity: " + getCapacity() + "\n" + "Schedule: " + getSchedule() + "\n";
    }

}
