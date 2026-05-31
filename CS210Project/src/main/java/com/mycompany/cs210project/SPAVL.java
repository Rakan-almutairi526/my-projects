package com.mycompany.cs210project;

public class SPAVL {

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

    public void Traverse() {
        Traverse(root);
    }

    private void Traverse(AVLNode node) {
        if (node != null) {
            Traverse(node.Left);
            System.out.println(node.token + " " + node.frequency);
            Traverse(node.Right);
        }
    }

    @Override
    public String toString() {
        return toString(root);
    }
    
    private String toString(AVLNode n){
        
        if (n == null) {
            return ""; 
        }
        return toString(n.Left) + n.token + " " + n.frequency + "\n" + toString(n.Right);
    }

    /*private String makeString(AVLNode node) {
        if (node == null) {
            return "";
        }

        String left = makeString(node.Left);
        String middle = node.token + " " + node.frequency;
        String right = makeString(node.Right);

        if (!left.equals("")) {
            middle = left + "\n" + middle;
        }

        if (!right.equals("")) {
            middle = middle + "\n" + right;
        }

        return middle;
    }
*/
}
