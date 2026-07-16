package com.mycompany.cs102project;

import java.util.Scanner;

public class AdminMenu implements Menu {

    private Admin admin;
    private Scanner input;

    @Override
    public void start() {
        admin.showRoleSummary();
        System.out.println();
        System.out.println();

        int choice;
        do {
            menu();
            choice = input.nextInt();

            switch (choice) {

                case 1: break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6: break;
            }
        } while (choice != 0);
    }

    @Override
    public void menu() {
        System.out.println("-------------- Admin Menu --------------");
        System.out.println("Please choose an option:");
        System.out.println("1. Add Course");
        System.out.println("2. Remove Course");
        System.out.println("3. Update Course");
        System.out.println("4. Add User");
        System.out.println("5. Remove User");
        System.out.println("6. Show System statistic");
        System.out.println("0. Logout");
        System.out.println("----------------------------------------");
        System.out.print("Enter your choice: ");
    }
}
