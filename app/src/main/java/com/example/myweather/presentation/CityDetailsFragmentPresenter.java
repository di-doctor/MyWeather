package com.example.myweather.presentation;

import android.os.Handler;
import android.os.Looper;

import com.example.myweather.data.Coord;
import com.example.myweather.data.ForecastResponse;
import com.example.myweather.data.WeatherDetails;
import com.example.myweather.data.WeatherRepo;
import com.example.myweather.data.WeatherRepoImpl;
import com.example.myweather.data.WeatherResponse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import moxy.MvpPresenter;

public class CityDetailsFragmentPresenter extends MvpPresenter<CityDetailsView> {
    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    WeatherRepo repo = new WeatherRepoImpl();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private Coord coord;

    public Coord getCoord() {
        return coord;
    }

    public void loadDataOboutCity(int mCityId)  {
        //progressBar
        getViewState().onLoadStarted();
        mExecutor.execute(()->{
            try {
                WeatherResponse weatherResponse = repo.getTempFromWeatherResponse(mCityId);
                ForecastResponse forecastResponse = repo.getForecastFromForecastResponse(mCityId);
                WeatherDetails weatherDetails = WeatherDetails.convertFromResponse(weatherResponse,forecastResponse);
                coord = weatherDetails.getCoord();

                mHandler.post(()-> getViewState().onLoadSuccess(weatherDetails));
            }
             catch (IOException e) {
                e.printStackTrace();
                 mHandler.post(()-> getViewState().onLoadFailed(e.getMessage()));
            }
            finally {
                mHandler.post(()->getViewState().onLoadFinished());
            }
        });

    }
}
