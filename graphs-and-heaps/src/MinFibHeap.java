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
            if (min.getNext() == null) min.setNext(min);
            if (min.getPrev() == null) min.setPrev(min);
        } else merge(t);
    }

    public Vertex poll() {
        if (min == null) return null;
        Vertex old_min = min;
        min = null;
        if (old_min.getNext().compareTo(old_min) != 0) {
            old_min.getNext().setPrev(old_min.getPrev());
            old_min.getPrev().setNext(old_min.getNext());
            min = old_min.getNext();
        } else if (old_min.getChildren() == null) return old_min;
        if (old_min.getChildren() != null) merge(old_min.getChildren());
        combine();
        return old_min;
    }

    public void decreaseKey(double key, Vertex t) {
        t.setDist(key);
        if (min == null) return;
        if (t.getQueueParent() == null) {
            if (min.compareTo(t) >= 0) min = t;
            return;
        } else if (t.getQueueParent().getChildren() != null &&
                t.getQueueParent().compareTo(t) > 0) {
            t.exitHeap();
            offer(t);
        }
    }

    private void merge(Vertex root) {
        if (min == null) min = root;
        else {
            Vertex r = min.getNext();
            Vertex m = root.getPrev();
            min.setNext(root);
            root.setPrev(min);
            r.setPrev(m);
            m.setNext(r);
            if (min.compareTo(root) > 0) min = root;
        }
    }

    private void combine() {
        ArrayList<Vertex> degrees = new ArrayList<>();
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
                    if (root.compareTo(r) > 0) {
                        Vertex temp = r;
                        r = root;
                        root = temp;
                    }
                    degrees.set(r.getDegree(), null);
                    r.addChild(root);
                    degrees.set(root.getDegree(), root);
                    combined = false;
                }
                root = root.getNext();
            }
        } while (!combined);
        old_min = root;
        while (root.compareTo(old_min) != 0) {
            if (min.compareTo(root) > 0) min = root;
            root = root.getNext();
        }

    }

}
