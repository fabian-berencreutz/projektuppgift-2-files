package se.iths.fabian.webshop;

public class Electronics extends Product {

    public Electronics(String articleNumber, String title, double price, String description) {
        super(articleNumber, title, price, description);
    }

    @Override
    public String category() {
        return "Electronics";
    }
}
