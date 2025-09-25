package se.iths.fabian.webshop;

public class Main {
    public static void main(String[] args) {
        UI consoleUi = new ConsoleUi();

        ProductService productService = new ProductService(consoleUi);

        productService.menuService();
    }
}