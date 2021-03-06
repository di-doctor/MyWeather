package com.example.myweather;

import androidx.annotation.Nullable;

public class WeatherObject {
    private final String city;
    private final Float temperature;

    @Nullable
    private Integer id;

    public WeatherObject(String city,Float temperature){
        this.city = city;
        this.temperature = temperature;
    }
    public WeatherObject(String city,Float temperature, int id){
        this.city = city;
        this.temperature = temperature;
        this.id = id;
    }

    public static WeatherObject convertFromResponse(WeatherResponse weatherResponse) {
        return new WeatherObject(weatherResponse.getName(),weatherResponse.getMain().getTemp());
    }

    public String getCity() {
        return city;
    }

    public Float getTemperature() {
        return temperature;
    }
}
