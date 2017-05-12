import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;

class Assn3Main {

    private static Scanner s;
    private static BST bst = new BST();

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
                "  g [STRING]: get a node that has a string as value and assign to BST\n" +
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
                bst = new BST();
                System.out.println();
                break;
            case "i":
                System.out.println(bst.insert(s.next()) + "\n");
                break;
            case "r": 
                System.out.println(bst.remove(s.next()) + "\n");
                break;
            case "c": 
                System.out.println(bst.contains(s.next()) + "\n");
                break;
            case "g": 
                //Uncomment first line to print subtree
                //Uncomment second line to print string in node
                //Uncomment third line to reassign bst to subtree
                bst.get(s.next()).print();
                //System.out.println(bst.get(s.next()).val());
                //bst = bst.get(s.next());
                System.out.println();
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
                System.exit(0);
                break;
            case "p":
                bst.print();
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
                    bst.insert(MyRandom.nextString(5,15));
                break;
            case "C":
                try {
                    File file_in = new File(s.next());
                    Scanner file = new Scanner(file_in);
                    boolean res = true;
                    while (file.hasNext())
                        if (!bst.contains(file.next())) res = false;
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
                    while (file.hasNext())
                        if (!bst.insert(file.next())) res = false;
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
                    while (file.hasNext())
                        if (!bst.remove(file.next())) res = false;
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
                if (left == null) left = new Node(s, null, null);
                else return left.insert(s);
            } else if (data.compareTo(s) < 0) {
                if (right == null) right = new Node(s, null, null);
                else return right.insert(s);
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
                } else data = null;
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

    public boolean insert(String s) {
        if (root.insert(s)) {
            size++;
            return true;
        } else return false;
    }

    public boolean remove(String s) {
        if (root.remove(s)) {
            size--;
            return true;
        } else return false;
    }

    public String findMin() {
        return root.findMin().data;
    }

    public String findMax() {
        return root.findMax().data;
    }

    public boolean contains(String s) {
        return root.getNode(s) != null;
    }

    public BST get(String s) {
        Node n = root.getNode(s);
        return (n == null) ? null : new BST(n, n.getSize());
    }

    public String val() {
        return root.data;
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
