package com.bobbi.weatherandroidapp;

import android.content.Context;

import com.bobbi.weatherandroidapp.data.source.WeatherDataSource;
import com.bobbi.weatherandroidapp.data.source.local.WeatherLocalDataSource;
import com.bobbi.weatherandroidapp.data.source.remote.APIService;
import com.bobbi.weatherandroidapp.data.source.remote.WeatherRemoteDataSource;
import com.bobbi.weatherandroidapp.util.di.Local;
import com.bobbi.weatherandroidapp.util.di.Remote;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */
@Module
public class BaseApplicationModule {
    private final BaseApplication mBaseApplication;

    public BaseApplicationModule(BaseApplication baseApplication) {
        mBaseApplication = baseApplication;
    }

    @Provides
    @Singleton
    BaseApplication provideBaseApplication() {
        return mBaseApplication;
    }

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return new EventBus();
    }

    @Provides
    @Singleton
    public APIService provideApiService(BaseApplication baseApplication) {
        return APIService.Factory.create(baseApplication);
    }

    @Singleton
    @Local
    @Provides
    WeatherDataSource provideWeatherLocalDataSource(BaseApplication baseApplication) {
        return new WeatherLocalDataSource(baseApplication);
    }

    @Singleton
    @Remote
    @Provides
    WeatherDataSource provideWeatherRemoteDataSource(APIService apiService) {
        return new WeatherRemoteDataSource(apiService);
    }
}
