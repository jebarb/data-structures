import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

public class TestDiGraph {
    /*
     * Ensure the empty graph starts off correctly.
     */
    @Test
    public void testEmptyGraph() {
        DiGraphInterface empty = new DiGraph();
        assertEquals(0, empty.numEdges());
        assertEquals(0, empty.numNodes());
        assertArrayEquals(new String[] {}, empty.topoSort());
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
        assertArrayEquals(new String[] {"1"}, singleton.topoSort());
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
        assertArrayEquals(new String[] {"1"}, singleton.topoSort());
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
        assertArrayEquals(new String[] {"1"}, singleton.topoSort());
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
        assertArrayEquals(new String[] {}, singleton.topoSort());
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
        assertArrayEquals(new String[] {"1", "2"}, edged.topoSort());
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
        assertArrayEquals(new String[] {"1", "2"}, edged.topoSort());
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
        assertArrayEquals(new String[] {"1", "2"}, edged.topoSort());
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
        assertArrayEquals(new String[] {"1", "2"}, edged.topoSort());
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
        assertArrayEquals(new String[] {"1"}, edged.topoSort());
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
        String[] sorted = sortable.topoSort();
        String[] possible1 = new String[] { "3", "2", "4", "1"};
        String[] possible2 = new String[] { "3", "2", "1", "4"};
        String[] possible3 = new String[] { "3", "1", "2", "4"};

        if (!Arrays.equals(sorted, possible1) &&
                !Arrays.equals(sorted, possible2) &&
                !Arrays.equals(sorted, possible3)) {
            String sorted_str = String.join(" ", sorted);
            String possible1_str = String.join(" ", possible1);
            String possible2_str = String.join(" ", possible2);
            String possible3_str = String.join(" ", possible3);

            fail("Topological sort result was '" + sorted_str + "', expected " +
                    "'" + possible1_str + "' or '" + possible2_str + "' or '" +
                    possible3_str + "'");
        }
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
        String[] sorted = sortable.topoSort();
        String[] possible1 = new String[] { "2", "1", "3" };
        String[] possible2 = new String[] { "2", "3", "1" };
        String[] possible3 = new String[] { "3", "2", "1" };

        if (!Arrays.equals(sorted, possible1) &&
                !Arrays.equals(sorted, possible2) &&
                !Arrays.equals(sorted, possible3)) {
            String sorted_str = String.join(" ", sorted);
            String possible1_str = String.join(" ", possible1);
            String possible2_str = String.join(" ", possible2);
            String possible3_str = String.join(" ", possible3);

            fail("Topological sort result was '" + sorted_str + "', expected " +
                    "'" + possible1_str + "' or '" + possible2_str + "' or '" +
                    possible3_str + "'");
        }
    }
}