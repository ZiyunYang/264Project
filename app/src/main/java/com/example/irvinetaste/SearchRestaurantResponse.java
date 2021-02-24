package com.example.irvinetaste;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchRestaurantResponse {

    @SerializedName("businesses")
    private List<Restaurant> restaurantsList;

    public SearchRestaurantResponse(List<Restaurant> results) {
        this.restaurantsList = results;
    }

    public List<Restaurant> getRestaurantList(){
        return this.restaurantsList;
    }
}
