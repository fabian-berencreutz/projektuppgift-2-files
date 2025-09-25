package se.iths.fabian.webshop;

import java.util.Scanner;

public class ConsoleUi implements UI {

    Scanner scanner = new Scanner(System.in);

    @Override
    public String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    @Override
    public void info(String message) {
        System.out.println(message);
    }

    @Override
    public String menu() {
        System.out.println("\n---MENU---");
        System.out.println("""
                Make your choice:
                1. Add Product
                2. List All Products
                3. Show Info About Product
                4. Exit Application""");

        return scanner.nextLine();
    }
}
