package budgetmanager;

public enum Category {
    FOOD("Food"),
    CLOTHES("Clothes"),
    ENTERTAINMENT("Entertainment"),
    OTHER("Other");

    private final String categoryName;

    Category(String name) {
        this.categoryName = name;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
