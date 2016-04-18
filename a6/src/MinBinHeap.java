import java.util.ArrayList;
import java.util.HashMap;

public class MinBinHeap {

    private ArrayList<Vertex> array;

    public MinBinHeap() {
        this.array = new ArrayList<>();
        array.add(0, new Vertex(null, 0));
    }

    public void offer(Vertex entry) {
        array.add(entry);
        bubbleUp(size());
    }

    public Vertex poll() {
        if (size() == 0) return null;
        Vertex res = array.get(1);
        if (size() == 1) array.remove(0);
        else {
            array.set(1, array.get(size()));
            array.remove(size());
            if (size() > 1) bubbleDown(1);
        }
        return res;
    }

    public int size() {
        return array.size() - 1;
    }

    public void build(HashMap<String, Vertex> entries) {
        array.clear();
        array.add(0, new Vertex(null, 0));
        array.addAll(entries.values());
        for (int i = size() / 2; i > 0; i--) bubbleDown(i);
    }


    private void bubbleUp(int idx) {
        int par = getParent(idx);
        if (array.get(idx).compareTo((array.get(par))) < 0) {
            Vertex tmp = array.get(par);
            array.set(par, array.get(idx));
            array.set(idx, tmp);
            if (par != 1) bubbleUp(par);
        }
    }

    private void bubbleDown(int idx) {
        int swp = getLeft(idx);
        if (getRight(idx) <= size() && array.get(getRight(idx)).compareTo(array.get(swp)) < 0)
            swp = getRight(idx);
        if (array.get(idx).compareTo(array.get(swp)) > 0) {
            Vertex tmp = array.get(swp);
            array.set(swp, array.get(idx));
            array.set(idx, tmp);
            if (getLeft(swp) <= size()) bubbleDown(swp);
        }
    }

    private int getParent(int i) {
        return i / 2;
    }

    private int getLeft(int i) {
        return 2 * i;
    }

    private int getRight(int i) {
        return 2 * i + 1;
    }

}
