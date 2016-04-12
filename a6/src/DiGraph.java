import java.util.*;

public class DiGraph implements DiGraphInterface {

    private HashMap<String, Vertex> vertices;
    private HashSet<Long> vertex_ids, edge_ids;

    private class Vertex {
        String name;
        boolean marked;
        private long id;
        private HashMap<String, Edge> edges;

        private Vertex(String name, long id) {
            this.name = name;
            this.id = id;
            this.edges = new HashMap<>();
            this.marked = false;
        }
    }

    private class Edge {
        String name, label;
        private long id, weight;

        private Edge(String name, long id, long weight, String label) {
            this.name = name;
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
                vertices.putIfAbsent(label, new Vertex(label, idNum)) == null) {
            vertex_ids.add(idNum);
            return true;
        } else return false;
    }

    @Override
    public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
        if (idNum >= 0 && sLabel != null && dLabel != null &&
                vertices.containsKey(dLabel) && !edge_ids.contains(idNum)) {
            Vertex v = vertices.get(sLabel);
            if (v != null && v.edges.putIfAbsent(dLabel, new Edge(dLabel, idNum, weight, eLabel)) == null) {
                edge_ids.add(idNum);
                return true;
            } else return false;
        }
        return false;
    }

    @Override
    public boolean delNode(String label) {
        Vertex vert = vertices.get(label);
        if (vert == null) return false;
        long idNum = vert.id;
        if (vertices.remove(label) != null) {
            for (Vertex v : vertices.values()) {
                Edge tmp = v.edges.get(label);
                if (v != null) {
                    edge_ids.remove(tmp.id);
                    v.edges.remove(label);
                }
            }
            for (Edge e : vert.edges.values()) edge_ids.remove(e.id);
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
        LinkedList<String> res = new LinkedList<String>();
        LinkedList<String> unmarked = new LinkedList<>();
        for (String s : vertices.keySet()) unmarked.add(s);
        while (unmarked.size() != 0)
            if (!visit(unmarked.get(0), res, unmarked)) return null;
        return res.toArray(new String[res.size()]);
    }

    private boolean visit(String s, LinkedList res, LinkedList<String> unmarked) {
        Vertex v = vertices.get(s);
        if (v.marked) return false;
        else if (!unmarked.contains(s)) return true;
        else {
            v.marked = true;
            for (Edge e : v.edges.values()) {
                Vertex tmp = vertices.get(e.name);
                if (tmp != null)
                    if (!visit(tmp.name, res, unmarked)) return false;
            }
            unmarked.remove(v.name);
            v.marked = false;
            res.add(0, s);
            return true;
        }

    }


    private Vertex findNewVertexOfIndegreeZero() {
        for (Vertex v : vertices.values()) if (v.edges.size() == 0) return v;
        return null;
    }
}
