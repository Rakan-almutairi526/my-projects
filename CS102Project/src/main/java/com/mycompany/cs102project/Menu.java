package com.mycompany.cs102project;

import java.util.Scanner;

public interface Menu {

    void start();
    void menu();
    default int readChoice(int max, int min, Scanner input) {

        while (true){
          String temp = input.nextLine().trim();
            System.out.println();
          try{
              int choice = Integer.parseInt(temp);
              if (choice <= max && choice >= min ) return choice;
          } catch (Exception e) {
          }
            System.out.print("invalid input, try again: ");
        }
    }
}
