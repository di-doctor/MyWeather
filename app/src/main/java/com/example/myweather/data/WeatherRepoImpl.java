package com.example.myweather.data;

import android.database.Cursor;
import android.database.DatabaseUtils;

import com.example.myweather.CityObject;
import com.example.myweather.MainApplication;
import com.example.myweather.WeatherAPI;
import com.example.myweather.WeatherObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherRepoImpl implements WeatherRepo {
    @Override
    public WeatherResponse getTempFromWeatherResponse(int idcity) throws IOException {          //matching temperature to city
        return WeatherAPI.getClient().getTempCall(String.valueOf(idcity),
                "metric", com.example.myweather.WeatherAPI.KEY).execute().body();
    }
    @Override
    public  ForecastResponse getForecastFromForecastResponse(int idCity) throws IOException {
        return  WeatherAPI.getClient().getForecastCall(String.valueOf(idCity),"metric",WeatherAPI.KEY
                ,WeatherAPI.CNT).execute().body();

    }

    @Override
    public void saveTemptoDb(int id, float temp, String iconId) {          // save temp using id.
        MainApplication.getInstance().getDb().execSQL("UPDATE cityNameTemp SET temperature=" + temp + " " +
                "WHERE cityNameTemp.id = " + id + ";");
        MainApplication.getInstance().getDb().execSQL("UPDATE cityNameTemp SET iconId=" + DatabaseUtils.sqlEscapeString(iconId) + " " +
                "WHERE cityNameTemp.id = " + id + ";");

    }

    @Override
    public List<WeatherObject> getAllFromDB() {     //list from db
        List<WeatherObject> list = new ArrayList<>();
        try (Cursor cursor = MainApplication.getInstance().getDb().rawQuery("SELECT * FROM cityNameTemp",null)) {
            while(cursor.moveToNext()){
                String city = cursor.getString(cursor.getColumnIndex("city"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                float temperature = cursor.getFloat(cursor.getColumnIndex("temperature"));
                String iconId = cursor.getString((cursor.getColumnIndex("iconId")));
                list.add(new WeatherObject(city, temperature, id, iconId));
            }
            return list;
        }
    }

    @Override
    public void saveCityIdToDb(CityObject cityObject) {
        String escapeCity = DatabaseUtils.sqlEscapeString(cityObject.getName());
        MainApplication.getInstance().getDb().execSQL("INSERT OR REPLACE INTO cityNameTemp (city, id) " +
                "VALUES (" + escapeCity + ", " + cityObject.getId() + ");");
    }


}
