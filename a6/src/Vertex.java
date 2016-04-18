import java.util.HashMap;

public class Vertex implements Comparable<Vertex> {
    private boolean marked;
    private String label;
    private Vertex parent;
    private long id;
    private double dist;
    private HashMap<String, Edge> out_edges;
    private HashMap<String, Edge> in_edges;

    public Vertex(String label, long id) {
        this.parent = null;
        this.label = label;
        this.id = id;
        this.out_edges = new HashMap<>();
        this.in_edges = new HashMap<>();
        this.marked = false;
        this.dist = Double.NaN;
    }

    @Override
    public int compareTo(Vertex other) {
        int res = Double.valueOf(dist).compareTo(other.dist);
        if (res == 0) res = label.compareTo(other.label);
        return res;
    }

    public boolean isMarked() {
        return marked;
    }

    public String getLabel() {
        return label;
    }

    public Vertex getParent() {
        return parent;
    }

    public long getId() {
        return id;
    }

    public double getDist() {
        return dist;
    }

    public HashMap<String, Edge> getOutEdges() {
        return out_edges;
    }

    public HashMap<String, Edge> getInEdges() {
        return in_edges;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }
}