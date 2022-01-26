package com.ecommerce.microcommerce.repository;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int id);

    @Query("SELECT id, name, price FROM Product p WHERE p.price = :priceLimit")
    List<Product> findExpensiveProduct(@Param("priceLimit") int price);
}
