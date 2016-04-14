import java.util.Arrays;

public class AssnTopoSort {

    public static void main(String[] args) {
/*
    public void randomFill(int n) {
        int m = n;
        while (m-- > 0)
            addNode(MyRandom.rand(0, 64 * n), MyRandom.nextString());
        for (Vertex v : vertices.values()) {
            int p = 10;
            for (Vertex e : vertices.values()) {
                if (p > 0) addEdge(p*MyRandom.rand(1, 64 * n), v.name, e.name, 1, null);
                p--;
            }
        }
    }

    public void randomRemoveNode(int n) {
        LinkedList<String> strings = new LinkedList<>();
        for (Vertex v : vertices.values()) {
            if (n > 0) strings.add(v.name);
            n--;
        }
        strings.forEach(s -> delNode(s));
    }

    public void randomRemoveEdge(int n) {
        LinkedList<String> stringsv = new LinkedList<>();
        LinkedList<String> stringse = new LinkedList<>();
        for (Vertex v : vertices.values()) {
            if (n > 0) {
                long count = n / numNodes() + 1;
                for (Edge e : v.out_edges.values()) {
                    if (count > 0) {
                        stringsv.push(v.name);
                        stringse.push(e.name);
                    }
                    count--;
                }
                n--;
            }
        }
        while (stringsv.size() != 0)
            delEdge(stringsv.pop(), stringse.pop());
    }
    */
        DiGraph g = new DiGraph();


        String[] vertices = {"Raleigh", "Durham", "Chapel Hill", "Graham", "Carrboro", "Cary", "Pittsboro", "Sanford", "Los Angeles", "Hillsboro"};
        int i = 0;
        for (String s: vertices) {
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

        /*
        g.addNode(1, "a");
        g.addNode(2, "b");
        g.addNode(3, "c");
        g.addEdge(2, "a", "b", 1, null);
        g.addEdge(1, "a", "c", 1, null);
        g.addEdge(3, "c", "b", 1, null);
        */
        //g.randomFill(10000);
        g.print();
        String[] str = g.topoSort();
        if (str != null) System.out.println(Arrays.toString(str));
        System.out.println(g.numEdges() + " " + g.numNodes());

        //g.randomRemoveEdge(100000);
        //g.randomRemoveNode(10000);
        //g.print();
        //str = g.topoSort();
        //if (str != null) System.out.println(Arrays.toString(str));
        //System.out.println(g.numEdges() + " " + g.numNodes());
    }
}

