import java.util.List;
import java.util.Scanner;

class Project2 {
    public static void mainMenu() {
        List<Entry> dataList = Main.readFromFile("data.txt");
        AVLTree tree = new AVLTree();

        for (Entry entry : dataList) {
            if (entry.getBirthDeath().equals("Births")) {
                tree.root = tree.insert(tree.root, entry.getCount(), entry);
            }
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Project 2 Menu:");
            System.out.println("1. Search for the region(s) with the minimum birth count");
            System.out.println("2. Search for the region(s) with the maximum birth count");
            System.out.println("3. Exit to AVL menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                AVLTree.AVLNode minNode = tree.findMin(tree.root);
                if (minNode != null) {
                    System.out.println("Region(s) with the minimum birth count (" + minNode.countKey + "):");
                    for (Entry entry : minNode.entries) {
                        System.out.println("Region: " + entry.getRegion() + ", Period: " + entry.getPeriod());
                    }
                } else {
                    System.out.println("Tree is empty.");
                }
            } else if (choice == 2) {
                AVLTree.AVLNode maxNode = tree.findMax(tree.root);
                if (maxNode != null) {
                    System.out.println("Region(s) with the maximum birth count (" + maxNode.countKey + "):");
                    for (Entry entry : maxNode.entries) {
                        System.out.println("Region: " + entry.getRegion() + ", Period: " + entry.getPeriod());
                    }
                } else {
                    System.out.println("Tree is empty.");
                }
            } else if (choice == 3) {
                System.out.println("Returning to AVL menu...");
                UnifiedProject.avlMenu(scanner);
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
