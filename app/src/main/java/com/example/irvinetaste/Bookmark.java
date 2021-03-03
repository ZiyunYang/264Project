package com.example.irvinetaste;

public class Bookmark {

    String name;
    String imageUrl;
    int restaurantId;

    public Bookmark(int id, String n, String url) {
        restaurantId = id;
        imageUrl = url;
        name = n;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() { return imageUrl; }

    public int getRestaurantId() { return restaurantId; }

}
