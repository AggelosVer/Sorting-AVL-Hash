import java.util.ArrayList;
import java.util.List;

class AVLTree {
    AVLNode root;

    class AVLNode {
        int key;
        List<Entry> entries;
        int height;
        AVLNode left, right;

        AVLNode(int key, Entry entry) {
            this.key = key;
            this.entries = new ArrayList<>();
            this.entries.add(entry);
            this.height = 1;
        }
    }

    public AVLNode insert(AVLNode node, int key, Entry entry) {
        if (node == null) {
            return new AVLNode(key, entry);
        }
        if (key < node.key) {
            node.left = insert(node.left, key, entry);
        } else if (key > node.key) {
            node.right = insert(node.right, key, entry);
        } else {
            node.entries.add(entry);
            return node;
        }
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node);
        if (balance > 1 && key < node.left.key) {
            return rotateRight(node);
        }
        if (balance < -1 && key > node.right.key) {
            return rotateLeft(node);
        }
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && key < node.right.key) {
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

    public AVLNode findMin(AVLNode node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public AVLNode findMax(AVLNode node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public void preOrder(AVLNode node, int level) {
        if (node != null) {
            printIndent(level);
            System.out.println("Count: " + node.key + " (" + node.entries.size() + " entries)");
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


