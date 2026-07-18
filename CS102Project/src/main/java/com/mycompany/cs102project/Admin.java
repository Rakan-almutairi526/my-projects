package com.mycompany.cs102project;

import java.util.ArrayList;

public class Admin extends User {

    public Admin(String id, String name, String phone, String address) {
        super(id, name, phone, address);
    }

    public void addCourse(Course course) {
        DataManager.courseList.add(course);
    }

    public void removeCourse(Course course) {

        for (Student student : course.getStudentList()){
            student.dropCourse(course.getCourseCode());
        }
        for (Course course1 : DataManager.courseList){
            if (course1.getPrerequisites().contains(course.getCourseCode())){
                course1.getPrerequisites().remove(course.getCourseCode());
            }
        }
        DataManager.courseList.remove(course);
    }

    public void addUser(User user) {
        DataManager.userList.add(user);
    }

    public void removeUser(User user) {

        if (user instanceof Student student){
            removeStudent(student);
        }
        if (user instanceof Advisor advisor){
            removeAdvisor(advisor);
        }
    }

    private void removeStudent(Student student){
        ArrayList<Course> registeredCourses = new ArrayList<>(student.getRegisteredCourses());
        for (Course course : registeredCourses){
            student.dropCourse(course.getCourseCode());
        }
        User advior = DataManager.findUser(student.getAdvisorId());
        if (advior instanceof Advisor advisor){
            advisor.getStudentList().remove(student);
        }
        for (SpecialRequest sr : student.getRequests()){
            DataManager.specialRequestsList.remove(sr);
        }
        DataManager.userList.remove(student);
    }

    private void removeAdvisor(Advisor advisor){
        for (Student student : advisor.getStudentList()){
            student.setAdvisorId("Yet to be defined");
        }
        for (Course course : DataManager.courseList){
            if (course.getInstructorId().equalsIgnoreCase(advisor.getId())) course.setInstructorId("Yet to be defined");
        }
        DataManager.userList.remove(advisor);
    }
    public void updateCourseInfo(Course course,String info, int choice){

        switch (choice){

            case 1:
                course.setCourseCode(info);
                break;
            case 2:
                course.setCourseTitle(info);
                break;
            case 3:
                course.setCourseDescription(info);
                break;
            case 4:
                course.setInstructorId(info);
                break;
            case 5:
                course.setCapacity(Integer.parseInt(info));
                break;
            case 6:
                course.setSchedule(info);
                break;
            case 7:
                course.setCredits(Integer.parseInt(info));
                break;
            case 8:
                course.getPrerequisites().add(info);
                break;
            case 9: course.getPrerequisites().remove(info);
        }
    }

    public void viewAllUsers() {
        for (User user : DataManager.userList) {
            System.out.println(user);
        }
    }

    public void viewAllCourses() {
        for (Course course : DataManager.courseList) {
            System.out.println(course);
        }
    }

    @Override
    public void showRoleSummary() {
        System.out.println("---- Admin Account ---");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("--------------------------------");
    }

    @Override
    public String toString() {
        return super.toString() + " Role: Admin";
    }

}
