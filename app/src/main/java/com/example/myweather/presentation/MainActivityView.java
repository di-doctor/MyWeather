package com.example.myweather.presentation;

import com.example.myweather.WeatherObject;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEnd;
import moxy.viewstate.strategy.alias.AddToEndSingle;
import moxy.viewstate.strategy.alias.OneExecution;
import moxy.viewstate.strategy.alias.SingleState;
import moxy.viewstate.strategy.alias.Skip;

public interface MainActivityView extends MvpView {
    @AddToEndSingle
    void onCityAdded(WeatherObject weatherObject);

    @AddToEndSingle
    void onCitiesLoaded(List<WeatherObject> list);

    @OneExecution
    void errorLoad();

    @OneExecution
    void successLoad();

    @AddToEndSingle
    void finishLoad();
}
