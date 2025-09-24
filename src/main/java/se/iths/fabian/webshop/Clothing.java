package se.iths.fabian.webshop;

public class Clothing extends Product {
    
    public Clothing(String articleNumber, String title, double price, String description) {
        super(articleNumber, title, price, description);
    }

    @Override
    public String category() {
        return "Clothing";
    }
}
