import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;

class Assn4Main {

    private static Scanner s;
    private static SplayTree splay = new SplayTree();

    private static void printHelp() {
        System.out.println("Usage: [OPTION]... [OPTION] [ARGUMENT]...");
        System.out.println("Options:");
        System.out.println(
                "  help      : print this message\n" +
                "  new       : make a new empty tree\n" +
                "  i [STRING]: insert a string\n" +
                "  I [FILE]  : fill the tree with strings from file\n" +
                "  r [STRING]: remove a node containing a string\n" +
                "  R [FILE]  : remove nodes containing strings from file\n" + 
                "  c [STRING]: contains a string\n" +
                "  C [FILE]  : contains all strings in a file\n" +
                "  f [INT]   : fill the tree with random strings\n" +
                "  x         : finds max string\n" +
                "  n         : finds min string\n" +
                "  v         : gets the value stored in the root node\n" +
                "  e         : empty\n" +
                "  s         : size\n" +
                "  h         : height\n" +
                "  q         : quit the tester loop\n" +
                "  p         : print the tree for inspection");
    }

    private static void process() {
        String str = s.next("\\S*");
        switch (str) {
            case "new":
                splay = new SplayTree();
                break;
            case "i":
                splay.insert(s.next());
                break;
            case "r":
                splay.remove(s.next());
                break;
            case "c":
                System.out.println(splay.contains(s.next()));
                break;
            case "x":
                System.out.println(splay.findMax());
                break;
            case "n":
                System.out.println(splay.findMin());
                break;
            case "v":
                System.out.println(splay.val());
                break;
            case "e":
                System.out.println(splay.empty());
                break;
            case "s":
                System.out.println(splay.size());
                break;
            case "h":
                System.out.println(splay.height());
                break;
            case "q":
                System.exit(0);
                break;
            case "p":
                splay.print();
                break;
            case "f":
                int num = 0;
                try {
                    num = s.nextInt();
                } catch (InputMismatchException ex) {
                    System.err.println("Invalid argument");
                }
                for (int i = 0; i < num; i++)
                    splay.insert(MyRandom.nextString());
                break;
            case "C":
                try {
                    File file_in = new File(s.next());
                    Scanner file = new Scanner(file_in);
                    boolean res = true;
                    while (file.hasNext()) 
                        if (!splay.contains(file.next())) res = false;
                    System.out.println(res);
                } catch (FileNotFoundException ex) {
                    System.err.println("Invalid argument");
                }
                break;
            case "I":
                try {
                    File file_in = new File(s.next());
                    Scanner file = new Scanner(file_in);
                    while (file.hasNext()) splay.insert(file.next());
                } catch (FileNotFoundException ex) {
                    System.err.println("Invalid argument");
                }
                break;
            case "R":
                try {
                    File file_in = new File(s.next());
                    Scanner file = new Scanner(file_in);
                    while (file.hasNext()) splay.remove(file.next());
                    System.out.println();
                } catch (FileNotFoundException ex) {
                    System.err.println("Invalid argument");
                }
                break;
            case "help":
                printHelp();
                break;
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            s = new Scanner(System.in);
            System.out.println("Type 'help' for usage");
            while (true)
                process();
        } else {
            String in = "";
            for (int i = 0; i < args.length; i++)
                in = in.concat(args[i] + " ");
            s = new Scanner(in);
            while (s.hasNext()) process();
        }

    }
}

class SplayTree {

    private Node root;
    private int size;

    private class Node {
        private String key;
        private Node left, right, parent;

        private Node(String key, Node left, Node right, Node parent) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
            size++;
        }

        private Node getNode(String s) {
            if (s.compareTo(key) < 0 && left != null) return left.getNode(s);
            if (s.compareTo(key) > 0 && right != null) return right.getNode(s);
            return splay();
        }

