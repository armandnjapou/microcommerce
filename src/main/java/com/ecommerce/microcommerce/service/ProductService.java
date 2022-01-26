package com.ecommerce.microcommerce.service;

import com.ecommerce.microcommerce.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProduct(int id);
    Product saveOrUpdateProduct(Product product);
    void deleteProduct(int id);
    int calculateProductMargin(Product product);
}
