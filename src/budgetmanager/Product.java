package budgetmanager;

public record Product(String name, double price) {

    @Override
    public String toString() {
        return String.format("%s $%.2f", name, price);
    }
}
