import java.util.InputMismatchException;
import java.util.Scanner;

public class AssnTopoSort {

    public static void main(String[] args) {
        DiGraph g = new DiGraph();
        g.randomFill(10);
        g.print();
        String[] str = g.topoSort();
        if (str != null) System.out.println(str.toString());
        System.out.println(g.numEdges() + " " + g.numNodes());
        g.randomRemoveEdge(100);
        //g.randomRemoveNode(10);
        g.print();
        System.out.println(g.numEdges() + " " + g.numNodes());
    }
}

