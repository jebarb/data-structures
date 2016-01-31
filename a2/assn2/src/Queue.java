/**
 * Created by jebarb on 1/26/16.
 */
public class Queue {

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

    public void insmiddle() {
        list.ins("TEST INSERT MIDDLE", tailIndex++ / 2);
    }

    public void insfront() {
        list.ins("TEST INSERT FRONT", headIndex);
        tailIndex++;
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
