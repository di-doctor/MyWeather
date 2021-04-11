package com.example.myweather.presentation;

import android.os.Handler;
import android.os.Looper;

import com.example.myweather.CityObject;
import com.example.myweather.WeatherObject;
import com.example.myweather.data.WeatherResponse;
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
    WeatherRepo repo = new WeatherRepoImpl();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public void saveCityId(CityObject cityObjectResponse) {
        mExecutor.execute(() -> {
            repo.saveCityIdToDb(cityObjectResponse);

            float temp = Float.MIN_VALUE;
            String iconId = "";
            try {
                WeatherResponse weatherResponse = repo.getTempFromWeatherResponse(cityObjectResponse.getId());    //запрос, Retrofit.  получение weather response
                temp = weatherResponse.getMain().getTemp();     //получение температуры,
                iconId = weatherResponse.getWeather().get(0).getIcon();
                repo.saveTemptoDb(cityObjectResponse.getId(), temp, iconId);        //save tempetature
                //WeatherResponse finalWeatherResponse = weatherResponse;
                mHandler.post(() -> getViewState().onCityAdded(WeatherObject.convertFromResponse(weatherResponse)));
            } catch (IOException e) {
                e.printStackTrace();
                mHandler.post(() -> getViewState().errorLoad());
            }
        });
    }
    // загрузить погоду из интернета
    //обновить температуру в базе. Статус загрузки отобразить в тосте.
    //отправит во вью новый лист с обновленной погодой.
    // добавить проект в гид и закомиить. bitbucket github.
    public void weatherUpdateTempList() {
        mExecutor.execute(() -> {
            List<WeatherObject> listWeatherObject = repo.getAllFromDB();//возвращается лист weather object из базы
            mHandler.post(() -> getViewState().onCitiesLoaded(listWeatherObject));  // грузиться сначала из базы, если есть интернет то грузиться из сети температура.
            try {
                for (int i = 0; i < listWeatherObject.size(); i++) {
                    WeatherResponse weatherResponse = repo.getTempFromWeatherResponse(listWeatherObject.get(i).getId());
                    listWeatherObject.set(i, WeatherObject.convertFromResponse(weatherResponse));
                    WeatherObject newWeather = listWeatherObject.get(i);
                    repo.saveTemptoDb(newWeather.getId(), newWeather.getTemperature(), newWeather.getIconId());        //save tempetature
                }
                mHandler.post(() -> {
                    getViewState().onCitiesLoaded(listWeatherObject);   // обновленный из интернета лист идет во вью.
                    getViewState().successLoad();
                });
            } catch (IOException e) {
                e.printStackTrace();
                mHandler.post(() -> getViewState().errorLoad());
            } finally {
                mHandler.post(() -> getViewState().finishLoad());
            }
        });
    }
}
