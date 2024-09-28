package budgetmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shopkeeper {
    Scanner sc = new Scanner(System.in);
    Boolean isRunning = true;
    StringBuilder menu = new StringBuilder();
    List<String> purchases = new ArrayList<>();
    double balance = 0.0;
    double totalIncome = 0.0;


    private void createMenu() {
        menu.append("Choose your action:" + "\n");
        menu.append("1) Add income" + "\n");
        menu.append("2) Add purchase" + "\n");
        menu.append("3) Show list of purchases" + "\n");
        menu.append("4) Balance" + "\n");
        menu.append("0) Exit");
    }

    void start() {
        createMenu();

        while (isRunning) {
            System.out.println(menu);
            int input = (int) getDoubleInput("");

            switch (input) {
                case 1 -> addIncome();
                case 2 -> addPurchase();
                case 3 -> showPurchasesList();
                case 4 -> showBalance();
                case 0 -> isRunning = false;
            }
            System.out.println();
        }
        System.out.println("Bye!");
    }


    private void addIncome() {
        System.out.println();
        double income = getDoubleInput("Enter income:");
        balance += income;
        System.out.println("Income was added!");
    }

    private void addPurchase() {
        System.out.println();
        String itemName = getStringInput("Enter purchase name:");
        double itemPrice = getDoubleInput("Enter its price:");
        purchases.add(String.format("%s %s", itemName, itemPrice));
        totalIncome += itemPrice;
        balance -= itemPrice;
        System.out.println("Purchase was added!");
    }

    private void showPurchasesList() {
        System.out.println();
        if (purchases.isEmpty()) {
            System.out.println("The purchase list is empty");
        } else {
            purchases.forEach(System.out::println);
            System.out.printf("Total sum: $%.2f\n", totalIncome);
        }
    }

    private void showBalance() {
        System.out.println();
        System.out.printf("Balance: $%.2f\n", balance);
    }

    private double getDoubleInput(String message) {
        if (!message.isBlank()) {
            System.out.println(message);
        }
        double value = sc.nextDouble();
        sc.nextLine();
        return value;
    }

    private String getStringInput(String message) {
        if (!message.isBlank()) {
            System.out.println(message);
        }
        return sc.nextLine();
    }

    public static void main(String[] args) {
        new Shopkeeper().start();
    }

}