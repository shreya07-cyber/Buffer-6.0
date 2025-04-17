package services;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class SlidingWindowPredictor {
    private final Map<YearMonth, Double> monthTotals = new TreeMap<>();

    // Add expense and aggregate by month
    public void addExpense(LocalDate date, double amount) {
        YearMonth yearMonth = YearMonth.from(date); // Get the Year-Month
        monthTotals.put(yearMonth, monthTotals.getOrDefault(yearMonth, 0.0) + amount);
    }

    // Predict based on monthly totals
    public void predict() {
        if (monthTotals.size() < 3) {
            System.out.println("Not enough data for prediction (need at least 3 months of data).");
            System.out.println("Data available for months: " + monthTotals.keySet());
            return;
        }

        // Get the last 3 months
        List<Double> lastMonths = new ArrayList<>();
        int count = 0;
        for (Double total : new TreeMap<>(monthTotals).descendingMap().values()) {
            lastMonths.add(total);
            count++;
            if (count >= 3) break;  // We only need the last 3 months
        }

        double sum = lastMonths.stream().mapToDouble(Double::doubleValue).sum();
        double avg = sum / lastMonths.size();

        // Pretty print the prediction
        System.out.println("=================================================================================================");
        System.out.println("              📊 Projected Monthly Spending Analysis 📊");
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("🔍 Based on the analysis of the last 3 months, your projected monthly spending is: ₹%.2f\n", avg);
        double budget = 5000.0;  // Replace with user-defined budget
        System.out.println("--------------------------------------------------------------------------------------------------");

        if (avg > budget) {
            System.out.println("⚠️ Budget Overrun Risk Detected! Consider reducing your spending.");
        } else {
            System.out.println("✅ Great! Your spending is under control.");
        }
        System.out.println("============================================================================");
    }


}
