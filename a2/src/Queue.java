public class Queue {

    private ListImp list;

    public Queue(String s) {
        if (s.equals("ListImpLinks")) {
            list = new ListImpLinks();
        } else if (s.equals("ListImpArray")) {
            list = new ListImpArray();
        }
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

    public void insmiddle() {
        list.ins("TEST INSERT MIDDLE", size() / 2);
    }

    public void insfront() {
        list.ins("TEST INSERT FRONT", 0);
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
