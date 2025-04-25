import java.util.List;
import java.util.Scanner;

class Project1 {
    public static void mainMenu() {
        AVLTree tree = new AVLTree();
        List<Entry> dataList = Main.readFromFile("data.txt");

        for (Entry entry : dataList) {
            tree.root = tree.insert(tree.root, entry.getRegion(), entry);
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Project 1 Menu:");
            System.out.println("1. Print AVL tree");
            System.out.println("2. Search for count of births");
            System.out.println("3. Modify the amount of births");
            System.out.println("4. Delete data for a region and period");
            System.out.println("5. Exit to AVL menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Preorder traversal of the constructed AVL tree is:");
                tree.preOrder(tree.root, 0);
            } else if (choice == 2) {
                System.out.print("Enter the region: ");
                String region = scanner.nextLine();
                System.out.print("Enter the period: ");
                int period = scanner.nextInt();
                int count = tree.search(tree.root, region, period);
                if (count != -1) {
                    System.out.println("Count of births for " + region + " in period " + period + ": " + count);
                } else {
                    System.out.println("No data found for the specified region and period.");
                }
            } else if (choice == 3) {
                System.out.print("Enter the region: ");
                String region = scanner.nextLine();
                System.out.print("Enter the period: ");
                int period = scanner.nextInt();
                System.out.print("Enter the new count of births: ");
                int newCount = scanner.nextInt();
                boolean success = tree.modify(tree.root, region, period, newCount);
                if (success) {
                    System.out.println("Updated count of births for " + region + " in period " + period + " to " + newCount);
                } else {
                    System.out.println("No data found for the specified region and period.");
                }
            } else if (choice == 4) {
                System.out.print("Enter the region: ");
                String region = scanner.nextLine();
                System.out.print("Enter the period: ");
                int period = scanner.nextInt();
                tree.root = tree.delete(tree.root, region, period);
                System.out.println("Deleted data for " + region + " in period " + period);
            } else if (choice == 5) {
                System.out.println("Returning to AVL menu...");
                UnifiedProject.avlMenu(scanner);
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
