package com.example.myweather.presentation;

import android.database.DatabaseUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.example.myweather.CityObject;
import com.example.myweather.MainApplication;
import com.example.myweather.WeatherAPI;
import com.example.myweather.WeatherObject;
import com.example.myweather.WeatherResponse;
import com.example.myweather.data.WeatherRepo;
import com.example.myweather.data.WeatherRepoImpl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {
    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    WeatherRepo repositories = new WeatherRepoImpl();
    public void saveCityId(CityObject cityObjectResponse) {
        mExecutor.execute(() -> {
            String escapeCity = DatabaseUtils.sqlEscapeString(cityObjectResponse.getName());
            MainApplication.getInstance().getDb().execSQL("INSERT OR REPLACE INTO cityNameTemp (city, id) " +
                    "VALUES (" + escapeCity + ", " + cityObjectResponse.getId() + ");");

            float temp = Float.MIN_VALUE;
            WeatherResponse weatherResponse = null;
            try {
                weatherResponse = repositories.getCityTempAsWeatherResponse(cityObjectResponse.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            temp = weatherResponse.getMain().getTemp();      //запрос, Retrofit.

            repositories.saveTemptoDb(cityObjectResponse.getId(), temp);
            WeatherResponse finalWeatherResponse = weatherResponse;
            getViewState().onCityAdded(WeatherObject.convertFromResponse(finalWeatherResponse));
        });
    }


    public void loadWeather() {
        mExecutor.execute(()->{
            List<WeatherObject> list  = repositories.getAllFromDB();
            new Handler(Looper.getMainLooper()).post(()-> getViewState().onCitiesLoaded(list));
            // загрузить погоду из интернета
            //обновить температуру в базе. Статус загрузки отобразить в тосте.
            //отправит во вью новый лист с обновленной погодой.
            // добавить проект в гид и закомиить. bitbucket github
        });
    }
}
