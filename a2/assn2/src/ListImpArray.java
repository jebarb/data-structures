/**
 * Created by jebarb on 1/26/16.
 */
public class ListImpArray implements ListImp {

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
