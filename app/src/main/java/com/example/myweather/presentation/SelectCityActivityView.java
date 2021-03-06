package com.example.myweather.presentation;

import com.example.myweather.CityObject;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEnd;
import moxy.viewstate.strategy.alias.AddToEndSingle;


public interface SelectCityActivityView extends MvpView {
    @AddToEnd
    void onLoadSuccess(List<CityObject> list);

    @AddToEndSingle
    void onLoadFailure(String str);
}
