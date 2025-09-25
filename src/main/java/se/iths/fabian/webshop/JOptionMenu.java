package se.iths.fabian.webshop;

import javax.swing.*;

public class JOptionMenu implements UI {
    @Override
    public String prompt(String message) {
        return JOptionPane.showInputDialog(message);
    }

    @Override
    public void info(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public String menu() {
        return JOptionPane.showInputDialog(null, """
                Make your choice:
                1. Add Product
                2. List All Products
                3. Show Info About Product
                4. Exit Application""");
    }
}
