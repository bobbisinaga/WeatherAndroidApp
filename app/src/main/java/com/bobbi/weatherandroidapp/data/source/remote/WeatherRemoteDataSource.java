package com.bobbi.weatherandroidapp.data.source.remote;

import android.support.annotation.NonNull;

import com.bobbi.weatherandroidapp.data.Weather;
import com.bobbi.weatherandroidapp.data.source.WeatherDataSource;
import com.bobbi.weatherandroidapp.util.UnitLocale;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */
@Singleton
public class WeatherRemoteDataSource implements WeatherDataSource {
    private final APIService mApiService;

    @Inject
    public WeatherRemoteDataSource(APIService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<Weather> getWeatherByCityName(@NonNull String cityName) {
        return mApiService.getWeatherByCityName(cityName, UnitLocale.Metric);
    }

    @Override
    public void saveWeather(@NonNull Weather weather) {
        //do not need to implemented
    }

    @Override
    public void refreshWeather() {
        //do not need to implemented
    }
}
