package com.example.irvinetaste;

import com.google.gson.annotations.SerializedName;

public class Coordinates {
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longtitude")
    private double longtitude;

    public Coordinates(double la, double lo) {
        this.latitude = la;
        this.longtitude = lo;
    }

    public double getLatitude() { return this.latitude; }

    public double getLongtitude() { return this.longtitude; }

}

