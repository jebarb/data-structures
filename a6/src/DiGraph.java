import java.util.*;

public class DiGraph implements DiGraphInterface {

    private HashMap<String, Vertex> vertices;
    private HashSet<Long> edge_ids, vertex_ids;

    private class Vertex {
        boolean marked;
        private long id;
        private HashMap<String, Edge> out_edges;
        private HashSet<String> in_edges;

        private Vertex(long id) {
            this.id = id;
            this.out_edges = new HashMap<>();
            this.in_edges = new HashSet<>();
            this.marked = false;
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
        if (idNum >= 0 && label != null && !vertex_ids.contains(idNum) &&
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
            if (v != null && v.out_edges.putIfAbsent(dLabel, new Edge(idNum, weight, eLabel)) == null) {
                vertices.get(dLabel).in_edges.add(sLabel);
                edge_ids.add(idNum);
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public boolean delNode(String label) {
        Vertex vert = vertices.get(label);
        if (vert == null) return false;
        vertices.remove(label);
        vertex_ids.remove(vert.id);
        vert.out_edges.entrySet().forEach(e -> {
            edge_ids.remove(e.getValue().id);
            vertices.get(e.getKey()).in_edges.remove(label);
        });
        vert.in_edges.forEach(e -> {
            Vertex v = vertices.get(e);
            edge_ids.remove(v.out_edges.get(label).id);
            v.out_edges.remove(label);
        });
        return true;
    }

    @Override
    public boolean delEdge(String sLabel, String dLabel) {
        Vertex v = vertices.get(sLabel);
        if (v == null) return false;
        Edge e = v.out_edges.get(dLabel);
        if (e == null) return false;
        else {
            edge_ids.remove(e.id);
            vertices.get(dLabel).in_edges.remove(sLabel);
            v.out_edges.remove(dLabel);
            return true;
        }
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
        vertices.entrySet().forEach(v -> {
            System.out.println("(" + v.getValue().id + ")" + v.getKey());
            v.getValue().out_edges.entrySet().forEach(e -> {
                if (e.getValue().label != null)
                    System.out.println("  (" + e.getValue().id + ")--" + e.getValue().label +
                            "," + e.getValue().weight + "--> " + e.getKey());
                else System.out.println("  (" + e.getValue().id + ")--" + e.getValue().weight + "--> " + e.getKey());
            });
        });
    }

    @Override
    public String[] topoSort() {
        String[] res = new String[vertices.size()];
        HashSet<String> unmarked = new HashSet<>();
        LinkedList<String> queue = new LinkedList<>();
        unmarked.addAll(vertices.keySet());
        queue.addAll(unmarked);
        while (unmarked.size() != 0)
            if (!visit(queue.pop(), res, unmarked)) return null;
        return res;
    }

    private boolean visit(String s, String[] res, HashSet<String> unmarked) {
        Vertex v = vertices.get(s);
        if (v.marked) return false;
        if (!unmarked.contains(s)) return true;
        v.marked = true;
        for (Map.Entry<String, Edge> e : v.out_edges.entrySet())
            if (!visit(e.getKey(), res, unmarked)) return false;
        unmarked.remove(s);
        v.marked = false;
        res[unmarked.size()] = s;
        return true;
    }

}
