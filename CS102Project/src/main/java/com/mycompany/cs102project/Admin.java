package com.mycompany.cs102project;

public class Admin extends User {

    public Admin(String id, String name, String phone, String address) {
        super(id, name, phone, address);
    }

    public void addCourse(Course course) {
        DataManager.courseList.add(course);
    }

    public void removeCourse(Course course) {
        DataManager.courseList.remove(course);
    }

    public void addUser(User user) {
        DataManager.userList.add(user);
    }

    public void removeUser(User user) {
        DataManager.userList.remove(user);
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
        System.out.println("---- Admin Account Summary ---");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Total users: " + DataManager.userList.size());
        System.out.println("Total courses: " + DataManager.courseList.size());
        System.out.println("--------------------------------");
    }

    @Override
    public String toString() {
        return super.toString() + "\nRole: Admin";
    }

    @Override
    public void viewMenu() {

    }

    @Override
    public void performeAction(int choice) {

    }
}
