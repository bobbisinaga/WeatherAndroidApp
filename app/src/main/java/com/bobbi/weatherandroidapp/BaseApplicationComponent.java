package com.bobbi.weatherandroidapp;

import android.content.Context;

import com.bobbi.weatherandroidapp.data.source.WeatherDataSource;
import com.bobbi.weatherandroidapp.data.source.WeatherRepository;
import com.bobbi.weatherandroidapp.data.source.remote.APIService;
import com.bobbi.weatherandroidapp.data.source.remote.UnauthorisedInterceptor;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */
@Singleton
@Component(modules = BaseApplicationModule.class)
public interface BaseApplicationComponent {
    void inject(BaseApplication baseApplication);

    void inject(UnauthorisedInterceptor unauthorisedInterceptor);

    BaseApplication getBaseApplication();

    APIService getAPIService();

    WeatherRepository getWeatherRepository();
}
