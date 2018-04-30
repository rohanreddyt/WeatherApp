package com.example.weather.networking;



import com.example.weather.models.WeatherInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkService {


    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    String API_KEY = "789d25f2b8cfa2c3f5a045e1dec5a2a1";


    @GET("weather?")
    Observable<WeatherInfo> getCurrentWeather(@Query("q") String q,
                                              @Query("appid") String appid);

}
