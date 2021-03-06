package com.example.myweather.data;

import com.example.myweather.CityObject;
import com.example.myweather.WeatherObject;
import com.example.myweather.WeatherResponse;

import java.io.IOException;
import java.util.List;

public interface WeatherRepo {
    WeatherResponse getTempFromWeatherResponse(int idcity) throws IOException;

    void saveTemptoDb(int id, float temp);

    List<WeatherObject> getAllFromDB();

    void saveCityIdToDb(CityObject cityObject);

}
