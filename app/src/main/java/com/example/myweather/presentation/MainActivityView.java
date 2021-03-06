package com.example.myweather.presentation;

import com.example.myweather.WeatherObject;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEnd;
import moxy.viewstate.strategy.alias.AddToEndSingle;

public interface MainActivityView extends MvpView {
    @AddToEnd
    void onCityAdded(WeatherObject weatherObject);
    @AddToEndSingle
    void onCitiesLoaded(List<WeatherObject> list);
}
