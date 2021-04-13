package com.example.myweather.data;

import com.example.myweather.Main;
import com.example.myweather.presentation.WeatherFromApi;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastWeatherFromApi {
    @SerializedName("dt")
    private int dt;

    @SerializedName("temp")
    private Temp temp;

    @SerializedName("weather")
    private List<WeatherFromApi> weatherList;


//*******************************_GET_**************
    public Temp getTemp() {
        return temp;    }

    public List<WeatherFromApi> getWeatherList() {
        return weatherList;
    }
    public int getDt() {
        return dt;
    }

}
