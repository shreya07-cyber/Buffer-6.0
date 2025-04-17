package Buffer_2025;

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Budgeting budgeting = new Budgeting();
    static SplitManagement splitMgmt = new SplitManagement();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== SMARTSPEND ===");
            System.out.println("1. Budgeting");
            System.out.println("2. Bill Splitting / Trip Manager");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    budgeting.main(); // Budgeting operations
                    break;
                case "2":
                    splitMgmt.main(); // Splitting operations
                    break;
                case "0":
                    exit = true;
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}