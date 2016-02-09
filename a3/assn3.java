class Assn3Main {
    class Assn3Main {
        public static void main (String[] args) {
            // in here code for doing the
            // interactive builder/tester mode
            // and the command line arg auto-execution mode
        }
    }
}

class BST {

    private node root;
    private int size, height;

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
            } else {
                if (right == null) return null;
                else return right.getNode(s);
            }
        }

        private Node findMin() {
        }

        private Node findMax() {
        }

        private int getHeight() {
        }

        private int getSize() {
        }

    }

    //new       :          -->  BST
    public BST(Node root, int size, int height) {
        this.root = root;
        this.size = size;
        this.height = height;

    }

    public BST() {
        this(new Node(null, null, null), 0, 0);
    }


    //insert    :  String  -->      (or maybe a boolean showing success)
    public boolean insert(String s) {
        if (root.insert(s) != null) {
            size++;
            return true;
        } else {
            return false;
        }
    }

    //remove    :  String  -->      (or maybe a boolean showing success)
    public boolean remove(String s) {
        if (root.remove(s) != null) {
            size--;
            return true;
        } else {
            return false;
        }
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
        return new BST(root.getNode(s), root.getSize(), root.getHeight());
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
        return height;
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
        return new String(b, 0);
    }

    public static String nextString() {
        return nextString(5, 25);
    }

}

import java.util.ArrayList;
import java.util.List;

public class TreePrinter {

    public static void main(String[] args) {
        // test 1
        BST root = new BST("c");
        BST left = new BST("b");
        left.left = new BST("a");
        BST right = new BST("e");
        right.left = new BST("d");
        right.right = new BST("f");
        root.left = left;
        root.right = right;

        print(root);

        // test 2
        root = new BST("h");
        for (String val : new String[] { "b", "r", "f", "a", "d", "q", "g" })
            root.ins(val);
        print(root);

        // test 3
        root = new BST("much longer Strings also work");
        for (String val : new String[] { "longer than one character", 
            "some other test string", "another test string" })
            root.ins(val);
        print(root);
    }

    public static void print(BST root) {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<BST> level = new ArrayList<BST>();
        List<BST> next = new ArrayList<BST>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (BST n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.root;
                    line.add(aa);
                    if (aa.length() > widest)
                        widest = aa.length();

                    next.add(n.left);
                    next.add(n.right);

                    if (n.left != null)
                        nn++;
                    if (n.right != null)
                        nn++;
                }
            }

            if (widest % 2 == 1)
                widest++;

            lines.add(line);

            List<BST> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null)
                                c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null)
                    f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }
}
