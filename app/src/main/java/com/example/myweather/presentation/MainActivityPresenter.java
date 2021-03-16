package com.example.myweather.presentation;

import com.example.myweather.CityObject;
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
            repositories.saveCityIdToDb(cityObjectResponse);

            float temp = Float.MIN_VALUE;
            WeatherResponse weatherResponse = null;
            try {

                weatherResponse = repositories.getTempFromWeatherResponse(cityObjectResponse.getId());    //запрос, Retrofit.  получение weather response

            } catch (IOException e) {
                e.printStackTrace();
            }
            temp = weatherResponse.getMain().getTemp();     //получение температуры,

            repositories.saveTemptoDb(cityObjectResponse.getId(), temp);        //save tempetature

            //WeatherResponse finalWeatherResponse = weatherResponse;
            getViewState().onCityAdded(WeatherObject.convertFromResponse(weatherResponse));
        });
    }
    // загрузить погоду из интернета
    //обновить температуру в базе. Статус загрузки отобразить в тосте.
    //отправит во вью новый лист с обновленной погодой.
    // добавить проект в гид и закомиить. bitbucket github.
    public void weatherUpdateTempList() {
        mExecutor.execute(() -> {
            List<WeatherObject> listWeatherObject = repositories.getAllFromDB();
            getViewState().onCitiesLoaded(listWeatherObject);
            try {
                for (int i = 0; i < listWeatherObject.size(); i++) {
                    WeatherResponse weatherResponse = repositories.getTempFromWeatherResponse(listWeatherObject.get(i).getId());
                    listWeatherObject.set(i, WeatherObject.convertFromResponse(weatherResponse));
                }
                getViewState().onCitiesLoaded(listWeatherObject);
                getViewState().successLoad();
            } catch (IOException e) {
                e.printStackTrace();
                getViewState().errorLoad();
            }
        });
    }
}
