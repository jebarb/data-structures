import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Assn3Main {
    public static void print(BST bst, int height, String s) {
        for (int i = 0; i < height; i++) {
            System.out.printf(" ");
        }
        System.out.printf(height + " " + s + ": " + bst.val() + "\n");
        BST left = bst.getLeft();
        BST right = bst.getRight();
        if (left != null) print(left, height+1, "left");
        if (right != null) print(right, height+1, "right");
    }

    //Allow:
    //<command> <argument>
    //OR
    //<command>
    //<argument>
    private static String getNext(Scanner s) {
        String res = s.findInLine("\\S.*");
        System.out.println(res);
        res = (res.length() == 0) ? s.next("\\S*") : res;
        return res;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            Scanner s = new Scanner(System.in);
            BST bst = new BST();
            while(true) {
                switch (s.next("\\S*")) { 
                    case "new": 
                        bst = new BST();
                        break;
                    case "i":
                        System.out.println(bst.insert(getNext(s)) + "\n");
                        break;
                    case "r": 
                        System.out.println(bst.remove(s.nextLine()) + "\n");
                        break;
                    case "c": 
                        System.out.println(bst.contains(s.nextLine()) + "\n");
                        break;
                    case "g": 
                        break;
                    case "x": 
                        System.out.println(bst.findMax() + "\n");
                        break;
                    case "n": 
                        System.out.println(bst.findMin() + "\n");
                        break;
                    case "v": 
                        System.out.println(bst.val() + "\n");
                        break;
                    case "e": 
                        System.out.println(bst.empty() + "\n");
                        break;
                    case "s": 
                        System.out.println(bst.size() + "\n");
                        break;
                    case "h": 
                        System.out.println(bst.height() + "\n");
                        break;
                    case "q": 
                        return;
                    case "p": 
                        print(bst, 0, "root");
                        System.out.println();
                    case "f": 
                        for (int i = 0; i < 30; i++) {
                            bst.insert(MyRandom.nextString(5,15));
                        }
                }
            }

        } else {
        }
    }
}

class BST {

    private Node root;
    private int size;

    private class Node {
        private String data;
        private Node left, right;

        private Node(String data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        private Node getNode(String s) {
            if (data == null) return null;
            if (data.equals(s)) return this;
            if (data.compareTo(s) > 0) {
                if (left == null) return null;
                else return left.getNode(s);
            } else if (data.compareTo(s) < 0) {
                if (right == null) return null;
                else return right.getNode(s);
            } else return null;
        }

        private boolean insert(String s) {
            if (data == null) data = s;
            else if (data.compareTo(s) > 0) {
                if (left == null) {
                    left = new Node(s, null, null);
                } else return left.insert(s);
            } else if (data.compareTo(s) < 0) {
                if (right == null) {
                    right = new Node(s, null, null);
                } else return right.insert(s);
            } else return false;
            return true;
        }

        private boolean remove(String s) {
            if (data == null) return false;
            if (data.equals(s)) {
                if (left != null) {
                    data = left.findMax().data;
                    left.remove(data);
                    if (left.data == null) left = null;
                } else if (right != null) {
                    data = right.findMin().data;
                    right.remove(data);
                    if (right.data == null) right = null;
                } else {
                    data = null;
                }
                return true;
            } else if (data.compareTo(s) > 0) {
                if (left == null) return false;
                if (!left.remove(s)) return false;
                if (left.data == null) left = null;
                return true;
            } else if (data.compareTo(s) < 0) {
                if (right == null) return false;
                if (!right.remove(s)) return false;
                if (right.data == null) right = null;
                return true;
            }
            return false;
        }

        private Node findMin() {
            if (left != null) return left.findMin();
            else return this;
        }

        private Node findMax() {
            if (right != null) return right.findMax();
            else return this;
        }

        private int getHeight() {
            int l = 0;
            int r = 0;
            if (left != null) l += left.getHeight()+1;
            if (right != null) r += right.getHeight()+1;
            return (l > r) ? l : r;
        }

        private int getSize(int i) {
            if (left != null) i = left.getSize(i+1);
            if (right != null) i = right.getSize(i+1);
            return i;
        }

    }

    //new       :          -->  BST
    public BST() {
        this.root = new Node(null, null, null);
        this.size = 0;
    }

    public BST(String s) {
        this();
        insert(s);
    }

    public BST(Node root, int size) {
        this.root = root;
        this.size = size;
    }


    //insert    :  String  -->      (or maybe a boolean showing success)
    public boolean insert(String s) {
        if (root.insert(s)) {
            size++;
            return true;
        } else return false;
    }

    //remove    :  String  -->      (or maybe a boolean showing success)
    public boolean remove(String s) {
        if (root.remove(s)) {
            size--;
            return true;
        } else return false;
    }

    //findMin   :          -->  String
    public String findMin() {
        return root.findMin().data;
    }

    //findMax   :          -->  String
    public String findMax() {
        return root.findMax().data;
    }

    //contains  :  String  -->  boolean
    public boolean contains(String s) {
        return root.getNode(s) != null;
    }

    //get       :  String  -->  BST  (a node actually)
    public BST get(String s) {
        Node n = root.getNode(s);
        return new BST(n, n.getSize(1));
    }

    //val       :          -->  String  (returns the key stored in the root)
    public String val() {
        return root.data;
    }

    //empty     :          -->  boolean
    public boolean empty() {
        return size == 0;
    }

    //size      :          -->  int+ (an integer zero or larger)
    public int size() {
        return size;
    }

    //height    :          -->  int+ (an integer zero or larger)
    public int height() {
        return root.getHeight();
    }

    public BST getLeft() {
        if (root.left != null) {
            return new BST(root.left, root.left.getSize(1));
        } else return null;
    }

    public BST getRight() {
        if (root.right != null) {
            return new BST(root.right, root.right.getSize(1));
        } else return null;
    }
}
class MyRandom {

    private static Random rn = new Random();

    private MyRandom(){ }

    public static int rand(int lo, int hi) {
        int n = hi - lo + 1;
        int i = rn.nextInt() % n;
        if (i < 0) i = -i;
        return lo + i;
    }

    public static String nextString(int lo, int hi) {
        int n = rand(lo, hi);
        byte b[] = new byte[n];
        for (int i = 0; i < n; i++)
            b[i] = (byte)rand('a', 'z');
        return new String(b);
    }

    public static String nextString() {
        return nextString(5, 25);
    }

}
