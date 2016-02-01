/**
 * Created by jebarb on 1/28/16.
 */
public class ListImpLinks implements ListImp {

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
