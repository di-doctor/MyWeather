package com.example.myweather.data;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.myweather.CityAdapterOnTwoActivity;
import com.example.myweather.CityObject;
import com.example.myweather.data.CityRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityRepositoryImpl implements CityRepository {
    List<CityObject> listCityObject = null;

    @Override
    public List<CityObject> loadCitiesFromRepo(Context context) throws IOException {
        if (listCityObject == null) {
            String strJson = getStringFromAssetFile(context);
            listCityObject = fromJson(strJson);
            Collections.sort(listCityObject);
        }
        return listCityObject;
    }

    @Override
    public List<CityObject> queryCitiesRepo(String str) {
        List<CityObject> result = new ArrayList<>();
        for (CityObject it : listCityObject) {
            if (it.getName().toLowerCase().startsWith(str.toLowerCase())) {
                result.add(it);
            }
        }
        return result;
    }

    private String getStringFromAssetFile(Context context) throws IOException {
        String fileName = "city.list.json";
        AssetManager am = context.getAssets();
        InputStream is = am.open(fileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        String result = new String(buffer);
        is.close();
        return result;
    }

    private List<CityObject> fromJson(String strJson) {
        //  int[] array = {};
        Type listType = new TypeToken<List<CityObject>>() {
        }.getType();
        return new Gson().fromJson(strJson, listType);
    }
}
