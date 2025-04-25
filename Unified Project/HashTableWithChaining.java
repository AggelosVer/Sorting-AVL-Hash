import java.util.ArrayList;
import java.util.LinkedList;

class HashTableWithChaining {
    private ArrayList<LinkedList<Entry>> table;
    private int size;

    public HashTableWithChaining(int size) {
        this.size = size;
        table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            table.add(new LinkedList<>());
        }
    }

    private int hash(String region) {
        int hashValue = 0;
        for (char c : region.toCharArray()) {
            hashValue += (int) c;
        }
        return hashValue % 3; 
    }

    public void insert(Entry entry) {
        int index = hash(entry.getRegion());
        table.get(index).add(entry);
    }

    public Entry search(String region, int period) {
        int index = hash(region);
        for (Entry entry : table.get(index)) {
            if (entry.getRegion().equals(region) && entry.getPeriod() == period) {
                return entry;
            }
        }
        return null;
    }

    public boolean modify(String region, int period, int newCount) {
        Entry entry = search(region, period);
        if (entry != null) {
            entry.setCount(newCount);
            return true;
        }
        return false;
    }

    public boolean delete(String region, int period) {
        int index = hash(region);
        LinkedList<Entry> chain = table.get(index);
        for (Entry entry : chain) {
            if (entry.getRegion().equals(region) && entry.getPeriod() == period) {
                chain.remove(entry);
                return true;
            }
        }
        return false;
    }
}