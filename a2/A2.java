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
    private int size;

    public ListImpArray() {
        str = new String[INIT];
    }

    @Override
    public void ins(String s, int n) {
        if (str[n] != null) {
            ins(str[n], n + 1);
        }
        str[n] = s;
    }

    @Override
    public void rem(int n) {
        if (str[n] != null) {
            str[n] = null;
            size--;
        }
    }

    @Override
    public String get(int n) {
        return str[n];
    }

    @Override
    public int size() {
        int size = 0;
        for (String n : str) {
            if (n != null)
                size++;
        }
        return size;
    }

    @Override
    public boolean empty() {
        return size() == 0;
    }

}

class ListImpLinks implements ListImp {

    private String data;
    private ListImpLinks next;
    private int index;

    private ListImpLinks(String data, ListImpLinks next, int index) {
        this.data = data;
        this.next = next;
        this.index = index;
    }

    public ListImpLinks() {
        this(null, null, 0);
    }

    @Override
    public void ins(String s, int n) {
        if (data == null) {
            data = s;
            index = n;
        } else if (index >= n) {
            next = new ListImpLinks(data, next, n + 1);
            data = s;
            index = n;
            increment_index();
        } else if (next == null) {
            next = new ListImpLinks(s, null, n);
        } else if (n > index && n <= next.index) {
            next = new ListImpLinks(s, next, n);
            if (n == next.next.index) {
                increment_index();
            }
        } else {
            next.ins(s, n);
        }

    }

    private void increment_index() {
        ListImpLinks l = next.next;
        int i = next.index;
        while (l != null && l.index == i) {
            l.index++;
            l = l.next;
            i++;
        }
    }

    @Override
    public void rem(int n) {
        if (next == null) {
            if (index == n) {
                data = null;
            }
        } else if (index == n) {
            data = next.data;
            index = next.index;
            next = next.next;
        } else if (next.index == n) {
            if (next.next == null) {
                next = null;
            } else {
                next.data = next.next.data;
                next.index = next.next.index;
                next.next = next.next.next;
            }
        } else {
            next.rem(n);
        }
    }

    @Override
    public String get(int n) {
        if (index == n) {
            return data;
        } else if (next != null) {
            return this.next.get(n);
        }
        return null;
    }

    @Override
    public int size() {
        if (data == null) {
            return 0;
        } else if (next == null) {
            return 1;
        } else {
            return next.size() + 1;
        }
    }

    @Override
    public boolean empty() {
        return this.data == null;
    }
}

class Queue {

    private ListImp list;
    private int headIndex, tailIndex;

    public Queue(String s) {
        if (s.equals("ListImpLinks")) {
            list = new ListImpLinks();
        } else if (s.equals("ListImpArray")) {
            list = new ListImpArray();
        }
        headIndex = 0;
        tailIndex = 0;
    }

    public Queue() {
        this("ListImpLinks");
    }

    public void enq(String s) {
        list.ins(s, tailIndex++);
    }

    public void deq() {
        if (headIndex < tailIndex) {
            list.rem(headIndex++);
        }
    }

    public String front() {
        return list.get(headIndex);
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

        Queue qLL = new Queue("ListImpLinks");
        Queue qAL = new Queue("ListImpArray");

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
