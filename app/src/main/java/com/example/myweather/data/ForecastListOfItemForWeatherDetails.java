package com.example.myweather.data;

public class ForecastListOfItemForWeatherDetails {
    private float tempForecastMorning;
    private float tempForecastEve;
    private float tempForecastNight;
    private float tempForecastDay;
    private String icon;
    private int dt;


    public void setTempForecastMorning(float tempForecastMorning) {
        this.tempForecastMorning = tempForecastMorning;
    }


    public void setTempForecastEve(float tempForecastEve) {
        this.tempForecastEve = tempForecastEve;
    }


    public void setTempForecastNight(float tempForecastNight) {
        this.tempForecastNight = tempForecastNight;
    }


    public void setTempForecastDay(float tempForecastDay) {
        this.tempForecastDay = tempForecastDay;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }


    public float getTempForecastMorning() {
        return tempForecastMorning;
    }

    public float getTempForecastEve() {
        return tempForecastEve;
    }

    public float getTempForecastNight() {
        return tempForecastNight;
    }

    public float getTempForecastDay() {
        return tempForecastDay;
    }

    public String getIcon() {
        return icon;
    }

    public int getDt() {
        return dt;
    }
}
