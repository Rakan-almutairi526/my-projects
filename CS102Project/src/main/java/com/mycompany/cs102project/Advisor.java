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

    public void viewAllStudent() {
        System.out.println("Students assigned to " + getName());
        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    public void viewStudnetCourses(Student student) {
        System.out.println("Student " + student.getName() + " courses");
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course.getCourseCode() + " - " + course.getCourseTitle());
        }
    }

    public void approveSpecialRequisite(SpecialRequest request, String comment) {
        request.approveRequest(comment);
    }

    public void denySpecialRequisite(SpecialRequest request, String comment) {
        request.denyRequest(comment);
    }

    @Override
    public void showRoleSummary() {
        System.out.println("---- Advisor Account Summary ---");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Students assigned: " + getStudentList().size());
        System.out.println("--------------------------------");
    }


    @Override
    public String toString() {
        return super.toString() + "\nRole: Advisor" + "\nAssigned Students: " + getStudentList().size();
    }

    @Override
    public void viewMenu() {

    }

    @Override
    public void performeAction(int choice) {

    }
}
