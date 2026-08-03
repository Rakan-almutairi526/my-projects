package com.mycompany.cs102project;

import java.util.Scanner;

public class AdvisorMenu implements Menu{

    private Advisor advisor;
    private Scanner input;

    public AdvisorMenu(Advisor advisor, Scanner input) {
        this.advisor = advisor;
        this.input = input;
    }

    @Override
    public void start() {
        advisor.showRoleSummary();
        System.out.println();
        System.out.println();
        int choice;
        do {
            menu();
            choice = readChoice(4, 0, this.input);
            switch (choice){
                case 0: break;
                case 1: viewAllStudents(); break;
                case 2: viewStudentCourses(); break;
                case 3: approveAndDenySpecialRequest("Approved");break;
                case 4: approveAndDenySpecialRequest("Decline"); break;
            }
        }while (choice != 0);
    }

    @Override
    public void menu() {
        System.out.println("-------------- Advisor Menu --------------");
        System.out.println("Please choose an option:");
        System.out.println("1. View All Assigned Students");
        System.out.println("2. View a Student's Courses");
        System.out.println("3. Approve a Special Request");
        System.out.println("4. Decline a Special Request");
        System.out.println("0. Logout");
        System.out.println("------------------------------------------");
        System.out.print("Enter your choice: ");
    }
    private void approveAndDenySpecialRequest(String statement){
        System.out.println("-------------- "+ statement + " Special --------------");
        advisor.showStudentRequests();
        do {
            System.out.println("0. return");
            System.out.print("Enter request id: ");
            String id = input.nextLine();
            System.out.println();
            if (id.equalsIgnoreCase("0")) break;
            SpecialRequest request = DataManager.findRequest(id);
            if (request == null){
                System.out.println("Request not found\n");
                continue;
            }
            if (!advisor.findStudent(request.getStudentId())){
                System.out.println("This student is not assigned to you!");
            }
            if (!request.getStatus().equalsIgnoreCase("Pending")){
                System.out.println("Request already handled\n");
                continue;
            }
            System.out.print("Enter comment: ");
            String comment = input.nextLine();
            System.out.println();
            if (statement.equalsIgnoreCase("Approved")) {
                advisor.approveSpecialRequisite(request, comment);
            }else {
                advisor.denySpecialRequisite(request, comment);
            }
            System.out.println("Request was successfully " + statement + "\n");
        }while (true);
        DataManager.saveAllData();
        System.out.println("---------------------------------------------------------");
    }
    private void viewAllStudents(){
        System.out.println("-------------- View All Students --------------");
        advisor.viewRegisteredStudent();
        System.out.println("-----------------------------------------------");
    }
    private void viewStudentCourses(){
        System.out.println("-------------- View Student Courses --------------");
        do {
            System.out.println("0. return");
            System.out.print("Enter student Id: ");
            String id = input.nextLine();
            System.out.println();
            if (id.equalsIgnoreCase("0")) break;
            User user = DataManager.findUser(id);

            if (!(user instanceof Student student)) {
                System.out.println("Student not found");
                continue;
            }
            if (!advisor.getStudentList().contains(student)) {
                System.out.println("This student is not assigned to you.");
                continue;
            }
            System.out.println("Student Name: " + student.getName() + " Student id: " + student.getId());
            student.viewRegisteredCourses();
            System.out.println();
        }while (true);
        System.out.println("--------------------------------------------------");
    }
}
