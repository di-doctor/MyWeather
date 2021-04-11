package com.example.myweather.data;

import com.example.myweather.CityObject;
import com.example.myweather.WeatherObject;

import java.io.IOException;
import java.util.List;

public interface WeatherRepo {
    WeatherResponse getTempFromWeatherResponse(int idcity) throws IOException;

    void saveTemptoDb(int id, float temp, String iconId);

    List<WeatherObject> getAllFromDB();

    void saveCityIdToDb(CityObject cityObject);

    ForecastResponse getForecastFromForecastResponse(int idCity) throws IOException;

}
