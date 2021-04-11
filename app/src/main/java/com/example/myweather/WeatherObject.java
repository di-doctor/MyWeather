package com.example.myweather;

import androidx.annotation.Nullable;

import com.example.myweather.data.WeatherResponse;

public class WeatherObject {
    private final String city;
    private final Float temperature;
    @Nullable
    private Integer id;
    @Nullable
    private String iconId;

    public WeatherObject(String city, Float temperature, int id, String iconId){
        this.city = city;
        this.temperature = temperature;
        this.id = id;
        this.iconId = iconId;
    }

    public static WeatherObject convertFromResponse(WeatherResponse weatherResponse) {
        String iconId  = null;
        if (!weatherResponse.getWeather().isEmpty())  {
            iconId = weatherResponse.getWeather().get(0).getIcon();
        }

        return new WeatherObject(weatherResponse.getName(),weatherResponse.getMain().getTemp(), weatherResponse.getId(), iconId);
    }

    public String getCity() {
        return city;
    }

    public Float getTemperature() {
        return temperature;
    }

    @Nullable
    public Integer getId() {
        return id;
    }
    @Nullable
    public String getIconId(){return iconId;}
}
