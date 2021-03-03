package com.example.irvinetaste;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class Restaurant {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("image_url")
    private String imgUrl;
    @SerializedName("is_claimed")
    private Boolean isClaimed;
    @SerializedName("is_closed")
    private Boolean isClosed;
    @SerializedName("display_phone")
    private String displayPhone;
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
    @SerializedName("categories")
    private List<HashMap<String, String>> categories;
    @SerializedName("transactions")
    private List<String> transactions;
    @SerializedName("distance")
    private Double distance;

    public Restaurant(String id,String name, String url, boolean isClaimed,boolean isClosed, String phone,
                      int reviewCount, Float rating, String price, Location location, List<String> photos){
        this.id=id;
        this.name=name;
        this.imgUrl=url;
        this.isClaimed=isClaimed;
        this.isClosed=isClosed;
        this.displayPhone = phone;
        this.reviewCount=reviewCount;
        this.rating=rating;
        this.price=price;
        this.location=location;
        this.photos=photos;
    }

    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public Float getRating(){
        return rating;
    }
    public String getImgUrl(){
        return imgUrl;
    }

    public String getId() {
        return id;
    }

    public List<HashMap<String, String>> getCategories(){
        return categories;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public Double getDistance() {
        return distance;
    }

    public Location getLocation() {
        return location;
    }

    public String getDisplayPhone() { return displayPhone; }
}
