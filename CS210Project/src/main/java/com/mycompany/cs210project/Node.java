package com.mycompany.cs210project;

public class Node {

    Node next;
    Node prev;
    String token;
    int frequency;

    public Node(String token) {
        this.token = token;
        frequency = 1;
        next = null;
        prev = null;
    } 
}
