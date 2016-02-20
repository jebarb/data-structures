import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;

class Assn3Main {

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
                "  g [STRING]: get a node that has a string as value and assign to SplayTree\n" +
                "  x         : findMax, returns a string\n" +
                "  n         : findMin, returns a string\n" +
                "  v         : val, gets the value stored in the root node\n" +
                "  e         : empty\n" +
                "  s         : size\n" +
                "  h         : height\n" +
                "  q         : quit the tester loop\n" +
                "  p         : print the tree for inspection\n" +
                "  f [INT]   : fill the tree with some amount of random string data\n");
    }

    private static void process() {
        String str = s.next("\\S*");
        switch (str) {
            case "new":
                splay = new SplayTree();
                System.out.println();
                break;
            case "i":
                System.out.println(splay.insert(s.next()) + "\n");
                break;
            case "r": 
                splay.remove(s.next());
                System.out.println("\n");
                break;
            case "c": 
                splay.contains(s.next());
                System.out.println("\n");
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
        private String data;
        private Node left, right, parent;

        private Node(String data, Node left, Node right, Node parent) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        private Node getParentLeft() {
            if (parent != null && parent.right == this) return getParentLeft();
            else return this;
        }

        private Node getParentRight() {
            if (parent != null && parent.left == this) return getParentRight();
            else return this;
        }

        private Node splay() {
            Node n = getParentLeft();
            if (n != this) {
            n.parent = this;
            n.right = this.right;
            this.left = left;
            }
            return this;
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

        private Node insert(String s) {
            if (data == null) {
                data = s;
                return this;
            } else if (data.compareTo(s) > 0) {
                if (left == null) { 
                    left = new Node(s, null, null, this);
                    return left.splay();
                }
                else return left.insert(s);
            } else if (data.compareTo(s) < 0) {
                if (right == null) {
                    right = new Node(s, null, null, this);
                    return right.splay();
                }
                else return right.insert(s);
            } else return splay();
        }

        private Node remove(String s) {
            if (getNode(s).data.equals(s)) {
                right.left = left.findMax();
                return right;
            } else return this;
        }

        private Node findMin() {
            if (left != null) return left.findMin();
            return splay();
        }

        private Node findMax() {
            if (right != null) return right.findMax();
            return splay();
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
            if (data == null) return 0;
            if (left != null) i += left.getSize();
            if (right != null) i += right.getSize();
            return i;
        }

        private void print(int depth, String side, String lines) {
            int i;
            if (data == null) {
                System.out.println("null");
                return;
            }
            for (i = 0; i < depth; i++) {
                if (lines.charAt(i) == '1') System.out.print(" |");
                else System.out.print("  ");
            }
            if (depth != 0) {
                if (lines.charAt(i) == '1') System.out.print(" ├");
                else System.out.print(" └");
                System.out.printf(depth + " " + side + ": " + data + "\n");
            } else System.out.printf(side + ": " + data + "\n");
            depth++;
            String temp = new String(lines);
            if (left != null) {
                if (right != null) left.print(depth, "left", temp.concat("1"));
                else left.print(depth, "left", temp.concat("0"));
            }
            if (right != null) right.print(depth, "right", lines.concat("0"));
        }
    }

    public SplayTree() {
        this.root = new Node(null, null, null, null);
        this.size = 0;
    }

    public SplayTree(String s) {
        this();
        insert(s);
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
        root = root.insert(s);
        return this;
    }

    public String findMin() {
        return root.findMin().data;
    }

    public String findMax() {
        return root.findMax().data;
    }

    public boolean contains(String s) {
        return (root.getNode(s).data.equals(s)) ? true : false;
    }


    public String val() {
        return root.data;
    }

    public boolean empty() {
        return size() == 0;
    }

    public int size() {
        return root.getSize();
    }

    public int height() {
        return root.getHeight();
    }

    public void print() {
        root.print(0, "root", "0");
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
