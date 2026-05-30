package com.mycompany.cs210project;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        SPAVL spavl = new SPAVL();
        SPDLIST spdlist = new SPDLIST();
        int activeStructure = 0;
        // 0 nothing 1 avl 2 DList
        
        
        while (input.hasNextLine()) {
                
            //before we get to the commands we need to make sure its in the right format.
                
            
            // we take the line 
            String line = input.nextLine().trim();
            
            
            // check if line is empty 
            if (line.equals("")) {
                System.out.println(-1);
                return;
            }
            
            // split line to two halfs.
            String[] split = line.split(" ", 2);
            
            //we creat integer command and get to the try to make sure that an integer was intered first.
            int command;
            try {
                command = Integer.parseInt(split[0]);
            } catch (Exception e) {
                System.out.println(-1);
                return;
            }
            
            
            
            // now that we know 
            if (command > 5 || command < 1) {
                System.out.println(-1);
                return;
            }

            if (command == 1) {

                String[] tokens = validateTokens(split);

                if (tokens == null) {
                    System.out.println(-1);
                    return;
                }

                readInputAVL(spavl, tokens);
                activeStructure = 1;

            } else if (command == 2) {

                String[] tokens = validateTokens(split);

                if (tokens == null) {
                    System.out.println(-1);
                    return;
                }

                readinputDList(spdlist, tokens);
                activeStructure = 2;

            } else if (command == 3) {

                if (activeStructure == 0) {

                    System.out.println(-1);
                    return;
                }

                String[] tokens = validateTokens(split);

                if (tokens == null) {
                    System.out.println(-1);
                    return;
                }

                if (tokens.length > 1) {
                    System.out.println(-1);
                    return;
                }

                if (activeStructure == 1) {
                    String token = tokens[0];
                    System.out.println(spavl.Search(token));
                }
                if (activeStructure == 2) {
                    String token = tokens[0];
                    System.out.println(spdlist.Search(token));
                }

            } else if (command == 4) {

                if (activeStructure == 0) {

                    System.out.println(-1);
                    return;
                }

                String[] tokens = validateTokens(split);

                if (tokens == null) {
                    System.out.println(-1);
                    return;
                }
                if (tokens.length > 1) {
                    System.out.println(-1);
                    return;
                }

                if (activeStructure == 1) {
                    String token = tokens[0];
                    spavl.Remove(token);
                }
                if (activeStructure == 2) {
                    String token = tokens[0];
                    spdlist.Remove(token);
                }

            } else if (command == 5) {

                if (activeStructure == 0) {

                    System.out.println(-1);
                    return;
                }

                if (activeStructure == 1) {
                    spavl.Traverse();
                }
                if (activeStructure == 2) {
                    System.out.print(spdlist.toString());
                }
            }

        }

    }

    public static String[] validateTokens(String[] s) {

        if (s.length < 2) {
            return null;
        }

        String[] tokens = s[1].trim().split("\\s+");

        if (tokens.length < 1 || tokens.length > 100000) {
            return null;
        } else {
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].length() > 1000 || tokens[i].length() < 1) {
                    return null;
                }
            }
        }
        return tokens;
    }

    public static void readinputDList(SPDLIST spdlist, String[] tokens) {

        for (int i = 0; i < tokens.length; i++) {

            spdlist.Insert(tokens[i]);
        }

    }

    public static void readInputAVL(SPAVL spavl, String[] tokens) {

        for (int i = 0; i < tokens.length; i++) {

            spavl.Insert(tokens[i]);
        }

    }

}
