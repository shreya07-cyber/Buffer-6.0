package services;

import models.Transaction;
import services.SegmentTree;

import java.time.LocalDate;
import java.util.*;

public class TransactionService {
    private final Deque<Transaction> transactions = new LinkedList<>();  // Deque to store transactions
    private final Map<String, Double> categorySums = new HashMap<>();
    private final Map<LocalDate, Integer> dateToIndex = new HashMap<>();
    private int nextIndex = 0;
    private final SegmentTree segmentTree = new SegmentTree(365); // 1 year support

    private double totalBudget = 0.0; // 🆕 user-defined budget

    public void setTotalBudget(double budget) {
        this.totalBudget = budget;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void addTransaction(String desc, double amount, String category, LocalDate date) {
        Transaction newTransaction = new Transaction(desc, amount, category, date);

        // Add the new transaction to the front of the deque
        transactions.offerFirst(newTransaction);

        // Ensure the deque doesn't exceed a fixed size, e.g., the last 50 transactions
        if (transactions.size() > 50) {
            transactions.pollLast();  // Remove the oldest transaction if the deque exceeds the limit
        }

        categorySums.put(category, categorySums.getOrDefault(category, 0.0) + amount);

        int index = getDateIndex(date);
        segmentTree.update(index, amount);
    }

    private int getDateIndex(LocalDate date) {
        return dateToIndex.computeIfAbsent(date, d -> nextIndex++);
    }

    public void printSummaryWithBarChart() {
        if (categorySums.isEmpty()) {
            System.out.println("No spending data to show.");
            return;
        }

        PriorityQueue<Map.Entry<String, Double>> maxHeap =
                new PriorityQueue<>((a, b) -> Double.compare(b.getValue(), a.getValue()));
        maxHeap.addAll(categorySums.entrySet());

        double max = maxHeap.peek().getValue();

        System.out.println("\n📊 Spending Summary for Last Month (Descending Order):");
        System.out.println("-----------------------------------------------------");

        while (!maxHeap.isEmpty()) {
            Map.Entry<String, Double> entry = maxHeap.poll();
            String bar = getBar(entry.getValue(), max);
            System.out.printf("%-15s | %-30s | ₹%.2f\n", entry.getKey(), bar, entry.getValue());
        }

        System.out.println("-----------------------------------------------------");
    }

    private String getBar(double value, double max) {
        int maxBarLength = 30;
        int filled = (int) ((value / max) * maxBarLength);
        return "█".repeat(filled);
    }

    public Map<String, Double> getCategorySums() {
        return categorySums;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);  // Convert deque to list for easier iteration
    }

    // Method to show recent transactions
    public void showRecentTransactions(int num) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        System.out.println("\n==== Recent Transactions ====");
        System.out.println("+------------+-------------------+--------------+-----------------------------------+");
        System.out.printf("| %-10s | %-25s | %-25s | %-10s |\n", "Date", "Description", "Category", "Amount");
        System.out.println("+------------+-------------------+--------------+-----------------------------------+");

        int count = 0;
        for (Transaction t : transactions) {
            if (count >= num) break;
            System.out.printf("| %-10s | %-25s | %-25s | ₹%-10.2f |\n",
                    t.getDate(), t.getDescription(), t.getCategory(), t.getAmount());
            count++;
        }

        System.out.println("+------------+-------------------+--------------+------------------------------------+");
    }

}
