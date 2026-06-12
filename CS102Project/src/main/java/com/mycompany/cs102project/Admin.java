package com.mycompany.cs102project;

import java.util.ArrayList;

public class Admin extends User {

    public Admin(String id, String name, String phone, String address) {
        super(id, name, phone, address);
    }

    public void addCourse(Course course) {
        DataManager.courseList.add(course);
    }

    public String removeCourse(String courseCode) {

        Course course = DataManager.findCourse(courseCode);
        if (course == null) return "COURSE_NOT_FOUND";

        DataManager.courseList.remove(course);
        return "SUCCESS";
    }

    public void addUser(User user) {
        DataManager.userList.add(user);
    }

    public String removeUser(String userId) {

        User user = DataManager.findUser(userId);

        if (user == null) return "USER_NOT_FOUND";

        DataManager.userList.remove(user);
        return "SUCCESS";
    }


    public String updateCourseInfo(String courseCode,String info, int choice){

        Course course = DataManager.findCourse(courseCode);
        if (course == null) return "COURSE_NOT_FOUND";
        if (choice > 8 || choice < 0) return "INVALID_CHOICE";

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
                Course prereq = DataManager.findCourse(info);
                if (prereq == null) return "PREREQUISITE_NOT_FOUND";
                prereq.getPrerequisites().add(prereq.getCourseCode());
                break;
        }

        return "SUCCESS";
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
        System.out.println("--------------------------------\n\n");
    }

    @Override
    public String toString() {
        return super.toString() + " Role: Admin";
    }

}
