package com.example.irvinetaste;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Restaurant {
    @SerializedName("name")
    private String name;
    @SerializedName("img_url")
    private String imgUrl;
    @SerializedName("is_claimed")
    private Boolean isClaimed;
    @SerializedName("is_closed")
    private Boolean isClosed;
    @SerializedName("display_phone")
    private String display_phone;
    @SerializedName("review_count")
    private Integer reviewCount;
    @SerializedName("rating")
    private Float rating;
    @SerializedName("price")
    private String price;
    @SerializedName("location")
    private Location location;
    @SerializedName("photos")
    private List<String> photos;



}
