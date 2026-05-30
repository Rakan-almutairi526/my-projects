package com.mycompany.cs210project;

public class AVLNode {

    AVLNode Left;
    AVLNode Right;
    String token;
    int frequency;
    int height;

    public AVLNode(String token) {
        this.token = token.toLowerCase();
        this.frequency = 1;
        this.height = 1;
        this.Left = null;
        this.Right = null;
    }
}
