package se.iths.fabian.webshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class ProductManager {
    private static final String ADD_PRODUCT = "1";
    private static final String LIST_PRODUCT = "2";
    private static final String SHOW_INFO = "3";
    private static final String EXIT = "4";
    private static final String PRODUCTS_FILE = "./products.json";

    private List<Product> productsList;
    private UI ui;
    private ObjectMapper objectMapper;

    public ProductManager(UI ui) {
        this.ui = ui;
        this.objectMapper = new ObjectMapper();
        productsList = new ArrayList<>();
        loadProductsFromFile();
    }

    private void addProduct(Product product) {
        productsList.add(product);
    }

    public void addElectronic(String articleNumber, String title, double price, String description) {
        Product product = new Electronics(articleNumber, title, price, description);
        addProduct(product);
    }

    public void addFurniture(String articleNumber, String title, double price, String description) {
        Product product = new Furniture(articleNumber, title, price, description);
        addProduct(product);
    }

    public void addClothing(String articleNumber, String title, double price, String description) {
        Product product = new Clothing(articleNumber, title, price, description);
        addProduct(product);
    }

    public void menuService() {
        boolean running = true;
        while (running) {
            String choice = ui.menu();
            try {
                switch (choice) {
                    case ADD_PRODUCT -> addProductMenu(this);
                    case LIST_PRODUCT -> this.listProducts();
                    case SHOW_INFO -> showInfo(this);
                    case EXIT -> {
                        ui.info("Exiting application...");
                        saveProductsToFile();
                        running = false;
                    }
                    default -> ui.info("Invalid input, enter a number between 1-4");
                }
            } catch (InputMismatchException e) {
                ui.info("Invalid input, enter a valid number.");
            }
        }
    }

    public void addProductMenu(ProductManager productManager) {
        String choice = ui.prompt("""
                ---Choose Category---
                1. Electronics
                2. Furniture
                3. Clothing""");

        try {
            String articleNumber = ui.prompt("Enter Article Number: ");
            if (!isValidInput(articleNumber, "Article Number")) {
                return;
            }
            if (articleNumberExists(articleNumber)) {
                ui.info("Article number " + articleNumber + " already exists.");
                return;
            }
            String title = ui.prompt("Enter Title: ");
            if (!isValidInput(title, "Title")) {
                return;
            }

            String priceInput = ui.prompt("Enter Price: ");
            double price;
            try {
                price = Double.parseDouble(priceInput);
                if (!isValidPrice(price)) {
                    return;
                }
            } catch (NumberFormatException e) {
                ui.info("Invalid input format. Please enter a valid number");
                return;
            }

            String description = ui.prompt("Enter Description: ");
            if (!isValidInput(description, "Description")) {
                return;
            }

            addProductByCategory(choice, articleNumber, title, price, description);

        } catch (Exception e) {
            ui.info("An error occurred while adding the product: " + e.getMessage());
        }
    }

    private void addProductByCategory(String choice, String articleNumber, String title,
                                      double price, String description) {
        ProductCategory category = ProductCategory.fromMenuChoice(choice);

        if (category == null) {
            ui.info("Invalid category choice, enter a number between 1-3");
            return;
        }

        Product product = createProductByCategory(category, articleNumber, title, price, description);

        if (product != null) {
            addProduct(product);
            ui.info("Product {" + title + "} successfully registered in the " +
                    category.getDisplayName().toLowerCase() + " category.");
        }
    }

    private Product createProductByCategory(ProductCategory category, String articleNumber, String title,
                                            double price, String description) {
        return switch (category) {
            case ELECTRONICS -> new Electronics(articleNumber, title, price, description);
            case FURNITURE -> new Furniture(articleNumber, title, price, description);
            case CLOTHING -> new Clothing(articleNumber, title, price, description);
        };
    }
    
    public void showInfo(ProductManager productManager) {
        String articleNumber = ui.prompt("Type in article number for the product: ");
        if (isValidInput(articleNumber, "Article Number")) {
            productInfo(articleNumber);
        }
    }

    public boolean isValidInput(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            ui.info(fieldName + " cannot be empty");
            return false;
        }

        return true;
    }

    public boolean isValidPrice(double price) {
        if (price < 0) {
            ui.info("Price cannot be negative");
            return false;
        }

        return true;
    }

    public void listProducts() {
        if (productsList.isEmpty()) {
            ui.info("No products available");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("---All products---\n");

        for (Product p : productsList) {
            sb.append("• ").append(p.getTitle())
                    .append(" (").append(p.category()).append(")")
                    .append(System.lineSeparator());
        }

        ui.info(sb.toString());
    }

    public void productInfo(String articleNumber) {
        productsList.stream()
                .filter(p -> p.getArticleNumber().equals(articleNumber))
                .findFirst()
                .ifPresentOrElse(
                        product -> ui.info(String.format("""
                                        ---Product information---
                                        Article Number: %s
                                        Title: %s
                                        Price: %.2f kr
                                        Description: %s
                                        Category: %s
                                        """,
                                product.getArticleNumber(),
                                product.getTitle(),
                                product.getPrice(),
                                product.getDescription(),
                                product.category())),
                        () -> ui.info("Article was not found.")
                );
    }

    public boolean articleNumberExists(String articleNumber) {
        return productsList.stream()
                .anyMatch(p -> p.getArticleNumber().equals(articleNumber));
    }

    private void loadProductsFromFile() {
        File file = new File(PRODUCTS_FILE);
        System.out.println("---Loading---");
        System.out.println("Absolute path: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());

        if (!file.exists()) {
            ui.info("No existing product file found. Starting with empty product list.");
            return;
        }

        try {
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Product.class);
            List<Product> loadedProducts = objectMapper.readValue(file, listType);
            productsList.addAll(loadedProducts);
            ui.info("Successfully loaded " + loadedProducts.size() + " products from file.");
        } catch (IOException e) {
            ui.info("Error loading products from file: " + e.getMessage());
            ui.info("Starting with empty list.");
        }
    }

    private void saveProductsToFile() {
        File file = new File(PRODUCTS_FILE);
        System.out.println("---Saving---");
        System.out.println("Absolut path: " + file.getAbsolutePath());
        System.out.println("Products to save: " + productsList.size());

        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(PRODUCTS_FILE), productsList);
            ui.info("Successfully saved " + productsList.size() + " products to file.");
        } catch (IOException e) {
            ui.info("Error saving products to file: " + e.getMessage());
        }
    }
}
