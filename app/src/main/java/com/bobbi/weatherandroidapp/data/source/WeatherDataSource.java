package com.bobbi.weatherandroidapp.data.source;

import android.support.annotation.NonNull;

import com.bobbi.weatherandroidapp.data.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;

import rx.Observable;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */

public interface WeatherDataSource {
    Observable<Weather> getWeatherByCityName(@NonNull String cityName);

    void saveWeather(@NonNull Weather weather);

    void refreshWeather();
}
