package com.example.myweather.presentation;

import android.content.Context;

import com.example.myweather.CityObject;
import com.example.myweather.data.CityRepository;
import com.example.myweather.data.CityRepositoryImpl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class SelectCityActivityPresenter extends MvpPresenter<SelectCityActivityView> {
    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    CityRepository repository = new CityRepositoryImpl();

    public void loadCities(Context context) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<CityObject> listCityObject = null;
                try {
                    listCityObject = repository.loadCitiesFromRepo(context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getViewState().onLoadSuccess(listCityObject);
            }
        });
    }

    public void queryCities(String str) {
        mExecutor.execute(()->{
            List<CityObject> cityObjectList = repository.queryCitiesRepo(str);
            getViewState().onLoadSuccess(cityObjectList);
        });
    }
}
