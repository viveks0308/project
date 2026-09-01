import java.util.ArrayList;
import java.util.Scanner;

// Represents a single bank account
class Account {
    private int accountNumber;
    private String holderName;
    private double balance;

    public Account(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Deposit money (amount must be positive)
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    // Withdraw money (amount must be positive and not exceed balance)
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%-10d %-20s %12.2f", accountNumber, holderName, balance);
    }
}

// Main application with menu-driven dashboard
public class Main {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextAccountNumber = 1001;

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> openAccount();
                case 2 -> displayAllAccounts();
                case 3 -> depositMoney();
                case 4 -> withdrawMoney();
                case 5 -> closeAccount();
                case 6 -> searchAccount();
                case 7 -> System.out.println("\nThank you for using the system. Goodbye!");
                default -> System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    // Show the main menu
    private static void showMenu() {
        System.out.println("\n========== ACCOUNT MANAGEMENT SYSTEM ==========");
        System.out.println("1. Open Account");
        System.out.println("2. Display All Accounts");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Close Account");
        System.out.println("6. Search Account by Number");
        System.out.println("7. Exit");
        System.out.println("===============================================");
    }

    // Open a new account with a positive opening balance
    private static void openAccount() {
        System.out.println("\n--- Open Account ---");
        String name = readString("Enter holder name: ");
        double balance = readDouble("Enter opening balance: ");

        if (balance < 0) {
            System.out.println("Opening balance cannot be negative.");
            return;
        }

        Account account = new Account(nextAccountNumber++, name, balance);
        accounts.add(account);
        System.out.println("Account created successfully. Account number: " + account.getAccountNumber());
    }

    // Display all accounts in a formatted table
    private static void displayAllAccounts() {
        System.out.println("\n--- All Accounts ---");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.printf("%-10s %-20s %12s%n", "Acc. No", "Holder Name", "Balance");
        System.out.println("------------------------------------------------");
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    // Deposit money into an existing account
    private static void depositMoney() {
        System.out.println("\n--- Deposit Money ---");
        Account account = findAccount();
        if (account == null) {
            return;
        }
        double amount = readDouble("Enter deposit amount: ");
        if (account.deposit(amount)) {
            System.out.printf("Deposit successful. New balance: %.2f%n", account.getBalance());
        } else {
            System.out.println("Invalid amount. Deposit must be positive.");
        }
    }

    // Withdraw money with balance validation
    private static void withdrawMoney() {
        System.out.println("\n--- Withdraw Money ---");
        Account account = findAccount();
        if (account == null) {
            return;
        }
        double amount = readDouble("Enter withdrawal amount: ");
        if (account.withdraw(amount)) {
            System.out.printf("Withdrawal successful. New balance: %.2f%n", account.getBalance());
        } else {
            System.out.println("Invalid amount. Must be positive and not exceed balance.");
        }
    }

    // Close (remove) an account by number
    private static void closeAccount() {
        System.out.println("\n--- Close Account ---");
        Account account = findAccount();
        if (account == null) {
            return;
        }
        accounts.remove(account);
        System.out.println("Account " + account.getAccountNumber() + " closed successfully.");
    }

    // Search and display a single account
    private static void searchAccount() {
        System.out.println("\n--- Search Account ---");
        Account account = findAccount();
        if (account == null) {
            return;
        }
        System.out.printf("%-10s %-20s %12s%n", "Acc. No", "Holder Name", "Balance");
        System.out.println("------------------------------------------------");
        System.out.println(account);
    }

    // Helper: find an account by number entered by the user
    private static Account findAccount() {
        int number = readInt("Enter account number: ");
        for (Account account : accounts) {
            if (account.getAccountNumber() == number) {
                return account;
            }
        }
        System.out.println("Account not found.");
        return null;
    }

    // Helper: read an integer safely
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    // Helper: read a double safely
    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Helper: read a non-empty string
    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty.");
        }
    }
}