        private Node insert(String s) {
            if (s.compareTo(key) < 0) {
                if (left == null) left = new Node(s, null, null, this);
                return left.insert(s);
            } else if (s.compareTo(key) > 0) {
                if (right == null) right = new Node(s, null, null, this);
                return right.insert(s);
            } else return splay();
        }

        private Node findMin() {
            return (left != null) ? left.findMin() : splay();
        }

        private Node findMax() {
            return (right != null) ? right.findMax() : splay();
        }

        private int getHeight() {
            int l = 0, r = 0;
            if (left != null) l = left.getHeight()+1;
            if (right != null) r = right.getHeight()+1;
            return (l > r) ? l : r;
        }

        private int getSize() {
            int i = 1;
            if (left != null) i += left.getSize();
            if (right != null) i += right.getSize();
            return i;
        }

        private void rotateRight() {
            Node tmp = parent;
            tmp.left = right;
            if (tmp.left != null) tmp.left.parent = tmp;
            parent = tmp.parent;
            if (parent != null && parent.right == tmp) parent.right = this;
            else if (parent != null) parent.left = this;
            tmp.parent = this;
            right = tmp;
        }

        private void rotateLeft() {
            Node tmp = parent;
            tmp.right = left;
            if (tmp.right != null) tmp.right.parent = tmp;
            parent = tmp.parent;
            if (tmp.parent != null && parent.right == tmp) parent.right = this;
            else if (tmp.parent != null) parent.left = this;
            tmp.parent = this;
            left = tmp;
        }

        private Node splay() {
            if (parent == null) return this;
            else if (parent.parent == null && parent.left == this) rotateRight();
            else if (parent.parent == null && parent.right == this) rotateLeft();
            else if (parent.left == this && parent.parent.left == parent) {
                parent.rotateRight();
                rotateRight();
            } else if (parent.left == this && parent.parent.right == parent) {
                rotateRight();
                rotateLeft();
            } else if (parent.right == this && parent.parent.right == parent) {
                parent.rotateLeft();
                rotateLeft();
            } else if (parent.right == this && parent.parent.left == parent) {
                rotateLeft();
                rotateRight();
            }
            return splay();
        }

        private void print(int depth, String side, String lines) {
            if (depth == 0) System.out.printf(side + ": " + key + "\n");
            else if (lines.charAt(lines.length()-1) == '|') 
                System.out.printf(lines.substring(0, lines.length()-2) + 
                        " ├" + depth + " " + side + ": " + key + "\n");
            else System.out.printf(lines.substring(0, lines.length()-2) + 
                    " └" + depth + " " + side + ": " + key + "\n");
            if (left != null && right != null) left.print(depth+1, "left", lines + " |");
            else if (left != null) left.print(depth+1, "left", lines + "  ");
            if (right != null) right.print(depth+1, "right", lines + "  ");
        }
    }

    public SplayTree() {
        this.size = 0;
    }

    public void insert(String s) {
        root = (root == null) ? new Node(s, null, null, null) : root.insert(s);
    }

    public void remove(String s) {
        if (root == null || !contains(s)) return;
        else if (root.left == null) root = root.right;
        else {
            Node r = root.right;
            root = root.left.findMax();
            root.right = r;
            if (root.right != null) root.right.parent = root;
        }
        if (root != null) root.parent = null;
        size--;
    }

    public String findMin() {
        if (root != null) root = root.findMin();
        return val();
    }

    public String findMax() {
        if (root != null) root = root.findMax();
        return val();
    }

    public boolean contains(String s) {
        if (root != null) root = root.getNode(s);
        return (val().equals(s));
    }

    public String val() {
        return (root == null) ? null : root.key;
    }

    public boolean empty() {
        return (size() == 0);
    }

    public int size() {
        return size;
    }

    public int height() {
        return (root == null) ? 0 : root.getHeight();
    }

    public void print() {
        if (root == null) System.out.println("null");
        else root.print(0, "root", "");
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
