import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


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
        g.addEdge(++i, "Sanford", "Los Angeles", 3007, ".");
        g.print();
        System.out.println("Nodes: " + g.numNodes() + " Edges: " + g.numEdges());
        String[] str = null;
        str= g.topoSort();
        if (str != null) System.out.println("Toposort: \n" + Arrays.toString(str));
        List<String> l_path = g.shortestPath("Carrboro");
        String[] path = new String[l_path.size()];
        l_path.toArray(path);
        if (str != null) System.out.println("Dijkstra's algorithm: \n" + Arrays.toString(path));
        for (String s : vertices)
            g.delNode(s);
        System.out.println("Graph is empty after removing all nodes: " + (g.numNodes() == 0 && g.numEdges() == 0));

        //fill with random strings from file
        long start = System.nanoTime();
        int count = 100000;                              //change this to insert more nodes
        System.out.printf("\n\nRandom fill of %d nodes and %d edges:\n", count, (int) (count * 1.5));
        count = (int) (count * 2.5);
        String source = "";
        try {
            File f = new File("randomstrings.txt");
            Scanner s = new Scanner(f);
            i = 0;
            while (s.hasNext() && i < count) {
                source = s.next();
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
        System.out.println("Nodes: " + g.numNodes() + " Edges: " + g.numEdges());
        System.out.printf("Insertion time: %.2f seconds\n", (double) (inserted - start) / 1000000000.0);
        str = g.topoSort();
        long sorted = System.nanoTime();
        if (str != null) System.out.println("Toposort is of correct length: " + (str[0] != null));
        else System.out.println("Graph contains cycle");
        System.out.printf("Toposort time: %.2f seconds\n", (double) (sorted - inserted) / 1000000000.0);

        List<String> list_path = g.shortestPath(source);
        String[] rand_path = new String[list_path.size()];
        list_path.toArray( rand_path);
        long short_path = System.nanoTime();
        System.out.println("Dijkstra's algorithm is of correct length: " + (rand_path.length == g.numNodes() && rand_path[(int)g.numNodes()-1] != null));
        System.out.printf("Dijkstra's algorithm time: %.2f seconds\n", (double) (short_path - sorted) / 1000000000.0);


        //remove all nodes from file
        try {
            File f = new File("randomstrings.txt");
            Scanner s = new Scanner(f);
            i = 0;
            while (s.hasNext() && i < count) {
                source = s.next();
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

