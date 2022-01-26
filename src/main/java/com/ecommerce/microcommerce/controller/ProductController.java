package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.exceptions.ProductNotFoundException;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.service.ProductService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@Api("API for CRUD operations on products")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("Get all available products")
    @GetMapping("/products")
    public MappingJacksonValue listProducts() {
        List<Product> products = productService.getAllProducts();
        productService.sortProductsByName(products);
        SimpleBeanPropertyFilter buyPriceFilter = SimpleBeanPropertyFilter.serializeAllExcept("buyPrice");
        FilterProvider filterList = new SimpleFilterProvider().addFilter("buyPriceFilter", buyPriceFilter);
        MappingJacksonValue filteredProducts = new MappingJacksonValue(products);
        filteredProducts.setFilters(filterList);
        return filteredProducts;
    }

    @ApiOperation("Get a specific product using id")
    @GetMapping("products/{id}")
    public Product displayProduct(@PathVariable int id) throws ProductNotFoundException {
        Product product = productService.getProduct(id);
        if(Objects.isNull(product)) throw new ProductNotFoundException("Product with id : " + id + " not found !");
        else return product;
    }

    @ApiOperation("Add a new product to stock")
    @PostMapping("products")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        Product addedProduct = productService.saveOrUpdateProduct(product);
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

    @ApiOperation("Update a specific product")
    @PutMapping("products")
    public void updateProduct(@RequestBody Product product) {
        productService.saveOrUpdateProduct(product);
    }

    @ApiOperation("Delete a specific product using id")
    @DeleteMapping("products/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    @ApiOperation("Get margin on product")
    @GetMapping("products/{id}/margin")
    public String getProductMargin(@PathVariable int id) {
        Product product = productService.getProduct(id);
        if(Objects.isNull(product)) throw new ProductNotFoundException("Product with id : " + id + " not found !");
        else {
            return product + " : " + productService.calculateProductMargin(product);
        }
    }
}
