package com.ecommerce.microcommerce.service;

import com.ecommerce.microcommerce.repository.ProductRepository;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public int calculateProductMargin(Product product) {
        return product.getPrice() - product.getBuyPrice();
    }

    @Override
    public void sortProductsByName(List<Product> products) {
        products.sort(Comparator.comparing(Product::getName));
    }
}
