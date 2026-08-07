package com.mycompany.cs102project;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        DataManager.loadCoursesFromFile("courses.txt");
        DataManager.loadUsersFromFile("users.txt");
        DataManager.loadEnrollments("enrollments.txt");
        DataManager.loadSpecialRequestFromFile("specialRequests.txt");

        Scanner input = new Scanner(System.in);
        Menu menu;
        while (true) {

            System.out.println("Welcome to the system");
            ;
            System.out.print("Enter your ID (or type EXIT to quit): ");
            String id = input.nextLine().trim();
            System.out.println();

            if (id.equalsIgnoreCase("EXIT")) {
                System.out.println("Goodbye!");
                break;
            }

            User loggedUser = DataManager.findUser(id);

            if (loggedUser == null) {
                System.out.println("User not found!");
                continue;
            }

            if (loggedUser instanceof Student student) {
                menu = new StudentMenu(student, input);
                menu.start();
            } else if (loggedUser instanceof Advisor advisor) {
                menu = new AdvisorMenu(advisor, input);
                menu.start();
            } else if (loggedUser instanceof Admin admin) {
                menu = new AdminMenu(admin, input);
                menu.start();
            }

        }
    }
}