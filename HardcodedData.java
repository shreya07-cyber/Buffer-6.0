package services;

import java.time.LocalDate;

public class HardcodedData {
    // Seeds TransactionService with hardcoded transactions
    public static void addTransactions(TransactionService service) {
        // January transactions
        service.addTransaction("groceries", 1500, "Food", LocalDate.of(2025, 1, 10));
        service.addTransaction("uber", 300, "Transport", LocalDate.of(2025, 1, 12));
        service.addTransaction("movie", 500, "Entertainment", LocalDate.of(2025, 1, 15));
        service.addTransaction("coffee", 200, "Food", LocalDate.of(2025, 1, 18));
        service.addTransaction("fuel", 700, "Transport", LocalDate.of(2025, 1, 20));
        service.addTransaction("gym membership", 1000, "Health", LocalDate.of(2025, 1, 22));
        service.addTransaction("netflix", 400, "Entertainment", LocalDate.of(2025, 1, 25));

        // February transactions
        service.addTransaction("groceries", 1200, "Food", LocalDate.of(2025, 2, 10));
        service.addTransaction("uber", 400, "Transport", LocalDate.of(2025, 2, 11));
        service.addTransaction("movie", 700, "Entertainment", LocalDate.of(2025, 2, 13));
        service.addTransaction("coffee", 250, "Food", LocalDate.of(2025, 2, 16));
        service.addTransaction("fuel", 800, "Transport", LocalDate.of(2025, 2, 18));
        service.addTransaction("gym membership", 1200, "Health", LocalDate.of(2025, 2, 20));
        service.addTransaction("netflix", 500, "Entertainment", LocalDate.of(2025, 2, 21));

        // March transactions
        service.addTransaction("groceries", 1800, "Food", LocalDate.of(2025, 3, 5));
        service.addTransaction("uber", 350, "Transport", LocalDate.of(2025, 3, 6));
        service.addTransaction("movie", 600, "Entertainment", LocalDate.of(2025, 3, 7));
        service.addTransaction("coffee", 220, "Food", LocalDate.of(2025, 3, 8));
        service.addTransaction("fuel", 750, "Transport", LocalDate.of(2025, 3, 10));
        service.addTransaction("gym membership", 1300, "Health", LocalDate.of(2025, 3, 12));
        service.addTransaction("netflix", 450, "Entertainment", LocalDate.of(2025, 3, 15));
    }

    // Seeds SlidingWindowPredictor with the same hardcoded data for prediction
    public static void addPredictorData(SlidingWindowPredictor predictor) {
        // January transactions
        predictor.addExpense(LocalDate.of(2025, 1, 10), 1500);
        predictor.addExpense(LocalDate.of(2025, 1, 12), 300);
        predictor.addExpense(LocalDate.of(2025, 1, 15), 500);
        predictor.addExpense(LocalDate.of(2025, 1, 18), 200);
        predictor.addExpense(LocalDate.of(2025, 1, 20), 700);
        predictor.addExpense(LocalDate.of(2025, 1, 22), 1000);
        predictor.addExpense(LocalDate.of(2025, 1, 25), 400);

        // February transactions
        predictor.addExpense(LocalDate.of(2025, 2, 10), 1200);
        predictor.addExpense(LocalDate.of(2025, 2, 11), 400);
        predictor.addExpense(LocalDate.of(2025, 2, 13), 700);
        predictor.addExpense(LocalDate.of(2025, 2, 16), 250);
        predictor.addExpense(LocalDate.of(2025, 2, 18), 800);
        predictor.addExpense(LocalDate.of(2025, 2, 20), 1200);
        predictor.addExpense(LocalDate.of(2025, 2, 21), 500);

        // March transactions
        predictor.addExpense(LocalDate.of(2025, 3, 5), 1800);
        predictor.addExpense(LocalDate.of(2025, 3, 6), 350);
        predictor.addExpense(LocalDate.of(2025, 3, 7), 600);
        predictor.addExpense(LocalDate.of(2025, 3, 8), 220);
        predictor.addExpense(LocalDate.of(2025, 3, 10), 750);
        predictor.addExpense(LocalDate.of(2025, 3, 12), 1300);
        predictor.addExpense(LocalDate.of(2025, 3, 15), 450);
    }
}
