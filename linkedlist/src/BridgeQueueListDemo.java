import java.util.Random;

public class BridgeQueueListDemo {

    private static Random RAND = new Random();

    private static void enqTest(int n, Queue ll, Queue arr) {
        System.out.println("\nenq " + n + ": ");
        while (n-- > 1) {
            ll.enq(Integer.toString(n));
            arr.enq(Integer.toString(n));
            System.out.println("ll enq:  " + ll.front());
            System.out.println("ll size: " + ll.size());
            System.out.println("a enq:   " + arr.front());
            System.out.println("a size:  " + arr.size());
        }
    }

    private static void deqTest(int n, Queue ll, Queue arr) {
        System.out.println("\ndeq " + n + ": ");
        while (n-- > 0) {
            ll.deq();
            arr.deq();
            System.out.println("ll deq:  " + ll.front());
            System.out.println("ll size: " + ll.size());
            System.out.println("a deq:   " + arr.front());
            System.out.println("a size:  " + arr.size());
        }
    }

    private static void testBoth(int n, Queue ll, Queue arr) {
        n++;
        while (n-- > 0) {
            if (RAND.nextBoolean()) {
                enqTest(RAND.nextInt(6) + 2, ll, arr);
            } else {
                deqTest(RAND.nextInt(6) + 2, ll, arr);
            }
        }
        while (!ll.empty()) {
            deqTest(1, ll, arr);
        }
    }

    public static void main(String args[]) {

        Queue ll1 = new Queue("ListImpLinks");
        Queue ar1 = new Queue("ListImpArray");

        if (args.length == 0) {

            //testBoth(3, ll1, ar1);
            enqTest(5,ll1,ar1);
            //System.out.println("\nInserted into middle");
            ll1.insmiddle();
            ar1.insmiddle();
            //System.out.println("ll1 front: " + ll1.front() + "\nar1 front: " + ar1.front() + "\nInserted into front");
            ll1.insfront();
            ar1.insfront();
            //System.out.println("ll1 front: " + ll1.front()  + "\nar1 front: " + ar1.front() + "\n");
            //enqTest(5,ll1,ar1);
            deqTest(7,ll1,ar1);
            testBoth(3, ll1, ar1);

            System.out.println("\nEmpty on finish: ");
            System.out.println(ll1.empty());
            System.out.println(ar1.empty());


        } else {

            ll1.enq(args[0]);
            ar1.enq(args[0]);
            System.out.println(ll1.size());
            System.out.println(ar1.size());
            System.out.println(ll1.front());
            System.out.println(ar1.front());
            ll1.deq();
            ar1.deq();
            System.out.println(ll1.size());
            System.out.println(ar1.size());

            for (int i = 1; i < args.length; i++) {
                ll1.enq(args[i]);
                ar1.enq(args[i]);
            }

            for (int i = 1; i < args.length; i++) {
                System.out.println(ll1.front());
                System.out.println(ar1.front());
                ll1.deq();
                ar1.deq();
                System.out.println(ll1.size());
                System.out.println(ar1.size());
            }

        }

        System.out.println();

    }
}
