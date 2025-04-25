import java.util.Scanner;

public class UnifiedProject {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
        scanner.close();
    }

    static void avlMenu(Scanner scanner) {
        while (true) {
            System.out.println("AVL Tree Menu:");
            System.out.println("1. Region-based AVL tree (Project 1)");
            System.out.println("2. Count-based AVL tree (Project 2)");
            System.out.println("3. Back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                Project1.mainMenu();
            } else if (choice == 2) {
                Project2.mainMenu();
            } else if (choice == 3) {
                mainMenu(scanner);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void mainMenu(Scanner scanner){
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Load file in AVL tree");
            System.out.println("2. Load file in Hashing chain");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                avlMenu(scanner);
            } else if (choice == 2) {
                Project3.mainMenu();
            } else if (choice == 3) {
                System.out.println("Exiting...");
                System.exit(0);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

