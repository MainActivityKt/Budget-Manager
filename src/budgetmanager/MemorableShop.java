package budgetmanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class MemorableShop {
    private final Scanner sc = new Scanner(System.in);
    private final StringBuilder menu = new StringBuilder();
    private final String FILE_PATH = "src/budgetmanager/purchases.txt";
    private Boolean isRunning = true;
    private boolean purchaseListIsEmpty = true;

    HashMap<Category, ArrayList<Product>> purchases = new HashMap<>();
    double balance = 0.0;
    double totalIncome = 0.0;

    private void createMenu() {
        menu.append("Choose your action:" + "\n");
        menu.append("1) Add income" + "\n");
        menu.append("2) Add purchase" + "\n");
        menu.append("3) Show list of purchases" + "\n");
        menu.append("4) Balance" + "\n");
        menu.append("5) Save" + "\n");
        menu.append("6) Load" + "\n");
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
                case 5 -> savePurchases();
                case 6 -> loadPurchases();
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
            System.out.println();
            String itemName = getStringInput("Enter purchase name:");
            double itemPrice = getDoubleInput("Enter its price:");

            purchases.get(Category.values()[n - 1]).add(new Product(itemName, itemPrice));

            totalIncome += itemPrice;
            balance -= itemPrice;
            if (purchaseListIsEmpty) {
                purchaseListIsEmpty = false;
            }
            System.out.println("Purchase was added!\n");
            printCategories(false, "Choose the type of purchase");
            n = getIntInput("");
        }
    }

    private void showPurchasesList() {
        System.out.println();
        if (purchaseListIsEmpty) {
            System.out.println("The purchase list is empty!");
        } else  {
            printCategories(true, "Choose the type of purchases");
            int n = getIntInput("");
            while (n != 6) {
                System.out.println();
                switch (n) {
                    case 1, 2, 3, 4 -> {
                        Category category = Category.values()[n - 1];
                        printPurchaseList(category);
                    }
                    case 5 -> printPurchaseList(null);
                }
                System.out.println();
                printCategories(true, "Choose the type of purchases");
                n = getIntInput("");
            }
        }
    }

    private void printPurchaseList(Category category) {
        if (category != null) {
            System.out.println(category.getCategoryName() + ":");

            List<Product> items = purchases.get(category);
            if (items.isEmpty()) {
                System.out.println("The purchase list is empty!");
            } else {
                double sum = 0.0;
                for (Product item : items) {
                    System.out.println(item);
                    sum += item.price();
                }

                System.out.printf("Total sum: $%.2f\n\n", sum);
            }
        } else {
            if (purchases.isEmpty()) {
                System.out.println("The purchase list is empty!");
            } else {
                System.out.println("All:");
                purchases.values().forEach(items -> items.forEach(System.out::println));
                System.out.printf("Total sum: $%.2f\n", totalIncome);
            }
        }
    }

    private void showBalance() {
        System.out.println();
        System.out.printf("Balance: $%.2f\n", balance);
    }

    private void savePurchases() {
        System.out.println();
        File file = new File(FILE_PATH);
        try (FileWriter writer = new FileWriter(file)) {
            writer.append(String.valueOf(balance)).append("\n");
            for (Category category : Category.values()) {
                writer.append("Category ").append(category.name()).append("\n");
                List<Product> items = purchases.get(category);

                if (!items.isEmpty()) {
                    for (Product product : items) {
                        writer.append(product.name()).append("=");
                        writer.append(String.valueOf(product.price())).append("\n");
                    }
                }
                writer.append("\n");
                System.out.println("Purchases were saved!");
            }

        } catch (IOException e) {
            System.out.println("Unable to create file");
        }

    }

    private void loadPurchases() {
        System.out.println();
        try(Scanner file = new Scanner(new File(FILE_PATH))) {
            ArrayList<Product> items = new ArrayList<>();
            Category currentCategory = Category.FOOD;
            balance += Double.parseDouble(file.nextLine());
            file.nextLine();  // skips the first category line

            while (file.hasNextLine()) {
                String line = file.nextLine();
                if (line.startsWith("Category")) {
                    currentCategory = Category.valueOf(line.split(" ")[1]);
                } else if (line.isEmpty()) {
                    if (!items.isEmpty()) {
                        purchases.put(currentCategory, new ArrayList<>(items));
                        if (purchaseListIsEmpty) {
                            purchaseListIsEmpty = false;
                        }
                        items.clear();
                    }

                } else {
                    String name = line.split("=")[0];
                    double price = Double.parseDouble(line.split("=")[1]);
                    totalIncome += price;
                    items.add(new Product(name, price));
                }
                System.out.println("Purchases were loaded!");
            }
        } catch (IOException e) {
            System.out.println("Unable to load purchases file " + e.getMessage());
        }

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
        new MemorableShop().start();
    }
}