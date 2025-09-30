package se.iths.fabian.webshop.service;

import se.iths.fabian.webshop.model.*;
import se.iths.fabian.webshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void addProduct(ProductCategory category, String articleNumber, String title,
                           double price, String description) {
        Product product = createProduct(category, articleNumber, title, price, description);
        repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductByArticleNumber(String articleNumber) {
        return repository.findByArticleNumber(articleNumber);
    }

    public boolean articleNumberExists(String articleNumber) {
        return repository.existsByArticleNumber(articleNumber);
    }

    public boolean isValidArticleNumber(String articleNumber) {
        return articleNumber != null && !articleNumber.trim().isEmpty();
    }

    public boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    public boolean isValidPrice(double price) {
        return price >= 0;
    }

    public boolean isValidDescription(String description) {
        return description != null && !description.trim().isEmpty();
    }

    public void saveToFile() {
        repository.saveToFile();
    }

    private Product createProduct(ProductCategory category, String articleNumber,
                                  String title, double price, String description) {
        return switch (category) {
            case ELECTRONICS -> new Electronics(articleNumber, title, price, description);
            case FURNITURE -> new Furniture(articleNumber, title, price, description);
            case CLOTHING -> new Clothing(articleNumber, title, price, description);
        };
    }
}