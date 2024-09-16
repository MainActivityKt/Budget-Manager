package budgetmanager;

import java.util.Arrays;
import java.util.*;

public class MoneyCounter {
    double totalMoney = 0.0;
    List<String> itemsList = new ArrayList<>();


    void startCounting() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            itemsList.add(line);

            String price = Arrays.stream(line.split(" ")).filter(it -> it.contains("$")).findAny().get();
            totalMoney += Double.parseDouble(price.substring(1));
        }
        System.out.println();
        itemsList.forEach(System.out::println);
        System.out.printf("Total: $%.2f", totalMoney);
    }

    public static void main(String[] args) {
        new MoneyCounter().startCounting();
    }
}
