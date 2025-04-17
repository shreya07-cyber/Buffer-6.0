package models;

import java.time.LocalDate;

public class Transaction {
    public String description;
    public double amount;
    public String category;
    public LocalDate date;

    public Transaction(String description, double amount, String category, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
