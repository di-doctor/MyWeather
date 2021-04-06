package com.example.myweather.data;

import com.example.myweather.Main;
import com.example.myweather.data.Coord;
import com.example.myweather.presentation.WeatherFromApi;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("main")
    private Main main;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("weather")
    private List<WeatherFromApi> weather;

    @SerializedName("coord")
    private Coord coord;



    // get and set

    public Coord getCoord() {
        return coord;
    }
    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public List<WeatherFromApi> getWeather() {
        return weather;
    }

    public int getId() {
        return id;
    }
}
