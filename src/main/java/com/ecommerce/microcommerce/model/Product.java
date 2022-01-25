package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"buyPrice"})
public class Product {
    private int id;
    private String name;
    private int price;
    private int buyPrice;

    public Product(int id, String name, int price, int buyPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.buyPrice = buyPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", buyPrice=" + buyPrice +
                '}';
    }
}
