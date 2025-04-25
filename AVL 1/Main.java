import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main {

    public static List<Entry> readFromFile(String filePath) {
        List<Entry> dataList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines.subList(1, lines.size())) {
                String[] parts = line.split(",", 4);
                int period = Integer.parseInt(parts[0]);
                String birthDeath = parts[1];
                String region = parts[2].replace("\"", "");
                int count = Integer.parseInt(parts[3]);
                dataList.add(new Entry(period, birthDeath, region, count));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static void main(String[] args) {
        List<Entry> dataList = readFromFile("data.txt");
        AVLTree tree = new AVLTree();

        for (Entry entry : dataList) {
            tree.root = tree.insert(tree.root, entry.getRegion(), entry);
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Print AVL tree");
            System.out.println("2. Search for count of births");
            System.out.println("3. Modify the amount of births");
            System.out.println("4. Delete data for a region and period");
            System.out.println("5. Exit");
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
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
