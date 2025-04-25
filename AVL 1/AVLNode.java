import java.util.ArrayList;
import java.util.List;

class AVLNode {
    String key;
    List<Entry> entries;
    int height;
    AVLNode left, right;

    public AVLNode(String key, Entry entry) {
        this.key = key;
        this.entries = new ArrayList<>();
        this.entries.add(entry);
        this.height = 1;
    }
}
