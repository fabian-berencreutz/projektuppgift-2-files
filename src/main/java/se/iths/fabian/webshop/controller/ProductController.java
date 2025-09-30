package se.iths.fabian.webshop.controller;

import se.iths.fabian.webshop.model.Product;
import se.iths.fabian.webshop.model.ProductCategory;
import se.iths.fabian.webshop.service.ProductService;
import se.iths.fabian.webshop.ui.UI;

import java.util.List;

public class ProductController {
    private static final String ADD_PRODUCT = "1";
    private static final String LIST_PRODUCTS = "2";
    private static final String SHOW_INFO = "3";
    private static final String EXIT = "4";

    private final UI ui;
    private final ProductService service;

    public ProductController(UI ui, ProductService service) {
        this.ui = ui;
        this.service = service;
    }

    public void start() {
        boolean running = true;

        while (running) {
            String choice = ui.menu();

            switch (choice) {
                case ADD_PRODUCT -> handleAddProduct();
                case LIST_PRODUCTS -> handleListProducts();
                case SHOW_INFO -> handleShowProductInfo();
                case EXIT -> {
                    ui.info("Saving products and exiting application...");
                    service.saveToFile();
                    running = false;
                }
                default -> ui.info("Invalid input, enter a number between 1-4");
            }
        }
    }

    private void handleAddProduct() {
        String categoryChoice = ui.prompt("""
                ---Choose Category---
                1. Electronics
                2. Furniture
                3. Clothing""");

        ProductCategory category = ProductCategory.fromMenuChoice(categoryChoice);

        if (category == null) {
            ui.info("Invalid category choice, enter a number between 1-3.");
            return;
        }

        try {
            String articleNumber = ui.prompt("Enter Article Number: ");
            if (!service.isValidArticleNumber(articleNumber)) {
                ui.info("Article number cannot be empty.");
                return;
            }

            if (service.articleNumberExists(articleNumber)) {
                ui.info("Article number " + articleNumber + " already exists.");
                return;
            }

            String title = ui.prompt("Enter Title: ");
            if (!service.isValidTitle(title)) {
                ui.info("Title cannot be empty.");
                return;
            }

            String priceInput = ui.prompt("Enter Price: ");
            double price;
            try {
                price = Double.parseDouble(priceInput);
                if (!service.isValidPrice(price)) {
                    ui.info("Price cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                ui.info("Invalid price format. Please enter a valid number.");
                return;
            }

            String description = ui.prompt("Enter Description: ");
            if (!service.isValidDescription(description)) {
                ui.info("Description cannot be empty.");
                return;
            }

            service.addProduct(category, articleNumber, title, price, description);
            ui.info("Product {" + title + "} successfully registered in the " +
                    category.getDisplayName().toLowerCase() + " category.");

        } catch (Exception e) {
            ui.info("An error occurred while adding the product: " + e.getMessage());
        }
    }

    private void handleListProducts() {
        List<Product> products = service.getAllProducts();

        if (products.isEmpty()) {
            ui.info("No products available.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("---All products---\n");

        for (Product p : products) {
            sb.append("• ").append(p.getTitle())
                    .append(" (").append(p.category()).append(")")
                    .append(System.lineSeparator());
        }

        ui.info(sb.toString());
    }

    private void handleShowProductInfo() {
        String articleNumber = ui.prompt("Type in article number for the product: ");

        if (!service.isValidArticleNumber(articleNumber)) {
            ui.info("Article number cannot be empty.");
            return;
        }

        service.getProductByArticleNumber(articleNumber)
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
}
