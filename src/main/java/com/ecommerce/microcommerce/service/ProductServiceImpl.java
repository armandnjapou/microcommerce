package com.ecommerce.microcommerce.service;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public Product getProduct(int id) {
        return productDao.findById(id);
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
        return productDao.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productDao.deleteById(id);
    }

    @Override
    public int calculateProductMargin(Product product) {
        return product.getPrice() - product.getBuyPrice();
    }
}
