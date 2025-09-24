package se.iths.fabian.webshop;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuBar {

    public MenuBar() {
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        ProductManager productManager = new ProductManager();

        boolean running = true;

        while (running) {
            System.out.println("\n---MENU---");
            System.out.println("""
                    Make your choice:
                    1. Add Product
                    2. List All Products
                    3. Show Info About Product
                    4. Exit Application""");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addProduct(scanner, productManager);
                    case 2 -> productManager.listProducts();
                    case 3 -> showInfo(scanner, productManager);
                    case 4 -> {
                        System.out.println("Exiting application...");
                        running = false;
                    }
                    default -> System.out.println("Invalid input, enter a number between 1-4");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, enter a valid number.");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    public void addProduct(Scanner scanner, ProductManager productManager) {
        System.out.println("""
                ---Choose Category---
                1. Electronics
                2. Furniture
                3. Clothing""");

        try {
            int categoryChoice = scanner.nextInt();
            scanner.nextLine();

            if (categoryChoice < 1 || categoryChoice > 3) {
                System.out.println("Invalid category choice, enter a number between 1-3.");
                return;
            }

            System.out.println("Enter Article Number: ");
            String articleNumber = scanner.nextLine();
            if (articleNumber.trim().isEmpty()) {
                System.out.println("Article number cannot be empty.");
                return;
            }
            if (productManager.articleNumberExists(articleNumber)) {
                System.out.println("Article number " + articleNumber + " already exists.");
                return;
            }
            System.out.println("Enter Title: ");
            String title = scanner.nextLine();
            if (title.trim().isEmpty()) {
                System.out.println("Title cannot be empty.");
                return;
            }
            System.out.println("Enter Price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            if (price < 0) {
                System.out.println("Price cannot be negative.");
                return;
            }
            System.out.println("Enter Description: ");
            String description = scanner.nextLine();
            if (description.trim().isEmpty()) {
                System.out.println("Description cannot be empty.");
                return;
            }

            switch (categoryChoice) {
                case 1 -> {
                    productManager.addElectronic(articleNumber, title, price, description);
                    System.out.println("Product {" + title + "} successfully registered" +
                            " in the electronics category.");
                }

                case 2 -> {
                    productManager.addFurniture(articleNumber, title, price, description);
                    System.out.println("Product {" + title + "} successfully registered" +
                            " in the furniture category.");
                }

                case 3 -> {
                    productManager.addClothing(articleNumber, title, price, description);
                    System.out.println("Product {" + title + "} successfully registered" +
                            " in the clothing category.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input, enter valid number for category and price.");
            scanner.nextLine();
        }
    }

    public void showInfo(Scanner scanner, ProductManager productManager) {
        System.out.println("Type in article number for the product: ");
        String articleNumber = scanner.nextLine();
        productManager.productInfo(articleNumber);
    }
}
