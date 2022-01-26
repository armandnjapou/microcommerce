package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.exceptions.ProductNotFoundException;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @GetMapping("/products")
    public MappingJacksonValue listProducts() {
        List<Product> products = productDao.findAll();
        SimpleBeanPropertyFilter buyPriceFilter = SimpleBeanPropertyFilter.serializeAllExcept("buyPrice");
        FilterProvider filterList = new SimpleFilterProvider().addFilter("buyPriceFilter", buyPriceFilter);
        MappingJacksonValue filteredProducts = new MappingJacksonValue(products);
        filteredProducts.setFilters(filterList);
        return filteredProducts;
    }

    @GetMapping("products/{id}")
    public Product displayProduct(@PathVariable int id) throws ProductNotFoundException {
        Product product = productDao.findById(id);
        if(Objects.isNull(product)) throw new ProductNotFoundException("Product with id : " + id + " not found !");
        else return product;
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

    @PutMapping("products")
    public void updateProduct(@RequestBody Product product) {
        productDao.save(product);
    }

    @DeleteMapping("products/{id}")
    public void deleteProduct(@PathVariable int id) {
        productDao.deleteById(id);
    }
}
