package com.example.myweather.data;

import java.util.ArrayList;
import java.util.List;

public class WeatherDetails {

    private final String nameCity;
    private final Coord coord;
    private final int id;
    private final float tempCurrent;
    List<ForecastListOfItemForWeatherDetails> foreCastListOfItemForWeatherDetails;


    public WeatherDetails(String nameCity, Coord coord, int id, float tempCurrent, List<ForecastListOfItemForWeatherDetails> list) {
        this.nameCity = nameCity;
        this.coord = coord;
        this.id = id;
        this.tempCurrent = tempCurrent;
        foreCastListOfItemForWeatherDetails = list;
    }

    //******_GET_METHOD*****

    public List<ForecastListOfItemForWeatherDetails> getForeCastListOfItemForWeatherDetails() {
        return foreCastListOfItemForWeatherDetails;
    }

    public float getTempCurrent() {
        return tempCurrent;
    }

    public String getNameCity() {
        return nameCity;
    }

    public Coord getCoord() {
        return coord;
    }

    public int getId() {
        return id;
    }

    public static WeatherDetails convertFromResponse(WeatherResponse weatherResponse, ForecastResponse forecastResponse) {

        return new WeatherDetails(weatherResponse.getName()
                , weatherResponse.getCoord()
                , weatherResponse.getId()
                , weatherResponse.getMain().getTemp()
                , convertListFromResponse(forecastResponse.getList()));

    }

    private static List<ForecastListOfItemForWeatherDetails> convertListFromResponse(List<ForecastWeatherFromApi> list) {
        List<ForecastListOfItemForWeatherDetails> listForecastFromItem = new ArrayList<>();
        for (ForecastWeatherFromApi forecastWeatherFromApi: list) {

            ForecastListOfItemForWeatherDetails forecastListOfItemForWeatherDetails = null;
            forecastListOfItemForWeatherDetails.setTempForecastDay(forecastWeatherFromApi.getTemp().getDayTemp());
            forecastListOfItemForWeatherDetails.setTempForecastEve(forecastWeatherFromApi.getTemp().getEveTemp());
            forecastListOfItemForWeatherDetails.setTempForecastMorning(forecastWeatherFromApi.getTemp().getMorningTemp());
            forecastListOfItemForWeatherDetails.setTempForecastNight(forecastWeatherFromApi.getTemp().getNightTemp());
            forecastListOfItemForWeatherDetails.setIcon(forecastWeatherFromApi.getWeatherList().get(0).getIcon());
            forecastListOfItemForWeatherDetails.setDt(forecastWeatherFromApi.getDt());

            listForecastFromItem.add(forecastListOfItemForWeatherDetails);
        }
        return listForecastFromItem;
    }
}