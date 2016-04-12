import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DiGraph implements DiGraphInterface {

    private HashMap<String, Vertex> vertices;
    private HashSet<Long> vertex_ids, edge_ids;

    private class Vertex {
        private long id;
        //private HashSet<Long> edge_ids;
        private HashMap<String, Edge> edges;

        private Vertex(long id) {
            this.id = id;
            this.edges = new HashMap<>();
            //this.edge_ids = new HashSet<>();
        }
    }

    private class Edge {
        String label;
        private long id, weight;

        private Edge(long id, long weight, String label) {
            this.label = label;
            this.weight = weight;
            this.id = id;
        }
    }

    public DiGraph() {
        this.vertices = new HashMap<>();
        this.vertex_ids = new HashSet<>();
        this.edge_ids = new HashSet<>();

    }

    @Override
    public boolean addNode(long idNum, String label) {
        if (idNum >= 0 && !vertex_ids.contains(idNum) && label != null &&
                vertices.putIfAbsent(label, new Vertex(idNum)) == null) {
            vertex_ids.add(idNum);
            return true;
        } else return false;
    }

    @Override
    public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
        if (idNum >= 0 && sLabel != null && dLabel != null &&
                vertices.containsKey(dLabel) && !edge_ids.contains(idNum)) {
            Vertex v = vertices.get(sLabel);
            if (v != null && v.edges.putIfAbsent(dLabel, new Edge(idNum, weight, eLabel)) == null) {
                edge_ids.add(idNum);
                return true;
            } else return false;
        } return false;
    }

    @Override
    public boolean delNode(String label) {
        Vertex vert = vertices.get(label);
        if (vert == null) return false;
        HashMap<String, Edge> vEdges = vert.edges;
        long idNum = vert.id;
        if (vertices.remove(label) != null) {
            for (Vertex v: vertices.values()) {
                Edge tmp = v.edges.get(label);
                if (v != null) {
                    long tmpNum = tmp.id;
                    edge_ids.remove(tmpNum);
                    v.edges.remove(label);
                }
            }
            for (Edge e: vEdges.values()) edge_ids.remove(e.id);
            vertex_ids.remove(idNum);
            return true;
        } else return false;
    }

    @Override
    public boolean delEdge(String sLabel, String dLabel) {
        Vertex v = vertices.get(sLabel);
        if (v == null) return false;
        Edge e = v.edges.get(dLabel);
        if (e == null) return false;
        long idNum = e.id;
        if (v.edges.remove(dLabel) != null) {
            edge_ids.remove(idNum);
            return true;
        } else return false;
    }

    @Override
    public long numNodes() {
        return vertices.size();
    }

    @Override
    public long numEdges() {
        return edge_ids.size();
    }

    @Override
    public void print() {

    }

    @Override
    public String[] topoSort() {
        return new String[0];
    }
}
