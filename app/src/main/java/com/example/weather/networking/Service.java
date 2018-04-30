package com.example.weather.networking;




import com.example.weather.models.WeatherInfo;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getWeather(final GetWeatherCallback callback, final String cityName) {

        return networkService.getCurrentWeather(cityName,NetworkService.API_KEY)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends WeatherInfo>>() {
                    @Override
                    public Observable<? extends WeatherInfo> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<WeatherInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(WeatherInfo weatherInfo) {
                        callback.onSuccess(weatherInfo);

                    }
                });
    }

    public interface GetWeatherCallback{
        void onSuccess(WeatherInfo weatherInfo);

        void onError(NetworkError networkError);
    }
}
