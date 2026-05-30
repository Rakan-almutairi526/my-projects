package com.mycompany.cs210project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SolutionForBpart {

    public static void main(String[] args) throws FileNotFoundException {

        String[] fileNames = {"file1kb.txt", "file10kb.txt", "file100kb.txt", "file1mb.txt", "file10mb.txt"};
        int countFile = 1;

        System.out.println("File 1 = 1KB, File 2 = 10KB, File 3 = 100KB, File 4 = 1MB, File 5 = 10MB\n");
        System.out.println("*********************\n");

        for (String fileName : fileNames) {

            double[] SPDLISTResults = testSPDLIST(fileName);
            double[] SPAVLResults = testSPAVL(fileName);

            System.out.println("File: " + countFile);

            System.out.println("\nSPDLIST\n Insertion time: " + SPDLISTResults[0] + "ms Search time: " + SPDLISTResults[1] + "ms Remove time: " + SPDLISTResults[2] + "\n");
            System.out.println("SPAVL\n Insertion time: " + SPAVLResults[0] + "ms Search time: " + SPAVLResults[1] + "ms Remove time: " + SPAVLResults[2] + "\n");
            System.out.println("*********************\n");
            countFile++;
        }

    }

    public static double[] testSPDLIST(String fileName) throws FileNotFoundException {

        SPDLIST spdlist = new SPDLIST();
        double[] results = new double[3];
        ArrayList<String> mArrayList = new ArrayList<>();

        File name = new File(fileName);
        Scanner read = new Scanner(name);

        while (read.hasNext()) {
            mArrayList.add(read.next());
        }

        long startTime = System.nanoTime();
        for (String string : mArrayList) {
            spdlist.Insert(string);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        results[0] = duration / 1000000.0;

        String word = mArrayList.get(mArrayList.size() / 2);

        startTime = System.nanoTime();
        spdlist.Search(word);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        results[1] = duration / 1000000.0;

        startTime = System.nanoTime();
        spdlist.Remove(word);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        results[2] = duration / 1000000.0;

        return results;
    }

    public static double[] testSPAVL(String fileName) throws FileNotFoundException {
        SPAVL spavl = new SPAVL();
        double[] results = new double[3];
        ArrayList<String> mArrayList = new ArrayList<>();

        File name = new File(fileName);
        Scanner read = new Scanner(name);

        while (read.hasNext()) {
            mArrayList.add(read.next());
        }

        long startTime = System.nanoTime();
        for (String string : mArrayList) {
            spavl.Insert(string);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        results[0] = duration / 1000000.0;

        String word = mArrayList.get(mArrayList.size() / 2);

        startTime = System.nanoTime();
        spavl.Search(word);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        results[1] = duration / 1000000.0;

        startTime = System.nanoTime();
        spavl.Remove(word);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        results[2] = duration / 1000000.0;

        return results;
    }

}
