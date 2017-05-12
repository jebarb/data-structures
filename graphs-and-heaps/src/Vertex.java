import java.util.HashMap;

public class Vertex implements Comparable<Vertex> {
    private boolean marked, queue_marked;
    private int location;
    private String label;
    private Vertex parent;
    private long id;
    private double dist;
    private HashMap<String, Edge> out_edges;
    private HashMap<String, Edge> in_edges;
    private Vertex children, next, prev, queue_parent;
    private int degree;


    public Vertex(String label, long id) {
        this.parent = null;
        this.label = label;
        this.id = id;
        this.out_edges = new HashMap<>();
        this.in_edges = new HashMap<>();
        this.marked = false;
        this.dist = Double.NaN;
        this.next = this;
        this.prev = this;
    }

    @Override
    public int compareTo(Vertex other) {
        int res = Double.valueOf(dist).compareTo(other.dist);
        if (res == 0) res = label.compareTo(other.label);
        return res;
    }
    private int setDegree() {
        if (this.parent != null) {
            parent.setDegree();
        }
        int i = 1;
        Vertex c = children;
        do {
            i += c.setDegree();
            c = c.next;
        } while (c != children);
        degree = i;
        return i;
    }

    public void addChild(Vertex c) {
        if (children == null) children = c;
        if (next == null) this.next = this;
        if (prev == null) this.prev = this;
        else {
            Vertex old_next = children.next;
            Vertex old_prev = c.prev;
            children.next = c;
            c.next = children;
            old_next.prev = old_prev;
            old_prev.next = old_next;
            c.parent = this;
        }
        degree += c.degree;
    }

    public void exitHeap() {
        Vertex p = this.getQueueParent();
        p.setChildren(this.getNext());
        this.getNext().setQueueParent(p);
        this.setQueueParent(null);
        Vertex c = this.getNext();
        c.setPrev(this.getPrev());
        c.getPrev().setNext(c);
        this.setPrev(this);
        this.setNext(this);
    }


    public void setChildren(Vertex children) {
        this.children = children;
        if (next == null) this.next = this;
        if (prev == null) this.prev = this;
    }

    public boolean isQueueMarked() {
        return queue_marked;
    }

    public void setQueueMarked(boolean queue_marked) {
        this.queue_marked = queue_marked;
    }

    public Vertex getQueueParent() {
        return queue_parent;
    }

    public void setQueueParent(Vertex parent) {
        this.queue_parent = parent;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public Vertex getChildren() {
        return children;
    }

    public Vertex getNext() {
        return next;
    }

    public void setNext(Vertex next) {
        this.next = next;
    }

    public Vertex getPrev() {
        return prev;
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
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

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

}