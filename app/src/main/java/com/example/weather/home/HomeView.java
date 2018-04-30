package com.example.weather.home;


import com.example.weather.models.WeatherInfo;

public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getWeatherInfoSuccess(WeatherInfo weatherInfo);

}
