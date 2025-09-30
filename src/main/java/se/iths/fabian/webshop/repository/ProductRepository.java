package se.iths.fabian.webshop.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.type.CollectionType;
import se.iths.fabian.webshop.model.Product;
import se.iths.fabian.webshop.ui.UI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private static final String PRODUCTS_FILE = "products.json";
    private final ObjectMapper objectMapper;
    private final List<Product> products;

    public ProductRepository() {
        this.objectMapper = new ObjectMapper();
        this.products = new ArrayList<>();
        loadProducts();
    }

    public void save(Product product) {
        products.add(product);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    public Optional<Product> findByArticleNumber(String articleNumber) {
        return products.stream()
                .filter(p -> p.getArticleNumber().equals(articleNumber))
                .findFirst();
    }

    public boolean existsByArticleNumber(String articleNumber) {
        return products.stream()
                .anyMatch(p -> p.getArticleNumber().equals(articleNumber));
    }

    public void saveToFile() {
        try {
            objectMapper.writerFor(new TypeReference<java.util.List<se.iths.fabian.webshop.model.Product>>() {
            })
                    .withDefaultPrettyPrinter()
                    .writeValue(new File(PRODUCTS_FILE), products);
            System.out.println("Successfully saved " + products.size() + " products to file.");
        } catch (IOException e) {
            System.err.println("Error saving products to file: " + e.getMessage());
        }
    }

    private void loadProducts() {
        File file = new File(PRODUCTS_FILE);

        if (!file.exists()) {
            System.out.println("No existing product file found. Starting with empty product list.");
            return;
        }

        try {
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Product.class);
            List<Product> loadedProducts = objectMapper.readValue(file, listType);
            products.addAll(loadedProducts);
            System.out.println("Successfully loaded " + loadedProducts.size() + " products from file.");
        } catch (IOException e) {
            System.err.println("Error loading products from file: " + e.getMessage());
            System.err.println("Starting with empty product list.");
        }
    }
}