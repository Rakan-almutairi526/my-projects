package com.mycompany.cs210project;

import java.util.Scanner;


public class SolutionAll {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        SPAVL spavl = new SPAVL();
        SPDLIST spdlist = new SPDLIST();
        int activeStructure = 0;
        // 0 nothing 1 avl 2 DList
        while (input.hasNextLine()) {

            String line = input.nextLine().trim();

            if (line.equals("")) {
                System.out.println(-1);
                return;
            }

            String[] split = line.split(" ", 2);
            int command;

            try {
                command = Integer.parseInt(split[0]);
            } catch (Exception e) {
                System.out.println(-1);
                return;
            }

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
                    System.out.print(spavl.toString());
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

class SPDLIST {

    Node Head;
    Node Tail;
    int size;

    public SPDLIST() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void Insert(String token) {

        token = token.toLowerCase();

        Node temp = new Node(token);

        if (isEmpty()) {
            Head = temp;
            Tail = temp;
            size++;
            return;
        }

        Node tempForFreq = getNode(token);

        if (tempForFreq != null) {
            tempForFreq.frequency++;
            return;
        }

        temp.prev = Tail;
        Tail.next = temp;
        Tail = temp;
        size++;
    }

    public boolean Remove(String token) {

        token = token.toLowerCase();

        if (isEmpty()) {
            return false;
        }

        if (Head.token.equals(token) && size != 1) {

            Head.next.prev = null;
            Head = Head.next;
            size--;
            return true;
        } else if (Head.token.equals(token)) {
            Head = null;
            Tail = null;
            size--;
            return true;
        }

        if (size == 1) {
            return false;
        }

        if (Tail.token.equals(token)) {

            Tail.prev.next = null;
            Tail = Tail.prev;
            size--;
            return true;
        }

        Node itr = Head.next;
        while (itr != Tail) {
            if (itr.token.equals(token)) {
                itr.prev.next = itr.next;
                itr.next.prev = itr.prev;
                size--;
                return true;
            }
            itr = itr.next;
        }

        return false;
    }

    public int Search(String token) {

        token = token.toLowerCase();

        if (isEmpty()) {
            return -1;
        }

        if (Head.token.equals(token)) {
            return Head.frequency;
        }

        if (size == 1) {
            return -1;
        }

        if (Tail.token.equals(token)) {
            return Tail.frequency;
        }

        Node itr = Head.next;

        while (itr != Tail) {
            if (itr.token.equals(token)) {
                return itr.frequency;
            }

            itr = itr.next;
        }

        return -1;

    }

    private Node getNode(String token) {

        Node itr = Head;

        while (itr != null) {
            if (itr.token.equals(token)) {
                return itr;
            }
            itr = itr.next;
        }

        return null;
    }

    @Override
    public String toString() {

        String result = "";
        Node itr = Head;

        while (itr != null) {
            result += itr.token + " " + itr.frequency + "\n";
            itr = itr.next;
        }

        return result;
    }

}

class SPAVL {

    AVLNode root;
    int size;

    public SPAVL() {
    }

    int height(AVLNode n) {
        if (n == null) {
            return 0;
        }
        return n.height;
    }

    int getBalance(AVLNode n) {
        if (n == null) {
            return 0;
        }
        return height(n.Left) - height(n.Right);
    }

    AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.Left;
        AVLNode T2 = x.Right;

        x.Right = y;
        y.Left = T2;

        y.height = Math.max(height(y.Left), height(y.Right)) + 1;
        x.height = Math.max(height(x.Left), height(x.Right)) + 1;

        return x;
    }

    AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.Right;
        AVLNode T2 = y.Left;

        y.Left = x;
        x.Right = T2;

        x.height = Math.max(height(x.Left), height(x.Right)) + 1;
        y.height = Math.max(height(y.Left), height(y.Right)) + 1;

        return y;
    }

    public void Insert(String token) {
        token = token.toLowerCase();
        root = Insert(root, token);
    }

    private AVLNode Insert(AVLNode node, String token) {
        if (node == null) {
            size++;
            return new AVLNode(token);
        }

        int cmp = token.compareTo(node.token);

        if (cmp < 0) {
            node.Left = Insert(node.Left, token);
        } else if (cmp > 0) {
            node.Right = Insert(node.Right, token);
        } else {
            node.frequency++;
            return node;
        }

        node.height = Math.max(height(node.Left), height(node.Right)) + 1;

        int balance = getBalance(node);

        if (balance > 1 && token.compareTo(node.Left.token) < 0) {
            return rotateRight(node);
        }

        if (balance < -1 && token.compareTo(node.Right.token) > 0) {
            return rotateLeft(node);
        }

        if (balance > 1 && token.compareTo(node.Left.token) > 0) {
            node.Left = rotateLeft(node.Left);
            return rotateRight(node);
        }

        if (balance < -1 && token.compareTo(node.Right.token) < 0) {
            node.Right = rotateRight(node.Right);
            return rotateLeft(node);
        }

        return node;
    }

    public int Search(String token) {

        token = token.toLowerCase();
        AVLNode temp = root;

        while (temp != null) {

            int cmp = token.compareTo(temp.token);

            if (cmp == 0) {
                return temp.frequency;
            } else if (cmp < 0) {
                temp = temp.Left;
            } else {
                temp = temp.Right;
            }
        }

        return -1;
    }

    public boolean Remove(String token) {
        token = token.toLowerCase();

        if (Search(token) == -1) {
            return false;
        }

        root = Remove(root, token);
        size--;
        return true;
    }

    private AVLNode Remove(AVLNode node, String token) {
        if (node == null) {
            return null;
        }

        int cmp = token.compareTo(node.token);

        if (cmp < 0) {
            node.Left = Remove(node.Left, token);
        } else if (cmp > 0) {
            node.Right = Remove(node.Right, token);
        } else {

            if (node.Left == null && node.Right == null) {
                return null;
            }

            if (node.Left == null) {
                return node.Right;
            }

            if (node.Right == null) {
                return node.Left;
            }

            AVLNode temp = minValueNode(node.Right);
            node.token = temp.token;
            node.frequency = temp.frequency;
            node.Right = Remove(node.Right, temp.token);
        }

        node.height = Math.max(height(node.Left), height(node.Right)) + 1;

        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.Left) >= 0) {
            return rotateRight(node);
        }

        if (balance > 1 && getBalance(node.Left) < 0) {
            node.Left = rotateLeft(node.Left);
            return rotateRight(node);
        }

        if (balance < -1 && getBalance(node.Right) <= 0) {
            return rotateLeft(node);
        }

        if (balance < -1 && getBalance(node.Right) > 0) {
            node.Right = rotateRight(node.Right);
            return rotateLeft(node);
        }

        return node;
    }

    AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;

        while (current.Left != null) {
            current = current.Left;
        }

        return current;
    }

    @Override
    public String toString() {
        return toString(root);
    }

    private String toString(AVLNode n) {

        if (n == null) {
            return "";
        }
        return toString(n.Left) + n.token + " " + n.frequency + "\n" + toString(n.Right);
    }
}

class Node {

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

class AVLNode {

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
