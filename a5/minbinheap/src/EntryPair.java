public class EntryPair {
    public String value;
    public int priority;

    public EntryPair(String aValue, int aPriority) {
        value = aValue;
        priority = aPriority;
    }
    public EntryPair() {
    }

    public String getValue() {
        return value;
    }

    public int getPriority() {
        return priority;
    }

    public void setValue(String aValue) {
        value = aValue;
    }

    public void setPriority(int aPriority) {
        priority = aPriority;
    }
}