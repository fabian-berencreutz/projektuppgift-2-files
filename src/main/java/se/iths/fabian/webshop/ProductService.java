//package se.iths.fabian.webshop;
//
//import java.util.InputMismatchException;
//
//public class ProductService {
//
//    private UI ui;
//
//    public ProductService(UI ui) {
//        this.ui = ui;
//    }
//
//    public void menuService() {
//        ProductManager productManager = new ProductManager();
//
//        boolean running = true;
//
//        while (running) {
//            String choice = ui.menu();
//            try {
//                switch (choice) {
//                    case "1" -> addProduct(productManager);
//                    case "2" -> productManager.listProducts();
//                    case "3" -> showInfo(productManager);
//                    case "4" -> {
//                        ui.info("Exiting application...");
//                        running = false;
//                    }
//                    default -> ui.info("Invalid input, enter a number between 1-4");
//                }
//            } catch (InputMismatchException e) {
//                ui.info("Invalid input, enter a valid number.");
//            }
//        }
//    }
//
//    public void addProduct(ProductManager productManager) {
//        String choice = ui.prompt("""
//                ---Choose Category---
//                1. Electronics
//                2. Furniture
//                3. Clothing""");
//
//        try {
//
//            String articleNumber = ui.prompt("Enter Article Number: ");
//            if (articleNumber.trim().isEmpty()) {
//                ui.info("Article number cannot be empty.");
//                return;
//            }
//            if (productManager.articleNumberExists(articleNumber)) {
//                ui.info("Article number " + articleNumber + " already exists.");
//                return;
//            }
//            String title = ui.prompt("Enter Title: ");
//            if (title.trim().isEmpty()) {
//                ui.info("Title cannot be empty.");
//                return;
//            }
//            double price = Double.parseDouble(ui.prompt("Enter Price: "));
//            if (price < 0) {
//                ui.info("Price cannot be negative.");
//                return;
//            }
//            String description = ui.prompt("Enter Description: ");
//            if (description.trim().isEmpty()) {
//                ui.info("Description cannot be empty.");
//                return;
//            }
//
//            switch (choice) {
//                case "1" -> {
//                    productManager.addElectronic(articleNumber, title, price, description);
//                    ui.info("Product {" + title + "} successfully registered" +
//                            " in the electronics category.");
//                }
//
//                case "2" -> {
//                    productManager.addFurniture(articleNumber, title, price, description);
//                    ui.info("Product {" + title + "} successfully registered" +
//                            " in the furniture category.");
//                }
//
//                case "3" -> {
//                    productManager.addClothing(articleNumber, title, price, description);
//                    ui.info("Product {" + title + "} successfully registered" +
//                            " in the clothing category.");
//                }
//                default -> ui.info("Invalid category choice, enter a number between 1-3.");
//            }
//        } catch (InputMismatchException e) {
//            ui.info("Invalid input, enter valid number for category and price.");
//        }
//    }
//
//    public void showInfo(ProductManager productManager) {
//        String articleNumber = ui.prompt("Type in article number for the product: ");
//        productManager.productInfo(articleNumber);
//    }
//}
