public class ListImpArray implements ListImp {

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
        if (str[n + headIndex] != null) {
            ins_no_idx(str[n + headIndex], n + 1);
        }
        str[n + headIndex] = s;
        tailIndex++;
    }

    private void ins_no_idx(String s, int n) {
        if (str[n] != null) {
            ins_no_idx(str[n], n + 1);
        } else {
            str[n] = s;
        }
    }

    @Override
    public void rem(int n) {
        if (str[n + headIndex] != null) {
            str[n + headIndex] = null;
            headIndex++;
            if (n != 0 || n != size() - 1) {
                ins_no_idx(get(headIndex-1), headIndex-1);
            }
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
