public class AssnTopoSort {

    public static void main(String[] args) {
        DiGraph g = new DiGraph();
        g.addNode(100, "Test");
        g.addNode(10, "Tes");
        g.addEdge(123, "Test", "Tes", 123, "sdfg");
        System.out.println(g.numEdges() + " " + g.numNodes());
        g.topoSort();
    }

    // anything else you need to add in
}