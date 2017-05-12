import static org.junit.Assert.*;
import org.junit.Test;

public class TestDiGraph {
    /*
     * Refactoring the commonalities of comparing toposorts with multiple
     * valid results, into its own function.
    */
    public void assertCorrectSort(DiGraphInterface graph, String... correct) {
        String[] sorted = graph.topoSort();
        if (sorted == null) {
            fail("Failed to sort graph, but it has a valid sort");
        }

        String sorted_string = String.join("", sorted);

        boolean found_match = false;
        for (String possible : correct) {
            if (sorted_string.equals(possible)) {
                found_match = true;
                break;
            }
        }

        if (!found_match) {
            int idx = 0;
            String[] decorated = new String[correct.length];
            for (String possible : correct) {
                decorated[idx] = "'" + possible + "'";
                idx++;
            }

            String all_correct = String.join(", ", decorated);
            fail("Topological sort was '" + sorted_string + "' but expected one of the following: " + all_correct);
        }
    }

    /*
     * Ensure the empty graph starts off correctly.
     */
    @Test
    public void testEmptyGraph() {
        DiGraphInterface empty = new DiGraph();
        assertEquals(0, empty.numEdges());
        assertEquals(0, empty.numNodes());
        assertCorrectSort(empty, "");
    }

    /*
     * Adding an node onto the empty graph.
     */
    @Test
    public void testSingletonGraph() {
        DiGraphInterface singleton = new DiGraph();
        assertTrue(singleton.addNode(1, "1"));

        assertEquals(0, singleton.numEdges());
        assertEquals(1, singleton.numNodes());
        assertCorrectSort(singleton, "1");
    }

    /*
     * Invalid nodes additions are rejected without modifying the graph.
     */
    @Test
    public void testInvalidNodeAdd()
    {
        DiGraphInterface singleton = new DiGraph();
        assertTrue(singleton.addNode(1, "1"));

        assertFalse(singleton.addNode(1, "different"));	// Clashing ID
        assertFalse(singleton.addNode(2, "1"));	// Clashing label
        assertFalse(singleton.addNode(-1, "different")); // Invalid ID
        assertFalse(singleton.addNode(2, null)); // Invalid label

        assertEquals(0, singleton.numEdges());
        assertEquals(1, singleton.numNodes());
        assertCorrectSort(singleton, "1");
    }

    /**
     * Invalid node removals are rejected without modifying the graph
     */
    @Test
    public void testInvalidNodeDel()
    {
        DiGraphInterface singleton = new DiGraph();
        assertTrue(singleton.addNode(1, "1"));

        assertFalse(singleton.delNode("2")); // Nonexistent node

        assertEquals(0, singleton.numEdges());
        assertEquals(1, singleton.numNodes());
        assertCorrectSort(singleton, "1");
    }

    /*
     * Adding a node, and then removing it, should be equivalent to the empty
     * graph.
     */
    @Test
    public void testRemovedNodeGraph() {
        DiGraphInterface singleton = new DiGraph();
        assertTrue(singleton.addNode(1, "1"));
        assertTrue(singleton.delNode("1"));

        assertEquals(0, singleton.numEdges());
        assertEquals(0, singleton.numNodes());
        assertCorrectSort(singleton, "");
    }

    /*
     * Adding an edge between two nodes should add a new edge, and also
     * put the source node before the destination node in the topological sort.
     */
    @Test
    public void testSingletonEdgeGraph() {
        DiGraphInterface edged = new DiGraph();

        assertTrue(edged.addNode(1, "1"));
        assertTrue(edged.addNode(2, "2"));
        assertTrue(edged.addEdge(1, "1", "2", 1, null));

        assertEquals(1, edged.numEdges());
        assertEquals(2, edged.numNodes());
        assertCorrectSort(edged, "12");
    }

    /**
     * Invalid edge additions are rejected without modifying the graph.
     */
    @Test
    public void testInvalidEdgesAdd()
    {
        DiGraphInterface edged = new DiGraph();
        assertTrue(edged.addNode(1, "1"));
        assertTrue(edged.addNode(2, "2"));
        assertTrue(edged.addEdge(1, "1", "2", 1, null));

        // Invalid IDs
        assertFalse(edged.addEdge(1, "2", "1", 1, null));
        assertFalse(edged.addEdge(-1, "2", "1", 1, null));

        // Nonexistent 1st node and 2nd node
        assertFalse(edged.addEdge(2, "nonexistent", "1", 1, null));
        assertFalse(edged.addEdge(2, "2", "nonexistent", 1, null));

        // Duplicate edges
        assertFalse(edged.addEdge(2, "1", "2", 1, null));

        assertEquals(1, edged.numEdges());
        assertEquals(2, edged.numNodes());
        assertCorrectSort(edged, "12");
    }

