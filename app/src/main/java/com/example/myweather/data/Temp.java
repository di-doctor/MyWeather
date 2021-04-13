package com.example.myweather.data;

import com.google.gson.annotations.SerializedName;

public class Temp {
    @SerializedName("day")
    private float day;

    @SerializedName("night")
    private float night;

    @SerializedName("eve")
    private float eve;

    @SerializedName("morn")
    private float morning;

    public float getDayTemp() {
        return day;
    }

    public float getNightTemp() {
        return night;
    }

    public float getEveTemp() {
        return eve;
    }

    public float getMorningTemp() {
        return morning;
    }



}
