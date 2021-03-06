package com.example.myweather;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {
    @SerializedName("main")
    private Main main;
    @SerializedName("name")
    private String name;


    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }
}
