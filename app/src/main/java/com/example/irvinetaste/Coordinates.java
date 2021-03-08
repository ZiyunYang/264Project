package com.example.irvinetaste;

import com.google.gson.annotations.SerializedName;

public class Coordinates {
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;

    public Coordinates(double la, double lo) {
        this.latitude = la;
        this.longitude = lo;
    }

    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

}

