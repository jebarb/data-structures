public class ListImpLinks implements ListImp {

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
            if (n > 0) {
                return next.getNodeHead(--n);
            } else {
                return this;
            }
        }

        private Node getNodeTail(int n) {
            if (n > 0) {
                return prev.getNodeTail(--n);
            } else {
                return this;
            }
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
            if (i != 0) {
                return;
            }
            head = tail = new Node(s, null, null);
        } else if (i == size) {
            tail = tail.next = new Node(s, null, tail);
        } else {
            Node n = getNode(i);
            if (n == null) {
                return;
            }
            Node p = new Node(s, n, n.prev);
            if (n.prev != null) {
                n.prev.next = p;
            } else {
                head = p;
            }
            if (n.next != null) {
                n.next.prev = p;
            }
        }
        size++;
    }

    @Override
    public void rem(int i) {
        Node n = getNode(i);
        if (n == null) {
            return;
        }
        if (n.prev != null) {
            n.prev.next = n.next;
        } else {
            head = n.next;
        }
        if (n.next != null) {
            n.next.prev = n.prev;
        } else {
            tail = n.prev;
        }
        size--;
    }

    @Override
    public String get(int i) {
        Node n = getNode(i);
        if (n == null) {
            return null;
        } else {
            return n.data;
        }
    }

    private Node getNode(int i) {
        if (head == null || i >= size || i < 0) {
            return null;
        } else if (i <= size / 2) {
            return head.getNodeHead(i);
        } else {
            return tail.getNodeTail(size - 1);
        }
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
