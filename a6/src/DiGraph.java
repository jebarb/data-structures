import java.util.*;

public class DiGraph implements DiGraphInterface {

    public void randomFill(int n) {
        int m = n;
        while (m-- > 0)
            addNode(MyRandom.rand(0, 10 * n), MyRandom.nextString());
        for (Vertex v : vertices.values()) {
            int count = MyRandom.rand(1, n);
            for (Vertex e : vertices.values()) {
                if (count > 0) addEdge(MyRandom.rand(1, 10 * n), v.name, e.name, MyRandom.rand(0, 5), null);
                count--;
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
                for (Edge e : v.in_edges.values()) {
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

    private LinkedHashMap<String, Vertex> vertices;
    private LinkedHashSet<Long> edge_ids, vertex_ids;

    private class Vertex {
        String name;
        boolean marked;
        private long id;
        private LinkedHashMap<String, Edge> out_edges, in_edges;


        private Vertex(String name, long id) {
            this.name = name;
            this.id = id;
            this.in_edges = new LinkedHashMap<>();
            this.out_edges = new LinkedHashMap<>();
            this.marked = false;
        }
    }

    private class Edge {
        String name, e_label;
        private long id, weight;

        private Edge(String name, long id, long weight, String e_label) {
            this.name = name;
            this.e_label = e_label;
            this.weight = weight;
            this.id = id;
        }
    }

    public DiGraph() {
        this.vertices = new LinkedHashMap<>();
        this.vertex_ids = new LinkedHashSet<>();
        this.edge_ids = new LinkedHashSet<>();

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
            if (v != null && v.out_edges.putIfAbsent(dLabel, new Edge(dLabel, idNum, weight, eLabel)) == null) {
                vertices.get(dLabel).in_edges.put(sLabel, new Edge(sLabel, idNum, weight, eLabel));
                edge_ids.add(idNum);
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public boolean delNode(String label) {
        Vertex vert = vertices.get(label);
        if (vert == null) return false;
        vert.out_edges.values().forEach(e -> {
            edge_ids.remove(e.id);
            vertices.get(e.name).in_edges.remove(label);
        });
        vertex_ids.remove(vert.id);
        if (vertices.remove(label) != null) {
            vert.in_edges.values().forEach(e -> {
                Vertex v = vertices.get(e.name);
                edge_ids.remove(v.out_edges.get(label).id);
                v.out_edges.remove(label);
            });
            return true;
        } else return false;
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
        vertices.values().forEach(v -> {
            System.out.println("(" + v.id + ")" + v.name);
            v.out_edges.values().forEach(e -> {
                if (e.e_label != null)
                    System.out.println("  (" + e.id + ")--" + e.e_label + "," + e.weight + "--> " + e.name);
                else System.out.println("  (" + e.id + ")--" + e.weight + "--> " + e.name);
            });
        });
    }

    @Override
    public String[] topoSort() {
        String[] res = new String[vertices.size()];
        LinkedHashSet<String> unmarked = new LinkedHashSet<>();
        unmarked.addAll(vertices.keySet());
        while (unmarked.size() != 0)
            if (!visit(unmarked.iterator().next(), res, unmarked)) return null;
        return res;
    }

    private boolean visit(String s, String[] res, LinkedHashSet<String> unmarked) {
        Vertex v = vertices.get(s);
        if (v.marked) return false;
        else if (!unmarked.contains(s)) return true;
        else {
            v.marked = true;
            for (Edge e : v.out_edges.values()) {
                Vertex tmp = vertices.get(e.name);
                if (tmp != null)
                    if (!visit(tmp.name, res, unmarked)) return false;
            }
            unmarked.remove(v.name);
            v.marked = false;
            res[unmarked.size()] = s;
            return true;
        }

    }
}
