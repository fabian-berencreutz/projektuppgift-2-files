package se.iths.fabian.webshop;

public class Electronics extends Product {

    public Electronics(String articleNumber, String title, double price, String description) {
        super(articleNumber, title, price, description);
    }

    public Electronics() {
        super();
    }

    @Override
    public String category() {
        return "Electronics";
    }
}
