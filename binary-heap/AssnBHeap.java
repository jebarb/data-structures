import java.util.Scanner;
import java.util.InputMismatchException;

public class AssnBHeap {

    private static Scanner s;
    private static MinBinHeap heap = new MinBinHeap();

    private static void printHelp() {
        System.out.println("Usage: [OPTION]... [OPTION] [ARGUMENT]...");
        System.out.println("Options:");
        System.out.println(
                "  help                  : print this message\n" +
                        "  new                   : make a new empty heap\n" +
                        "  i [STRING] [PRIORITY] : insert a string with priority\n" +
                        "  d                     : remove min\n" +
                        "  m                     : print min\n" +
                        "  b [FILE]              : Build heap from file\n" +
                        "  f [INT]               : fill the tree with random strings and priorities\n" +
                        "  e                     : empty tree, validate items removed == initial size\n" +
                        "  s                     : size\n" +
                        "  q                     : quit the tester loop\n" +
                        "  p                     : print the heap for inspection");
    }

    private static void process() {
        String str = s.next("\\S*");
        switch (str) {
            case "new":
                heap = new MinBinHeap();
                break;
            case "i":
                String data = s.next();
                int pri = 0;
                try {
                    pri = s.nextInt();
                } catch (Exception ex) {
                    System.err.println("Invalid argument");
                }
                heap.insert(new EntryPair(data, pri));
                break;
            case "d":
                heap.delMin();
                break;
            case "m":
                if (heap.size() != 0) System.out.println(heap.getMin().getValue());
                else System.out.println("null");
                break;
            case "e":
                int i = 0;
                int init_size = heap.size();
                while (heap.size() != 0) {
                    heap.delMin();
                    i++;
                }
                System.out.println(i == init_size);
                break;
            case "b":
                try {
                    int eSize = s.nextInt();
                    EntryPair[] entries = new EntryPair[eSize];
                    while (--eSize > -1) entries[eSize] = new EntryPair(MyRandom.nextString(), MyRandom.rand(1, 100));
                    heap.build(entries);
                } catch (InputMismatchException ex) {
                    System.err.println("Invalid argument");
                }
                break;
            case "s":
                System.out.println(heap.size());
                break;
            case "q":
                System.exit(0);
                break;
            case "p":
                heap.print();
                break;
            case "f":
                int num = 0;
                try {
                    num = s.nextInt();
                } catch (Exception ex) {
                    System.err.println("Invalid argument");
                }
                while (num-- > 0)
                    heap.insert(new EntryPair(MyRandom.nextString(), MyRandom.rand(1, 100)));
                break;
            case "help":
                printHelp();
                break;
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            s = new Scanner(System.in);
            System.out.println("Type 'help' for usage");
            while (true)
                process();
        } else {
            String in = "";
            for (String arg : args)
                in = in.concat(arg + " ");
            s = new Scanner(in);
            while (s.hasNext()) process();
        }
    }
}

