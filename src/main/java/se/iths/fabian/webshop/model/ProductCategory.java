package se.iths.fabian.webshop.model;

public enum ProductCategory {
    ELECTRONICS("Electronics", "1"),
    FURNITURE("Furniture", "2"),
    CLOTHING("Clothing", "3");

    private final String displayName;
    private final String menuChoice;

    ProductCategory(String displayName, String menuChoice) {
        this.displayName = displayName;
        this.menuChoice = menuChoice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMenuChoice() {
        return menuChoice;
    }

    public static ProductCategory fromMenuChoice(String choice) {
        for (ProductCategory category : values()) {
            if (category.menuChoice.equals(choice)) {
                return category;
            }
        }

        return null;
    }
}
