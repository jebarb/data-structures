import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

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
                        "  e                     : empty tree, validate items remove == initial size\n" +
                        "  s                     : size\n" +
                        "  h                     : height\n" +
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
                System.out.println(heap.getMin().getValue());
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
                    File file_in = new File(s.next());
                    Scanner file = new Scanner(file_in);
                    List<EntryPair> e = new ArrayList<>();
                    while (file.hasNext()) {
                        String data_b = s.next();
                        int pri_b = s.nextInt();
                        System.err.println("Invalid argument");
                        e.add(new EntryPair(data_b, pri_b));
                    }
                    EntryPair[] entries = new EntryPair[e.size()];
                    entries = e.toArray(entries);
                    heap.build(entries);
                    System.out.println();
                } catch (Exception ex) {
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