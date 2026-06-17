package com.mycompany.cs102project;

import java.util.ArrayList;

public class Advisor extends User {

    private ArrayList<Student> studentList;

    public Advisor(String id, String name, String phone, String address) {
        super(id, name, phone, address);
        this.studentList = new ArrayList<>();
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void addStudentToList(Student student) {
        studentList.add(student);
    }

    public void viewRegisteredStudent() {
        System.out.println("Students assigned to " + getName());
        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    public void viewStudentCourses(Student student) {
        System.out.println("Student " + student.getName() + " courses");
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course.getCourseCode() + " - " + course.getCourseTitle());
        }
    }

    public String approveSpecialRequisite(String requestId, String comment) {

        SpecialRequest request = DataManager.findRequest(requestId);
        if (request == null) return "REQUEST_NOT_FOUND";
        if (!request.getStatus().equalsIgnoreCase("Pending")) return "ALREADY_HANDLED";

        Student student = (Student) DataManager.findUser(request.getStudentId());
        Course course = DataManager.findCourse(request.getCourseCode());

        request.setStatus("Approved");
        request.setAdvisorComment(comment);

        student.getRegisteredCourses().add(course);
        course.getStudentList().add(student);
        course.setEnrolledStudentCount(course.getEnrolledStudentCount() + 1);
        return "SUCCESS";
    }

    public String denySpecialRequisite(String requestId, String comment) {

        SpecialRequest request = DataManager.findRequest(requestId);
        if (request == null) return "REQUEST_NOT_FOUND";
        if (!request.getStatus().equalsIgnoreCase("Pending")) return "ALREADY_HANDLED";

        request.setStatus("Declined");
        request.setAdvisorComment(comment);
        return "SUCCESS";
    }

    public void showStudentRequests() {

        for (Student student : getStudentList()) {
            for (SpecialRequest request : student.getRequests()) {
                System.out.println(request);
            }
        }
    }

    @Override
    public void showRoleSummary() {
        System.out.println("---- Advisor Account ---");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Students assigned: " + getStudentList().size());
        System.out.println("--------------------------------");
    }


    @Override
    public String toString() {
        return super.toString() + " Role: Advisor" + " Assigned Students: " + getStudentList().size();
    }

}
