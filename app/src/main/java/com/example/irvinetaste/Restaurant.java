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

    public Restaurant(String name, String url, boolean isClaimed,boolean isClosed, String phone,
                      int reviewCount, Float rating, String price, Location location, List<String> photos){
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
}
