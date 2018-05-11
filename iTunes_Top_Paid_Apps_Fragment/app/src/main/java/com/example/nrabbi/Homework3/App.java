// Nazmul Rabbi & Dyrell Cole
// App.java
// ITCS 4180 : Homework 3
// Group 20

package com.example.nrabbi.Homework3;

import java.io.Serializable;

public class App implements Serializable{
    String imageUrl, name, price;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "App{" +
                "imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
