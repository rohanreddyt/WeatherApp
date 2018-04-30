package com.example.weather.home;


import com.example.weather.models.WeatherInfo;
import com.example.weather.networking.NetworkError;
import com.example.weather.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter {
    private final Service service;
    private final HomeView view;
    private CompositeSubscription subscriptions;

    public HomePresenter(Service service, HomeView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getWeather(String cityName) {
        view.showWait();

        Subscription subscription = service.getWeather(new Service.GetWeatherCallback() {
            @Override
            public void onSuccess(WeatherInfo weatherInfo) {
                view.removeWait();
                view.getWeatherInfoSuccess(weatherInfo);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        },cityName);

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
