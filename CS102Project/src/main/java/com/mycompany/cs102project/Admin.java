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

        ArrayList<Student> students = new ArrayList<>(course.getStudentList());
        for (Student student : students) {
            student.dropCourse(course.getCourseCode());
        }
        for (Course course1 : DataManager.courseList) {
            if (course1.getPrerequisites().contains(course.getCourseCode())) {
                course1.getPrerequisites().remove(course.getCourseCode());
            }
        }
        DataManager.specialRequestsList.removeIf(request -> request.getCourseCode().equalsIgnoreCase(course.getCourseCode()));

        for (User user : DataManager.userList) {
            if (user instanceof Student student) {
                student.getRequests().removeIf(request -> request.getCourseCode().equalsIgnoreCase(course.getCourseCode()));
            }
        }
        DataManager.courseList.remove(course);
    }

    public void addUser(User user) {
        DataManager.userList.add(user);
    }

    public void removeUser(User user) {

        if (user instanceof Student student) {
            removeStudent(student);
        }
        if (user instanceof Advisor advisor) {
            removeAdvisor(advisor);
        }
        if (user instanceof Admin) {
            return;
        }
    }

    private void removeStudent(Student student) {
        ArrayList<Course> registeredCourses = new ArrayList<>(student.getRegisteredCourses());
        for (Course course : registeredCourses) {
            student.dropCourse(course.getCourseCode());
        }
        User advior = DataManager.findUser(student.getAdvisorId());
        if (advior instanceof Advisor advisor) {
            advisor.getStudentList().remove(student);
        }
        for (SpecialRequest sr : student.getRequests()) {
            DataManager.specialRequestsList.remove(sr);
        }
        DataManager.userList.remove(student);
    }

    private void removeAdvisor(Advisor advisor) {
        for (Student student : advisor.getStudentList()) {
            student.setAdvisorId("Yet to be defined");
        }
        for (Course course : DataManager.courseList) {
            if (course.getInstructorId().equalsIgnoreCase(advisor.getId())) course.setInstructorId("Yet to be defined");
        }
        DataManager.userList.remove(advisor);
    }

    public boolean updateCourseInfo(Course course, String information, int choice) {

        switch (choice) {

            case 1:

                String newCode = information.trim();
                String oldCode = course.getCourseCode();

                Course existingCourse = DataManager.findCourse(newCode);

                if (existingCourse != null && existingCourse != course) {
                    return false;
                }

                for (Course otherCourse : DataManager.courseList) {

                    for (int i = 0; i < otherCourse.getPrerequisites().size(); i++) {

                        String prerequisite = otherCourse.getPrerequisites().get(i);

                        if (prerequisite.equalsIgnoreCase(oldCode)) {
                            otherCourse.getPrerequisites().set(i, newCode);
                        }
                    }
                }

                for (SpecialRequest request : DataManager.specialRequestsList) {

                    if (request.getCourseCode().equalsIgnoreCase(oldCode)) {

                        request.setCourseCode(newCode);
                    }
                }
                for (User user : DataManager.userList) {

                    if (user instanceof Student student) {

                        for (int i = 0; i < student.getCompletedCourses().size(); i++) {

                            String completedCode = student.getCompletedCourses().get(i);

                            if (completedCode.equalsIgnoreCase(oldCode)) {
                                student.getCompletedCourses().set(i, newCode);
                            }
                        }
                    }
                }
                course.setCourseCode(newCode);
                return true;
            case 2:
                course.setCourseTitle(information);
                return true;
            case 3:
                course.setCourseDescription(information);
                return true;
            case 4:
                User user = DataManager.findUser(information);
                if (!(user instanceof Advisor)) return false;

                course.setInstructorId(information);
                return true;
            case 5:
                int capacity = Integer.parseInt(information);

                if (capacity < course.getStudentList().size()) {
                    return false;
                }

                course.setCapacity(capacity);
                return true;
            case 6:
                course.setSchedule(information);
                return true;
            case 7:
                course.setCredits(Integer.parseInt(information));
                return true;
            case 8:
                if (information.equalsIgnoreCase(course.getCourseCode())) {
                    return false;
                }

                if (DataManager.findCourse(information) == null) {
                    return false;
                }

                if (course.getPrerequisites().contains(information)) {
                    return false;
                }

                course.getPrerequisites().add(information);
                return true;
            case 9:
                if (!course.getPrerequisites().contains(information)) {
                    return false;
                }

                course.getPrerequisites().remove(information);
                return true;
        }
        return false;
    }

    private void viewAllUsers() {
        for (User user : DataManager.userList) {
            System.out.println(user);
        }
    }

    private void viewAllCourses() {
        for (Course course : DataManager.courseList) {
            System.out.println(course);
        }
    }

    private void viewAllSpecialRequests(){
        for (SpecialRequest sp : DataManager.specialRequestsList){
            System.out.println(sp);
        }
    }
    public void showSystemStatistics() {

        System.out.println("\n--- Users ---");
        viewAllUsers();

        System.out.println("\n--- Courses ---");
        viewAllCourses();

        System.out.println("\n--- Special Requests ---");
        viewAllSpecialRequests();

        int students = 0;
        int advisors = 0;
        int admins = 0;

        for (User user : DataManager.userList) {
            if (user instanceof Student) {
                students++;
            } else if (user instanceof Advisor) {
                advisors++;
            } else if (user instanceof Admin) {
                admins++;
            }
        }

        System.out.println("\n--- Summary ---");
        System.out.println("Students: " + students);
        System.out.println("Advisors: " + advisors);
        System.out.println("Admins: " + admins);
        System.out.println("Courses: " + DataManager.courseList.size());
        System.out.println("Special Requests: " + DataManager.specialRequestsList.size());

    }
    @Override
    public void showRoleSummary() {
        System.out.println("---- Admin Account ---");
        System.out.println("Name: " + getName());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Address: " + getAddress());
        System.out.println("ID: " + getId());
        System.out.println("--------------------------------");
    }

    @Override
    public String toString() {
        return super.toString() + " Role: Admin";
    }

}
