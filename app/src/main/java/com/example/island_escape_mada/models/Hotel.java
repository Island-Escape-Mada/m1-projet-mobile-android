package com.example.island_escape_mada.models;

import java.util.List;

public class Hotel {
    private String name;
    private String location;
    private double rating;
    private String price;
    private List<String> amenities;
    private String image;

    public Hotel() {
    }

    public Hotel(String name, String location, double rating, String price, List<String> amenities, String image) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.price = price;
        this.amenities = amenities;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public String getImage() {
        return image;
    }
}

