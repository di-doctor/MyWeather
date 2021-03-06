package com.example.myweather.data;

import android.database.Cursor;

import com.example.myweather.MainApplication;
import com.example.myweather.WeatherAPI;
import com.example.myweather.WeatherObject;
import com.example.myweather.WeatherResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherRepoImpl implements WeatherRepo {
    @Override
    public WeatherResponse getCityTempAsWeatherResponse(int idcity) throws IOException {
        return WeatherAPI.getClient().getTempCall(String.valueOf(idcity),
                "metric", com.example.myweather.WeatherAPI.KEY).execute().body();
    }

    @Override
    public void saveTemptoDb(int id, float temp) {
        MainApplication.getInstance().getDb().execSQL("UPDATE cityNameTemp SET temperature=" + temp + " " +
                "WHERE cityNameTemp.id = " + id + ";");

    }

    @Override
    public List<WeatherObject> getAllFromDB() {
        List<WeatherObject> list = new ArrayList<>();
        try (Cursor cursor = MainApplication.getInstance().getDb().rawQuery("SELECT * FROM cityNameTemp",null)) {
            while(cursor.moveToNext()){
                String city = cursor.getString(cursor.getColumnIndex("city"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                float temperature = cursor.getFloat(cursor.getColumnIndex("temperature"));
                list.add(new WeatherObject(city, temperature, id));
            }
            return list;
        }
    }
}
