package com.example.myweather.presentation;

import com.example.myweather.data.WeatherDetails;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;
import moxy.viewstate.strategy.alias.OneExecution;

public interface CityDetailsView extends MvpView {
    @AddToEndSingle
    void onLoadStarted(); // прогресс бар показать
    @AddToEndSingle
    void onLoadFinished(); //скрыть
    @AddToEndSingle
    void onLoadSuccess(WeatherDetails weatherDetails); //придумать обьект
    @AddToEndSingle
    void onLoadFailed(String message); // плказать ошибку и button загрузить
}
