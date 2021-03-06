package com.example.myweather.data;

import android.content.Context;

import com.example.myweather.CityObject;

import java.io.IOException;
import java.util.List;

public interface CityRepository {
    List<CityObject> loadCitiesFromRepo(Context context) throws IOException;

    List<CityObject> queryCitiesRepo(String s);
}
