package com.example.myweather.data;

import com.google.gson.annotations.SerializedName;

public class Coord {
    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }

    @SerializedName("lon")
    private float lon;

    @SerializedName("lat")
    private float lat;
}
