import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

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
            if (entry.getBirthDeath().equals("Births")) {
                tree.root = tree.insert(tree.root, entry.getCount(), entry);
            }
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Search for the region(s) with the minimum birth count");
            System.out.println("2. Search for the region(s) with the maximum birth count");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                AVLTree.AVLNode minNode = tree.findMin(tree.root);
                if (minNode != null) {
                    System.out.println("Region(s) with the minimum birth count (" + minNode.key + "):");
                    for (Entry entry : minNode.entries) {
                        System.out.println("Region: " + entry.getRegion() + ", Period: " + entry.getPeriod());
                    }
                } else {
                    System.out.println("Tree is empty.");
                }
            } else if (choice == 2) {
                AVLTree.AVLNode maxNode = tree.findMax(tree.root);
                if (maxNode != null) {
                    System.out.println("Region(s) with the maximum birth count (" + maxNode.key + "):");
                    for (Entry entry : maxNode.entries) {
                        System.out.println("Region: " + entry.getRegion() + ", Period: " + entry.getPeriod());
                    }
                } else {
                    System.out.println("Tree is empty.");
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
