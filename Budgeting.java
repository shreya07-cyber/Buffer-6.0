package Buffer_2025;

import java.time.LocalDate;
import java.util.*;

import models.Transaction;
import services.*;
import utils.InputValidator;

public class Budgeting {
    void main() {
        Scanner scanner = new Scanner(System.in);
        TransactionService service = new TransactionService();
        // hardcoded data (3 months of transactions)
        HardcodedData.addTransactions(service);

        System.out.print("Enter your total monthly budget: ₹");
        double budget = InputValidator.validateDouble(scanner);
        service.setTotalBudget(budget);

        TrieClassifier classifier = new TrieClassifier();
        SlidingWindowPredictor predictor = new SlidingWindowPredictor();

        HardcodedData.addPredictorData(predictor);
        BudgetAllocator allocator = new BudgetAllocator();

        while (true) {
            System.out.println("\n==== Budget CLI Menu ====");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Spending Summary");
            System.out.println("3. Allocate Budget (Zero-Based)");
            System.out.println("4. Predict Future Expenses");
            System.out.println("5. Show Recent Transactions");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amt = InputValidator.validateDouble(scanner);
                    System.out.print("Date (yyyy-mm-dd): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());

                    String category = classifier.classify(desc);
                    service.addTransaction(desc, amt, category, date);
                    System.out.println("Transaction added successfully");
                    predictor.addExpense(date, amt);
                    break;

                case "2":
                    service.printSummaryWithBarChart();
                    break;

                case "3":
                    List<Transaction> allTransactions = service.getTransactions();
                    double totalBudget = service.getTotalBudget(); // user's total budget

                    Map<String, Double> allocations = BudgetAllocator.allocate(allTransactions, totalBudget);
                    BudgetAllocator.printAllocations(allocations);
                    break;

                case "4":
                    predictor.predict();
                    break;

                case "5":
                    // Display recent transactions (e.g., last 5 transactions)
                    System.out.print("Enter number of recent transactions to display: ");
                    int num = InputValidator.validateInteger(scanner);
                    service.showRecentTransactions(num);
                    break;

                case "6":
                    System.out.println("Exiting... Bye!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
