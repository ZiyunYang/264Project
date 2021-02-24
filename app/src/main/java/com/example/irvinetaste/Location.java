package com.example.irvinetaste;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {
    @SerializedName("display_address")
    private List<String> address;
}
