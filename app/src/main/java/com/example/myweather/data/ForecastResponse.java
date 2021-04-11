package com.example.myweather.data;

import com.example.myweather.Main;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {
    @SerializedName("list")
    private List<ForecastWeatherFromApi> list;

    public List<ForecastWeatherFromApi> getList() {
        return list;
    }
}
