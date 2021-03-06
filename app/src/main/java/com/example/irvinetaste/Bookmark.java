package com.example.irvinetaste;

public class Bookmark {

    String name;
    String imageUrl;
    String restaurantId;

    public Bookmark(String id, String n, String url) {
        restaurantId = id;
        imageUrl = url;
        name = n;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() { return imageUrl; }

    public String getRestaurantId() { return restaurantId; }

}
