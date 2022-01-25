package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/products")
    public List<Product> listProducts() {
        return productDao.findAll();
    }

    @GetMapping("products/{id}")
    public Product displayProduct(@PathVariable int id) {
        return productDao.findById(id);
    }

    @PostMapping("products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = productDao.save(product);
        if(Objects.isNull(addedProduct)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedProduct.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
