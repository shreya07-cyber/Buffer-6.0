package utils;

import java.util.Scanner;

public class InputValidator {
    public static double validateDouble(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }

    public static int validateInteger(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);  // Try parsing the integer
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid integer: ");
            }
        }
    }
}
