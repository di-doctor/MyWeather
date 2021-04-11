package com.example.myweather;

import com.example.myweather.data.ForecastResponse;
import com.example.myweather.data.WeatherResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherAPI {
    public static final String CNT = "10";
    public static String KEY = "f7558befe506a8be0b6f433a346c9bcc";
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static ApiInterface api = null;

    public interface ApiInterface {
        @GET("weather")
        Call<WeatherResponse> getTempCall(
                @Query("id") String id,
                @Query("units") String units,
                @Query("appid") String appid
        );
        @GET("forecast/daily")
        Call<ForecastResponse> getForecastCall(
                @Query("id") String id,
                @Query("units") String units,
                @Query("appid") String appid,
                @Query("cnt") String cnt
        );
    }

    public static ApiInterface getClient() {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiInterface.class);            //Создание объекта некого класса (за кулисами-анонимного) который  реализует интерфейс ApiInterface, этот объект имеет ссылку типа интерфейс
        }
        return api;
    }

}

