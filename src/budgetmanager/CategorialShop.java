package budgetmanager;

import java.util.*;

public class CategorialShop {
    private final Scanner sc = new Scanner(System.in);
    private final StringBuilder menu = new StringBuilder();
    private Boolean isRunning = true;
    HashMap<Category, ArrayList<Product>> purchases = new HashMap<>();
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

    private void initializePurchaseList() {
        for (Category c : Category.values()) {
            purchases.put(c, new ArrayList<>());
        }
    }

    void start() {
        createMenu();
        initializePurchaseList();

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
        printCategories(false, "Choose the type of purchase");
        int n = getIntInput("");
        while (n != 5) {
            String itemName = getStringInput("Enter purchase name:");
            double itemPrice = getDoubleInput("Enter its price:");

            purchases.get(Category.values()[n - 1]).add(new Product(itemName, itemPrice));

            totalIncome += itemPrice;
            balance -= itemPrice;
            System.out.println("Purchase was added!\n");
            printCategories(false, "Choose the type of purchase");
            n = getIntInput("");
        }
    }

    private void showPurchasesList() {
        printCategories(true, "Choose the type of purchases");
        int n = getIntInput("");

        while (n != 6) {
            System.out.println();
            switch (n) {
                case 1, 2, 3, 4 -> {
                    Category category = Category.values()[n - 1];
                    System.out.println(category.getCategoryName() + ":");

                    ArrayList<Product> items = purchases.get(category);
                    if (items.isEmpty()) {
                        System.out.println("The purchase list is empty!");
                    } else {
                        items.forEach(System.out::println);
                        System.out.printf("Total sum: $%.2f\n\n", totalIncome);
                    }
                }
                case 5 -> {
                    if (purchases.isEmpty()) {
                        System.out.println("The purchase list is empty!");
                    } else {
                        System.out.println("All:");
                        purchases.values().forEach(items -> items.forEach(System.out::println));
                        System.out.printf("Total sum: $%.2f\n", totalIncome);
                    }
                }
            }
            System.out.println();
            printCategories(true, "Choose the type of purchases");
            n = getIntInput("");
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

    private int getIntInput(String message) {
        if (!message.isBlank()) {
            System.out.println(message);
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    private void printCategories(boolean showAllOption, String message) {
        if (!message.isBlank()) {
            System.out.println(message);
        }
        for (Category c : Category.values()) {
            System.out.printf("%d) %s\n", c.ordinal() + 1, c.getCategoryName());
        }
        if (showAllOption) {
            System.out.println("5) All");
            System.out.println("6) Back");
        } else {
            System.out.println("5) Back");
        }

    }

    public static void main(String[] args) {
        new CategorialShop().start();
    }
}

