import java.util.Arrays;

public class MinBinHeap implements HeapInterface {

    private EntryPair[] array;
    private int size;
    private static final int INIT = 100000;

    public MinBinHeap() {
        this.array = new EntryPair[INIT];
        array[0] = new EntryPair(null, -100000);
    }

    public void insert(EntryPair entry) {
        if (size() == array.length/2+1) array = Arrays.copyOf(array, 2 * array.length);
        array[size()+1] = entry;
        bubbleUp(size()+1);
        size++;
    }

    public void delMin() {
        if (array[1] != null) {
            if (array[2] == null) array[1] = null;
            array[1] = array[size()];
            array[size()] = null;
            if (array[2] != null) bubbleDown(1);
            size--;
        }
    }

    public EntryPair getMin() {
        return array[1];
    }

    public int size() {
        return size;
    }

    public void build(EntryPair[] entries) {
        array = new EntryPair[entries.length*4];
        size = 0;
        array[0] = new EntryPair(null, -100000);
        int idx = 0;
        while (idx < entries.length && entries[idx] != null) {
            array[idx+1] = entries[idx];
            size++;
            idx++;
        }
        for (int i = size() / 2; i > 0; i--) bubbleDown(i);
    }

    private void print(int idx, int depth, String side, String lines) {
        if (depth == 0) System.out.printf(side + ": " + array[idx].getPriority() + ": " + array[idx].getValue() + "\n");
        else if (lines.charAt(lines.length()-1) == '|') System.out.printf(lines.substring(0, lines.length()-2) + " ├" +
                depth + " " + side + ": " + array[idx].getPriority() + ": " + array[idx].getValue() + "\n");
        else System.out.printf(lines.substring(0, lines.length()-2) + " └" + depth + " " + side + ": " +
                    array[idx].getPriority() + ": " + array[idx].getValue() + "\n");
        if (array[getLeft(idx)] != null && array[getLeft(idx)] != null) print(getLeft(idx), depth+1, "left", lines + " |");
        else if (array[getLeft(idx)] != null) print(getLeft(idx), depth+1, "left", lines + "  ");
        if (array[getRight(idx)] != null) print(getRight(idx), depth+1, "right", lines + "  ");
    }

    public void print() {
        if (array[1] == null) System.out.println("null");
        else print(1, 0, "root", "");
    }

    private void bubbleUp(int idx) {
        int par = getParent(idx);
        if (array[idx].getPriority() < array[par].getPriority()) {
            EntryPair tmp = array[par];
            array[par] = array[idx];
            array[idx] = tmp;
            if (par != 1) bubbleUp(par);
        }
    }

    private void bubbleDown(int idx) {
        int swp = getLeft(idx);
        if (array[getRight(idx)] != null && array[getRight(idx)].getPriority() < array[swp].getPriority())
            swp = getRight(idx);
        if (array[idx].getPriority() > array[swp].getPriority()) {
            EntryPair tmp = array[swp];
            array[swp] = array[idx];
            array[idx] = tmp;
            if (array[getLeft(swp)] != null) bubbleDown(swp);
        }
    }

    private int getParent(int i) {
        return i/2;
    }

    private int getLeft(int i) {
        return 2*i;
    }

    private int getRight(int i) {
        return 2*i+1;
    }

}

