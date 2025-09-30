package se.iths.fabian.webshop.model;

public class Clothing extends Product {
    
    public Clothing(String articleNumber, String title, double price, String description) {
        super(articleNumber, title, price, description);
    }

    public Clothing() {
        super();
    }

    @Override
    public String category() {
        return "Clothing";
    }
}
