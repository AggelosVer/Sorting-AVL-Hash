import java.util.ArrayList;
import java.util.List;

class AVLTree {
    AVLNode root;

    class AVLNode {
        String key;
        List<Entry> entries;
        int height;
        AVLNode left, right;

        AVLNode(String key, Entry entry) {
            this.key = key;
            this.entries = new ArrayList<>();
            this.entries.add(entry);
            this.height = 1;
        }
    }

    public AVLNode insert(AVLNode node, String key, Entry entry) {
        if (node == null) {
            return new AVLNode(key, entry);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, entry);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key, entry);
        } else {
            node.entries.add(entry);
            return node;
        }
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node);
        if (balance > 1 && key.compareTo(node.left.key) < 0) {
            return rotateRight(node);
        }
        if (balance < -1 && key.compareTo(node.right.key) > 0) {
            return rotateLeft(node);
        }
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private AVLNode rotateLeft(AVLNode z) {
        AVLNode y = z.right;
        AVLNode T2 = y.left;
        y.left = z;
        z.right = T2;
        z.height = Math.max(getHeight(z.left), getHeight(z.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        return y;
    }

    private AVLNode rotateRight(AVLNode z) {
        AVLNode y = z.left;
        AVLNode T3 = y.right;
        y.right = z;
        z.left = T3;
        z.height = Math.max(getHeight(z.left), getHeight(z.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        return y;
    }

    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalance(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    public int search(AVLNode node, String region, int period) {
        if (node == null) {
            return -1; 
        }
        if (region.compareTo(node.key) < 0) {
            return search(node.left, region, period);
        } else if (region.compareTo(node.key) > 0) {
            return search(node.right, region, period);
        } else {
            for (Entry entry : node.entries) {
                if (entry.getPeriod() == period && entry.getBirthDeath().equals("Births")) {
                    return entry.getCount();
                }
            }
            return -1; 
        }
    }

    public boolean modify(AVLNode node, String region, int period, int newCount) {
        if (node == null) {
            return false; 
        }
        if (region.compareTo(node.key) < 0) {
            return modify(node.left, region, period, newCount);
        } else if (region.compareTo(node.key) > 0) {
            return modify(node.right, region, period, newCount);
        } else {
            for (Entry entry : node.entries) {
                if (entry.getPeriod() == period && entry.getBirthDeath().equals("Births")) {
                    entry.setCount(newCount);
                    return true; 
                }
            }
            return false; 
        }
    }


    public AVLNode delete(AVLNode node, String region, int period) {
        if (node == null) {
            return node;
        }
        if (region.compareTo(node.key) < 0) {
            node.left = delete(node.left, region, period);
        } else if (region.compareTo(node.key) > 0) {
            node.right = delete(node.right, region, period);
        } else {
            node.entries.removeIf(entry -> entry.getPeriod() == period && entry.getBirthDeath().equals("Births"));
            if (node.entries.isEmpty()) {
                if (node.left == null || node.right == null) {
                    node = (node.left != null) ? node.left : node.right;
                } else {
                    AVLNode temp = minValueNode(node.right);
                    node.key = temp.key;
                    node.entries = temp.entries;
                    node.right = delete(node.right, temp.key, period);
                }
            }
        }
        if (node == null) {
            return node;
        }
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        int balance = getBalance(node);
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void preOrder(AVLNode node, int level) {
        if (node != null) {
            printIndent(level);
            System.out.println(node.key + " (" + node.entries.size() + " entries)");
            for (Entry entry : node.entries) {
                printIndent(level + 1);
                System.out.println(entry);
            }
            preOrder(node.left, level + 1);
            preOrder(node.right, level + 1);
        }
    }

    private void printIndent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("   ");
        }
    }
}

