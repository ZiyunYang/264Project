package com.example.irvinetaste;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {
    @SerializedName("display_address")
    private List<String> address;
    public Location(List<String> add) { this.address = add; }
    public String getAddress() {
        String strAddress = "";
        for (String str : this.address) { strAddress += str; }
        return strAddress;
    }
}