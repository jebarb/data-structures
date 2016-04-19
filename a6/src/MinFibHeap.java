import java.util.ArrayList;

public class MinFibHeap {

    private Vertex min;
    private int size;

    public MinFibHeap() {
    }

    public MinFibHeap(Vertex min) {
        this.min = min;
    }

    public void offer(Vertex t) {
        if (min == null) {
            min = t;
            min.setNext(min);
            min.setPrev(min);
        }
        else merge(t);
    }

    public Vertex poll() {
        if (min == null) return null;
        Vertex old_min = min;
        if (min.getNext().equals(min)) {
            if (min.getChildren() == null) {
                min = null;
                return old_min;
            } else {
                min = min.getChildren();
                old_min = min;
            }
        }
        Vertex n = min.getNext();
        n.setPrev(min.getPrev());
        n.getPrev().setNext(n);
        min = n;
        if (old_min.getChildren() != null ) merge(old_min.getChildren());
        combine();
        return old_min;
    }

    public void decreaseKey(double key, Vertex t) {
        if (min == null) {
            t.setDist(key);
            return;
        }
        if (min.compareTo(t) == 0) min = null;
        if (t.getQueueParent() != null && t.getQueueParent().getChildren() != null &&
                t.getQueueParent().getChildren().compareTo(t) == 0)
            t.getQueueParent().setChildren(t.getNext());
        t.setDist(key);
        Vertex c = t.getNext();
        c.setPrev(t.getPrev());
        c.getPrev().setNext(t);
        if (min == t) min = null;
        offer(t);
    }

    private void merge(Vertex root) {
        Vertex r = min.getNext();
        Vertex m = root.getPrev();
        min.setNext(root);
        root.setPrev(min);
        r.setNext(m);
        m.setPrev(r);
        if (min.compareTo(root) > 0) min = root;
    }

    private void combine() {
        ArrayList<Vertex> degrees = new ArrayList<>(/*(int)Math.ceil(Math.log(size))*/);
        Vertex root = min;
        Vertex old_min = min;
        boolean combined;
        do {
            combined = true;
            while (!root.equals(old_min)) {
                if (degrees.get(root.getDegree()) == null)
                    degrees.set(root.getDegree(), root);
                else {
                    Vertex r = degrees.get(root.getDegree());
                    degrees.set(r.getDegree(), null);
                    r.addChild(root);
                    r = root.getPrev();
                    r.setPrev(root.getNext());
                    r.getPrev().setNext(r);
                    if (degrees.get(root.getDegree()) == null)
                        degrees.set(root.getDegree(), root);
                    combined = false;

                }
                root = root.getNext();
            }
        } while (!combined);
        old_min = root;
        while (!root.equals(old_min)) {
            if (min.compareTo(root) > 0) min = root;
            root = root.getNext();
        }

    }

}
