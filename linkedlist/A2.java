interface ListImp {
    void ins(String s, int i);
    void rem(int i);
    String get(int i);
    int size();
    boolean empty();
}

class ListImpArray implements ListImp {

    private static int INIT = 10000;
    private String[] str;
    private int headIndex, tailIndex;

    public ListImpArray() {
        str = new String[INIT];
        headIndex = 0;
        tailIndex = 0;
    }

    @Override
    public void ins(String s, int n) {
        if (str[n + headIndex] != null) ins_no_idx(str[n + headIndex], n + 1);
        str[n + headIndex] = s;
        tailIndex++;
    }

    private void ins_no_idx(String s, int n) {
        if (str[n] != null) ins_no_idx(str[n], n + 1);
        else str[n] = s;
    }

    @Override
    public void rem(int n) {
        if (str[n + headIndex] != null) {
            str[n + headIndex] = null;
            headIndex++;
            if (n != 0 || n != size() - 1)
                ins_no_idx(get(headIndex-1), headIndex-1);
        }
    }

    @Override
    public String get(int n) {
        return str[n + headIndex];
    }

    @Override
    public int size() {
        return tailIndex - headIndex;
    }

    @Override
    public boolean empty() {
        return size() == 0;
    }

}

class ListImpLinks implements ListImp {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        String data;
        Node next, prev;

        private Node(String data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        private Node getNodeHead(int n) {
            if (n > 0) return next.getNodeHead(--n);
            else return this;
        }

        private Node getNodeTail(int n) {
            if (n > 0) return prev.getNodeTail(--n);
            else return this;
        }
    }

    public ListImpLinks() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void ins(String s, int i) {
        if (head == null) {
            if (i != 0) return;
            head = tail = new Node(s, null, null);
        } else if (i == size) {
            tail = tail.next = new Node(s, null, tail);
        } else {
            Node n = getNode(i);
            if (n == null) return;
            Node p = new Node(s, n, n.prev);
            if (n.prev != null) n.prev.next = p;
            else head = p;
            if (n.next != null) n.next.prev = p;
            else tail = n;
        }
        size++;
    }

    @Override
    public void rem(int i) {
        Node n = getNode(i);
        if (n == null) return;
        if (n.prev != null) n.prev.next = n.next;
        else head = n.next;
        if (n.next != null) n.next.prev = n.prev;
        else tail = n.prev;
        size--;
    }

    @Override
    public String get(int i) {
        Node n = getNode(i);
        if (n == null) return null;
        else return n.data;
    }

    private Node getNode(int i) {
        if (head == null || i >= size || i < 0) return null;
        else if (i <= size / 2) return head.getNodeHead(i);
        else return tail.getNodeTail(size - 1);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean empty() {
        return size() == 0;
    }
}

class Queue {

    private ListImp list;

    public Queue(String s) {
        if (s.equals("links")) list = new ListImpLinks();
        else if (s.equals("array")) list = new ListImpArray();
    }

    public Queue() {
        this("ListImpLinksSingle");
    }

    public void enq(String s) {
        list.ins(s, size());
    }

    public void deq() {
        list.rem(0);
    }

    public String front() {
        return list.get(0);
    }

    public int size() {
        return list.size();
    }

    public boolean empty() {
        return list.empty();
    }

}

class BridgeQueueListDemo {

    public static void main(String args[]) {

        Queue qLL = new Queue("links");
        Queue qAL = new Queue("array");

        if (args.length != 0) {
            qLL.enq(args[0]);
            qAL.enq(args[0]);
            System.out.println(qLL.size());
            System.out.println(qAL.size());
            System.out.println(qLL.front());
            System.out.println(qAL.front());
            qLL.deq();
            qAL.deq();
            System.out.println(qLL.size());
            System.out.println(qAL.size());

            for (int i = 1; i < args.length; i++) {
                qLL.enq(args[i]);
                qAL.enq(args[i]);
            }

            for (int i = 1; i < args.length; i++) {
                System.out.println(qLL.front());
                System.out.println(qAL.front());
                qLL.deq();
                qAL.deq();
                System.out.println(qLL.size());
                System.out.println(qAL.size());
            }
        }
        System.out.println();
    }
}
