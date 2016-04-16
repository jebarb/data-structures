import java.util.*;

public class DiGraph implements DiGraphInterface {

    private HashMap<String, Vertex> vertices;
    private HashSet<Long> edge_ids, vertex_ids;

    private class Vertex implements Comparable<Vertex> {
        boolean marked;
        private String label;
        private Vertex parent;
        private long id;
        private double dist;
        private HashMap<String, Edge> out_edges;
        private HashMap<String, Edge> in_edges;

        private Vertex(String label, long id) {
            this.parent = null;
            this.dist = Double.NaN;
            this.label = label;
            this.id = id;
            this.out_edges = new HashMap<>();
            this.in_edges = new HashMap<>();
            this.marked = false;
        }

        @Override
        public int compareTo(Vertex other) {
            return Double.valueOf(dist).compareTo(other.dist);
        }
    }

    private class Edge {
        Vertex source, dest;
        String label;
        private long id, weight;

        private Edge(long id, long weight, String label, Vertex dest, Vertex source) {
            this.source = source;
            this.label = label;
            this.dest = dest;
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
                vertices.putIfAbsent(label, new Vertex(label, idNum)) == null) {
            vertex_ids.add(idNum);
            return true;
        } else return false;
    }

    @Override
    public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
        if (idNum >= 0 && sLabel != null && dLabel != null && !edge_ids.contains(idNum)) {
            Vertex source = vertices.get(sLabel);
            Vertex dest = vertices.get(dLabel);
            Edge e = new Edge(idNum, weight, eLabel, dest, source);
            if (source != null && dest != null && source.out_edges.putIfAbsent(dLabel, e) == null) {
                vertices.get(dLabel).in_edges.put(sLabel, e);
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
        vert.out_edges.values().forEach(e -> {
            edge_ids.remove(e.id);
            e.dest.in_edges.remove(label);
        });
        vert.in_edges.values().forEach(e -> {
            edge_ids.remove(e.id);
            e.source.out_edges.remove(label);
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
            e.dest.in_edges.remove(sLabel);
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
        vertices.values().forEach(v -> {
            System.out.println("(" + v.id + ")" + v.label);
            v.out_edges.values().forEach(e -> {
                if (e.label != null)
                    System.out.println("  (" + e.id + ")--" + e.label + "," + e.weight + "--> " + e.dest.label);
                else System.out.println("  (" + e.id + ")--" + e.weight + "--> " + e.dest.label);
            });
        });
    }

    @Override
    public String[] topoSort() {
        String[] res = new String[vertices.size()];
        HashSet<String> unmarked = new HashSet<>();
        unmarked.addAll(vertices.keySet());
        for (Vertex v : vertices.values())
            if (!visit(v, res, unmarked)) return null;
        return res;
    }

    private boolean visit(Vertex v, String[] res, HashSet<String> unmarked) {
        if (!unmarked.contains(v.label)) return true;
        if (v.marked) return false;
        v.marked = true;
        for (Edge e : v.out_edges.values())
            if (!visit(e.dest, res, unmarked)) return false;
        unmarked.remove(v.label);
        v.marked = false;
        res[unmarked.size()] = v.label;
        return true;
    }

    public List<String> shortestPath(String label) {
        PriorityQueue<Vertex> visit = new PriorityQueue<>();
        List<String> res = new LinkedList<>();
        Vertex source = vertices.get(label);
        if (source == null) return null;
        source.dist = 0;

        for (Vertex v : vertices.values()) {
            if (v.dist != 0) {
                v.dist = Double.POSITIVE_INFINITY;
                v.parent = null;
                v.marked = false;
            }
            visit.offer(v);
        }

        while (visit.size() != 0) {
            Vertex u = visit.poll();
            if (u.marked) continue;
            u.marked = true;
            for (Edge v : u.out_edges.values()) {
                double alt = u.dist + v.weight;
                if (alt < v.dest.dist) {
                    v.dest.dist = alt;
                    v.dest.parent = u;
                    visit.offer(v.dest);
                }
            }
            res.add(u.label.concat(": " + u.dist));
        }
        return res;
    }
}
