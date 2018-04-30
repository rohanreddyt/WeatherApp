package com.example.weather.deps;




import com.example.weather.home.HomeActivity;
import com.example.weather.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}
