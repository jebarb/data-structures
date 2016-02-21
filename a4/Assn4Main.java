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
                "  I [FILE]  : fill the tree with strings from file,\n" + 
                "                  report true if no duplicates\n" +
                "  r [STRING]: remove a node containing a string\n" +
                "  R [STRING]: remove a nodes containing strings from file,\n" + 
                "                  report true if all strings are removed with no duplicates\n" +
                "  c [STRING]: contains a string, report a boolean indicating success\n" +
                "  C [FILE]  : contains all strings in a file, report boolean indicating success\n" +
                "  x         : findMax, returns a string\n" +
                "  n         : findMin, returns a string\n" +
                "  v         : val, gets the value stored in the root node\n" +
                "  e         : empty\n" +
                "  s         : size\n" +
                "  h         : height\n" +
                "  q         : quit the tester loop\n" +
                "  p         : print the tree for inspection\n" +
                "  f [INT]   : fill the tree with some amount of random string key\n");
    }

    private static void process() {
        String str = s.next("\\S*");
        switch (str) {
            case "new":
                splay = new SplayTree();
                System.out.println();
                break;
            case "i":
                splay.insert(s.next());
                System.out.println();
                break;
            case "r": 
                splay.remove(s.next());
                System.out.println();
                break;
            case "c": 
                System.out.println(splay.contains(s.next()) + "\n");
                break;
            case "x": 
                System.out.println(splay.findMax() + "\n");
                break;
            case "n": 
                System.out.println(splay.findMin() + "\n");
                break;
            case "v": 
                System.out.println(splay.val() + "\n");
                break;
            case "e": 
                System.out.println(splay.empty() + "\n");
                break;
            case "s": 
                System.out.println(splay.size() + "\n");
                break;
            case "h": 
                System.out.println(splay.height() + "\n");
                break;
            case "q":
                System.exit(0);
                break;
            case "p":
                splay.print();
                System.out.println();
                break;
            case "f":
                int num = 0;
                try {
                    num = s.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid argument\n");
                }
                for (int i = 0; i < num; i++)
                    splay.insert(MyRandom.nextString(5,15));
                break;
            case "C":
                try {
                    File file_in = new File(s.next());
                    Scanner file = new Scanner(file_in);
                    boolean res = true;
                    while (file.hasNext()) splay.contains(file.next());
                    System.out.println(res + "\n");
                } catch (FileNotFoundException ex) {
                    System.out.println("Invalid argument\n");
                }
                break;
            case "I":
                try {
                    File file_in = new File(s.next());
                    Scanner file = new Scanner(file_in);
                    boolean res = true;
                    while (file.hasNext()) splay.insert(file.next());
                    System.out.println(res + "\n");
                } catch (FileNotFoundException ex) {
                    System.out.println("Invalid argument\n");
                }
                break;
            case "R":
                try {
                    File file_in = new File(s.next());
                    Scanner file = new Scanner(file_in);
                    boolean res = true;
                    while (file.hasNext()) splay.remove(file.next());
                    System.out.println(res + "\n");
                } catch (FileNotFoundException ex) {
                    System.err.println("Invalid argument\n");
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
            System.out.println(in);
            s = new Scanner(in);
            while (s.hasNext())
                process();
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
        }

        private Node getNode(String s) {
            if (key.equals("")) return splay();
            if (key.equals(s)) return splay();
            if (key.compareTo(s) > 0) {
                if (left == null) return splay();
                else return left.getNode(s);
            } else if (key.compareTo(s) < 0) {
                if (right == null) return splay();
                else return right.getNode(s);
            } else return splay();
        }

        private Node insert(String s) {
            if (key.equals("")) {
                key = s;
                size++;
                return splay();
            } else if (key.compareTo(s) > 0) {
                if (left == null) { 
                    left = new Node(s, null, null, this);
                    size++;
                    return left.splay();
                }
                else return left.insert(s);
            } else if (key.compareTo(s) < 0) {
                if (right == null) {
                    right = new Node(s, null, null, this);
                    size++;
                    return right.splay();
                }
                else return right.insert(s);
            } else {
                return splay();
            }
        }

        private Node findMin() {
            Node res = (left != null) ? left.findMin() : this;
            return res.splay();
        }

        private Node findMax() {
            Node res = (right != null) ? right.findMax() : this;
            return res.splay();
        }

        private int getHeight() {
            int l = 0;
            int r = 0;
            if (left != null) l += left.getHeight()+1;
            if (right != null) r += right.getHeight()+1;
            return (l > r) ? l : r;
        }

        private int getSize() {
            int i = 1;
            if (key.equals("")) return 0;
            if (left != null) i += left.getSize();
            if (right != null) i += right.getSize();
            return i;
        }

        private Node rotateRight() {
            Node tmp = left;
            if (tmp != null) {
                left = tmp.right;
                if (tmp.right != null) tmp.right.parent = this;
                tmp.parent = parent;
            }
            if (parent != null && this == parent.left ) parent.left = tmp;
            else if (parent != null) parent.right = tmp;
            if (tmp != null) tmp.right = this;
            parent = tmp;
            return this;
        }

        private Node rotateLeft() {
            Node tmp = right;
            if(tmp != null) {
                right = tmp.left;
                if(tmp.left != null) tmp.left.parent = this;
                tmp.parent = parent;
            }
            if (parent != null && this == parent.left ) parent.left = tmp;
            else if (parent != null) parent.right = tmp;
            if (tmp != null) tmp.left = this;
            parent = tmp;
            return this;
        }

        private Node splay() {
            if (parent == null) return this;
            else if (parent.parent == null) {
                if (parent.left == this) parent.rotateRight();
                else parent.rotateLeft();
            } else if (parent.left == this && parent.parent.left == parent) {
                parent.parent.rotateRight();
                parent.rotateRight();
            } else if (parent.right == this && parent.parent.right == parent ) {
                parent.parent.rotateLeft();
                parent.rotateLeft();
            } else if (parent.left == this && parent.parent.right == parent ) {
                parent.rotateRight();
                parent.rotateLeft();
            } else {
                parent.rotateLeft();
                parent.rotateRight();
            }
            return splay();
        }

        private void print(int depth, String side, String lines) {
            if (key.equals("")) System.out.println("null");
            else if (depth == 0) System.out.printf(side + ": " + key + "\n");
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
        this.root = new Node("", null, null, null);
        this.size = 0;
    }

    public SplayTree(String s) {
        this();
        root = root.insert(s);

    }

    public SplayTree(Node root, int size) {
        this.root = root;
        this.size = size;
    }

    public SplayTree insert(String s) {
        root = root.insert(s);
        return this;
    }

    public SplayTree remove(String s) {
        contains(s);
        if (!val().equals(s)) return this;
        size--;
        Node r = root.right;
        root = root.left.findMax();
        root.right = r;
        return this;
    }

    public String findMin() {
        root = root.findMin();
        return val();
    }

    public String findMax() {
        root = root.findMax();
        return val();
    }

    public boolean contains(String s) {
        root = root.getNode(s);
        return (val().equals(s)) ? true : false;
    }


    public String val() {
        return root.key;
    }

    public boolean empty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public int height() {
        return root.getHeight();
    }

    public void print() {
        root.print(0, "root", "  ");
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
