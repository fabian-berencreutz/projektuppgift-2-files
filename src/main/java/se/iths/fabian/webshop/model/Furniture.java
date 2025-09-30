package se.iths.fabian.webshop.model;

public class Furniture extends Product {

    public Furniture(String articleNumber, String title, double price, String description) {
        super(articleNumber, title, price, description);
    }

    public Furniture() {
        super();
    }

    @Override
    public String category() {
        return "Furniture";
    }
}
