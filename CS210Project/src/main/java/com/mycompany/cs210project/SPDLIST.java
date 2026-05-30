package com.mycompany.cs210project;

public class SPDLIST {

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
