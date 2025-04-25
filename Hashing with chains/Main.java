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

        HashTableWithChaining hashTable = new HashTableWithChaining(3);

        for (Entry entry : dataList) {
            if (entry.getBirthDeath().equals("Births")) {
                hashTable.insert(entry);
            }
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Search for the amount of birth in a period and a region");
            System.out.println("2. Modify the count of birth in a period and a region");
            System.out.println("3. Delete an index when it comes to a region");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter region: ");
                String region = scanner.nextLine();
                System.out.print("Enter period: ");
                int period = scanner.nextInt();
                Entry result = hashTable.search(region, period);
                if (result != null) {
                    System.out.println("Region: " + result.getRegion() + ", Period: " + result.getPeriod() + ", Births: " + result.getCount());
                } else {
                    System.out.println("Entry not found.");
                }
            } else if (choice == 2) {
                System.out.print("Enter region: ");
                String region = scanner.nextLine();
                System.out.print("Enter period: ");
                int period = scanner.nextInt();
                System.out.print("Enter new count of births: ");
                int newCount = scanner.nextInt();
                boolean success = hashTable.modify(region, period, newCount);
                if (success) {
                    System.out.println("Entry updated successfully.");
                } else {
                    System.out.println("Entry not found.");
                }
            } else if (choice == 3) {
                System.out.print("Enter region: ");
                String region = scanner.nextLine();
                System.out.print("Enter period: ");
                int period = scanner.nextInt();
                boolean success = hashTable.delete(region, period);
                if (success) {
                    System.out.println("Entry deleted successfully.");
                } else {
                    System.out.println("Entry not found.");
                }
            } else if (choice == 4) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
