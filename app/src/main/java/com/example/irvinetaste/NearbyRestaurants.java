package com.example.irvinetaste;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearbyRestaurants {
    @SerializedName("businesses")
    private List<Restaurant> restaurants;
    public NearbyRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<Restaurant>  getNearbyRestaurants() {
        return this.restaurants;
    }

    public Restaurant get(int idx) { return restaurants.get(idx); }

    public int size() { return restaurants.size(); }
}
