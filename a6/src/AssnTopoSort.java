import java.util.Arrays;

public class AssnTopoSort {

    public static void main(String[] args) {
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
        //g.randomFill(10);
        g.print();
        String[] str = g.topoSort();
        if (str != null) System.out.println(Arrays.toString(str));
        System.out.println(g.numEdges() + " " + g.numNodes());
        //g.randomRemoveEdge(100);
        //g.randomRemoveNode(10);
        //g.print();
        //System.out.println(g.numEdges() + " " + g.numNodes());
    }
}

