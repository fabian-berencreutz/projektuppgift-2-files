package se.iths.fabian.webshop;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private List<Product> productsList;

    public ProductManager() {
        productsList = new ArrayList<>();
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

    public void listProducts() {
        System.out.println("---All products---");
        for (Product p : productsList) {
            System.out.println("{ " + p.getTitle() + " }");
        }
    }

    public void productInfo(String articleNumber) {
        boolean found = false;

        for (Product p : productsList) {
            if (p.getArticleNumber().equals(articleNumber)) {
                System.out.println("---Product information---");
                System.out.println("Article Number: " + p.getArticleNumber());
                System.out.println("Title: " + p.getTitle());
                System.out.println("Price: " + p.getPrice());
                System.out.println("Description: " + p.getDescription());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Article was not found.");
        }
    }

    public boolean articleNumberExists(String articleNumber) {
        for (Product p : productsList) {
            if (p.getArticleNumber().equals(articleNumber)) {
                return true;
            }
        }

        return false;
    }
}
