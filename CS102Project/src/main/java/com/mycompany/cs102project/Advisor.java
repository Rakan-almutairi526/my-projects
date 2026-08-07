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
        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    public void viewStudentCourses(Student student) {
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course.getCourseCode() + " - " + course.getCourseTitle());
        }
    }

    public void approveSpecialRequisite(SpecialRequest request, String comment) {
        Student student = (Student) DataManager.findUser(request.getStudentId());
        Course course = DataManager.findCourse(request.getCourseCode());

        request.setStatus("Approved");
        request.setAdvisorComment(comment);

        student.getRegisteredCourses().add(course);
        course.getStudentList().add(student);
        course.setEnrolledStudentCount(course.getEnrolledStudentCount() + 1);
    }

    public void denySpecialRequisite(SpecialRequest request, String comment) {
        request.setStatus("Declined");
        request.setAdvisorComment(comment);
    }

    public boolean findStudent(String Id){
        for (Student student : getStudentList()){
            if (student.getId().equalsIgnoreCase(Id)) return true;
        }
        return false;
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
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Address: " + getAddress());
        System.out.println("ID: " + getId());
        System.out.println("Students assigned: " + getStudentList().size());
        System.out.println("--------------------------------");
    }


    @Override
    public String toString() {
        return super.toString() + " Role:Advisor" + " Assigned Students:" + getStudentList().size();
    }

}
