import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class AssnTopoSort {

    private class MyRandom {

        private Random rn = new Random();

        private MyRandom() {
        }

        public int rand(int lo, int hi) {
            int n = hi - lo + 1;
            int i = rn.nextInt() % n;
            if (i < 0) i = -i;
            return lo + i;
        }

        public String nextString(int lo, int hi) {
            int n = rand(lo, hi);
            byte b[] = new byte[n];
            for (int i = 0; i < n; i++)
                b[i] = (byte) rand('a', 'z');
            return new String(b);
        }

        private String nextString() {
            return nextString(5, 25);
        }
    }

    private void genRandStringFile(int num) {
        try {
            File f = new File("randomstrings.txt");
            PrintWriter p = new PrintWriter("randomstrings.txt", "UTF-8");
            MyRandom rand = new MyRandom();
            for (int i = 0; i < num; i++)
                p.println(rand.nextString());
            p.close();
        } catch (Exception ex) {
        }
    }

    private static DiGraph g = new DiGraph();

    public static void main(String[] args) {

        String[] vertices = {"Raleigh", "Durham", "Chapel Hill", "Graham", "Carrboro", "Cary", "Pittsboro", "Sanford", "Los Angeles", "Hillsboro"};
        long i = 0;
        for (String s : vertices) {
            g.addNode(i, s);
            i++;
        }
        g.addEdge(++i, "Raleigh", "Durham", 14, ".");
        g.addEdge(++i, "Durham", "Hillsboro", 9, ".");
        g.addEdge(++i, "Chapel Hill", "Graham", 25, ".");
        g.addEdge(++i, "Graham", "Hillsboro", 12, ".");
        g.addEdge(++i, "Graham", "Los Angeles", 3021, ".");
        g.addEdge(++i, "Chapel Hill", "Carrboro", 1, ".");
        g.addEdge(++i, "Carrboro", "Cary", 32, ".");
        g.addEdge(++i, "Carrboro", "Pittsboro", 15, ".");
        g.addEdge(++i, "Cary", "Raleigh", 3, ".");
        g.addEdge(++i, "Pittsboro", "Cary", 19, ".");
        g.addEdge(++i, "Pittsboro", "Sanford", 15, ".");
        g.addEdge(++i, "Sanford", "Los Angeles", 1007, ".");
        g.print();
        System.out.println(g.numEdges() + " " + g.numNodes());
        String[] str = g.topoSort();
        if (str != null) System.out.println(Arrays.toString(str));
        for (String s : vertices)
            g.delNode(s);
        System.out.println(g.numEdges() + " " + g.numNodes());
        str = g.topoSort();
        if (str != null) System.out.println(Arrays.toString(str));


        //fill with random strings from file
        long start = System.nanoTime();
        long count = 1000000;                              //change this to insert more nodes
        count = (long) (count * 2.5);
        try {
            File f = new File("randomstrings.txt");
            Scanner s = new Scanner(f);
            i = 0;
            while (s.hasNext() && i < count) {
                String source = s.next();
                String dest1 = s.next();
                String dest2 = s.next();
                String dest3 = s.next();
                g.addNode(++i, source);
                g.addNode(++i, dest1);
                g.addNode(++i, dest2);
                g.addNode(++i, dest3);
                g.addEdge(++i, source, dest1, 1, null);
                g.addEdge(++i, source, dest2, 1, null);
                g.addEdge(++i, source, dest3, 1, null);
                g.addEdge(++i, dest1, dest2, 1, null);
                g.addEdge(++i, dest1, dest3, 1, null);
                g.addEdge(++i, dest2, dest3, 1, null);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("error");
        }
        long inserted = System.nanoTime();

        System.out.println("Edges: " + g.numEdges() + " Nodes: " + g.numNodes());
        System.out.printf("Insertion time: %.2f seconds\n", (double) (inserted - start) / 1000000000.0);
        str = g.topoSort();
        long sorted = System.nanoTime();
        if (str != null) System.out.println("Toposort is of correct length: " + (str[0] != null));
        else System.out.println("Graph contains cycle");
        System.out.printf("Toposort time: %.2f seconds\n", (double) (sorted - inserted) / 1000000000.0);

        //remove all nodes from file
        try {
            File f = new File("randomstrings.txt");
            Scanner s = new Scanner(f);
            i = 0;
            while (s.hasNext() && i < count) {
                String source = s.next();
                String dest1 = s.next();
                String dest2 = s.next();
                String dest3 = s.next();
                g.delEdge(source, dest1);
                g.delEdge(dest1, dest2);
                g.delEdge(dest2, dest3);
                g.delNode(source);
                g.delNode(dest1);
                g.delNode(dest2);
                g.delNode(dest3);
                i += 9;
            }
        } catch (Exception ex) {
            System.out.println("error");
        }

        long removed = System.nanoTime();
        System.out.printf("Remove time: %.2f seconds\n", (double) (removed - sorted) / 1000000000.0);
        System.out.println("All nodes and edges removed: " + (g.numEdges() == 0 && g.numNodes() == 0));
    }
}

