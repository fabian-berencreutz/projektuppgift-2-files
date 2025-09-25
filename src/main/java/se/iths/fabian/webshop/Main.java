package se.iths.fabian.webshop;

public class Main {
    public static void main(String[] args) {
        UI consoleUi = new ConsoleUi();
        UI jOptionMenu = new JOptionMenu();

        ProductManager productManager = new ProductManager(jOptionMenu);

        productManager.menuService();
    }
}