    /**
     * Invalid edge deletions are rejected without modifying the graph.
     */
    @Test
    public void testInvalidEdgeDel()
    {
        DiGraphInterface edged = new DiGraph();
        assertTrue(edged.addNode(1, "1"));
        assertTrue(edged.addNode(2, "2"));
        assertTrue(edged.addEdge(1, "1", "2", 1, null));

        // Nonexistent 1st and 2nd edges
        assertFalse(edged.delEdge("nonexistent", "2"));
        assertFalse(edged.delEdge("1", "nonexistent"));

        assertEquals(1, edged.numEdges());
        assertEquals(2, edged.numNodes());
        assertCorrectSort(edged, "12");
    }


    /**
     * Adding cycles to a graph prevents it from being sorted.
     */
    @Test
    public void testCyclicGraph() {
        DiGraphInterface edged = new DiGraph();
        assertTrue(edged.addNode(1, "1"));
        assertTrue(edged.addNode(2, "2"));
        assertTrue(edged.addEdge(1, "1", "2", 1, null));
        assertTrue(edged.addEdge(2, "2", "1", 1, null));

        assertEquals(2, edged.numEdges());
        assertEquals(2, edged.numNodes());
        assertArrayEquals(null, edged.topoSort());
    }

    /**
     * Adding an edge and then removing it should be equivalent to the graph
     * without the added edge.
     */
    @Test
    public void testRemovedEdgeGraph() {
        DiGraphInterface edged = new DiGraph();

        assertTrue(edged.addNode(1, "1"));
        assertTrue(edged.addNode(2, "2"));
        assertTrue(edged.addEdge(1, "1", "2", 1, null));

        assertTrue(edged.addEdge(2, "2", "1", 1, null));
        assertTrue(edged.delEdge("2", "1"));

        assertEquals(1, edged.numEdges());
        assertEquals(2, edged.numNodes());
        assertCorrectSort(edged, "12");
    }

    /**
     * Ensure that removing an node removes any edges pointing to it.
     */
    @Test
    public void testRemoveNodeRemovesInboundEdges() {
        DiGraphInterface edged = new DiGraph();

        assertTrue(edged.addNode(1, "1"));
        assertTrue(edged.addNode(2, "2"));
        assertTrue(edged.addEdge(1, "1", "2", 1, null));

        // Deleting node 2 should eliminate the edge that goes into it
        assertTrue(edged.delNode("2"));

        assertEquals(0, edged.numEdges());
        assertEquals(1, edged.numNodes());
        assertCorrectSort(edged, "1");
    }

    /**
     * Test the topological sort, by having multiple levels in the graph
     */
    @Test
    public void testTopologicalSortConnected() {
        DiGraphInterface sortable = new DiGraph();
        sortable.addNode(1, "1");
        sortable.addNode(2, "2");
        sortable.addNode(3, "3");
        sortable.addNode(4, "4");

        sortable.addEdge(1, "3", "2", 1, null);
        sortable.addEdge(2, "3", "1", 1, null);
        sortable.addEdge(3, "2", "4", 1, null);
		
		/*
		 * Graphical representation:
		 * 
		 * 3 -> 2 -> 4
		 *  \-> 1
		 *  
		 * There are three possible topological sorts:
		 * 
		 * [3, 2, 4, 1]
		 * [3, 2, 1, 4]
		 * [3, 1, 2, 4]
		 * 
		 * Any one of these sorting results should be allowed.
		 * 
		 * (The weird order is because I had issues with this test essentially
		 * doing *nothing* (!) since it didn't connect any edges, and the sort
		 * that I wrote spits out the edges from 1 to 4 in order. By swapping
		 * the nodes out of ID order, I can ensure that the test actually does
		 * something).
		 */
        assertCorrectSort(sortable, "3241", "3214", "3124");
    }

    /**
     * Test the topological sort on a disconnected graph.
     */
    @Test
    public void testTopologicalSortDisconnected() {
        DiGraphInterface sortable = new DiGraph();
        sortable.addNode(1, "1");
        sortable.addNode(2, "2");
        sortable.addNode(3, "3");

        sortable.addEdge(1, "2", "1", 1, null);
		
		/*
		 * Graphical representation:
		 * 
		 * 2 -> 1
		 * 
		 * 3
		 * 
		 * Valid sorts:
		 * 
		 *   [2, 1, 3]
		 *   [2, 3, 1]
		 *   [3, 2, 1]
		 */
        assertCorrectSort(sortable, "213", "231", "321");
    }

    /**
     * Ensures that a repeated basic topological sort is non-destructive.
     */
    @Test
    public void testNonDestructiveTopoSort() {
        DiGraphInterface edged = new DiGraph();

        assertTrue(edged.addNode(1, "1"));
        assertTrue(edged.addNode(2, "2"));
        assertTrue(edged.addEdge(1, "1", "2", 1, null));

        assertCorrectSort(edged, "12");
        assertCorrectSort(edged, "12");
    }
}