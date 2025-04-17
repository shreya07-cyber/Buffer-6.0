package services;

import models.Transaction;

import java.time.LocalDate;
import java.util.*;

public class BudgetAllocator {

    public static Map<String, Double> allocate(List<Transaction> transactions, double totalBudget) {
        Map<String, Double> categorySums = new HashMap<>();

        // Step 1: Find latest year and month
        int latestYear = 0;
        int latestMonth = 0;
        for (Transaction t : transactions) {
            int year = t.getDate().getYear();
            int month = t.getDate().getMonthValue();

            if (year > latestYear || (year == latestYear && month > latestMonth)) {
                latestYear = year;
                latestMonth = month;
            }
        }

        // Step 2: Aggregate category totals for the latest month
        double totalSpent = 0;
        for (Transaction t : transactions) {
            LocalDate date = t.getDate();
            if (date.getYear() == latestYear && date.getMonthValue() == latestMonth) {
                String category = t.getCategory();
                double amount = t.getAmount();
                categorySums.put(category, categorySums.getOrDefault(category, 0.0) + amount);
                totalSpent += amount;
            }
        }

        // Step 3: Calculate budget allocations proportionally
        Map<String, Double> budgetAllocations = new HashMap<>();
        for (Map.Entry<String, Double> entry : categorySums.entrySet()) {
            String category = entry.getKey();
            double percentage = entry.getValue() / totalSpent;
            double allocated = percentage * totalBudget;
            budgetAllocations.put(category, allocated);
        }

        return budgetAllocations;
    }

    public static void printAllocations(Map<String, Double> allocations) {
        System.out.println("\n===== 🧾 Zero-Based Budget Allocation Summary =====");

        // Calculate the max length for label alignment and find max allocated value
        int maxLabelLength = allocations.keySet().stream()
                .mapToInt(String::length)
                .max().orElse(10);

        double maxValue = allocations.values().stream()
                .mapToDouble(Double::doubleValue)
                .max().orElse(1.0);

        for (Map.Entry<String, Double> entry : allocations.entrySet()) {
            String category = entry.getKey();
            double amount = entry.getValue();
            int barLength = (int) ((amount / maxValue) * 25); // max 25-bar scale

            String bar = "█".repeat(barLength);
            System.out.printf("%-" + (maxLabelLength + 2) + "s | %-25s ₹%.2f%n", category, bar, amount);
        }

        System.out.println("==================================================\n");
    }

}
