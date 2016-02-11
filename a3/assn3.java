/*
   class Assn3Main {
   class Assn3Main {
   public static void main (String[] args) {
// in here code for doing the
// interactive builder/tester mode
// and the command line arg auto-execution mode
   }
   }
   }
   */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            if (data.equals(s)) {
                if (left != null) {
                    data = left.findMax().data;
                    if (data.equals(left.data)) left = null;
                    else left.remove(data);
                    return true;
                } else if (right != null) {
                    data = right.findMin().data;
                    if (data.equals(right.data)) right = null;
                    else right.remove(data);
                    return true;
                } else {
                    data = null;
                    return false;
                }
            } else if (data.compareTo(s) > 0) {
                if (left == null) return false;
                else if (!left.remove(s)) left = null;
                return true;
            } else if (data.compareTo(s) < 0) {
                if (right == null) return false;
                else if (!right.remove(s)) right = null;
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

        private int getHeight(int i) {
            int l = 0;
            int r = 0;
            if (left != null) l = left.getHeight(i+1);
            if (right != null) r += right.getHeight(i+1);
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
        this(null);
    }

    public BST(String s) {
        this.root = new Node(s, null, null);
        this.size = 1;
    }

    public BST(Node root, int size) {
        this.root = root;
        this.size = size;
    }


    //insert    :  String  -->      (or maybe a boolean showing success)
    public boolean insert(String s) {
        if (root.insert(s)) {
            size--;
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
        return root.getHeight(0);
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


class TreePrinter {

    public static void main(String[] args) {

        BST root = new BST("c");
        root.insert("b");
        root.insert("a");
        root.insert("e");
        root.insert("d");
        root.insert("f");

        //print(root);

        //root.remove("c");

        //print(root);

        // test 2
        root = new BST();
        for (String val : new String[] { "h", "b", "r", "f", "a", "d", "q", "g" }) {
            root.insert(val);
            print(root);
        }

        for (String val : new String[] { "h", "g", "f", "d", "b", "a", "q", "r" }) {
            print(root);
            root.remove(val);
        }
        // test 3
        root = new BST();
        for (int i = 0; i < 15; i++) root.insert(MyRandom.nextString(1,2));
        //print(root);
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
                    String aa = n.val();
                    line.add(aa);
                    if (aa.length() > widest)
                        widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null)
                        nn++;
                    if (n.getRight() != null)
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
