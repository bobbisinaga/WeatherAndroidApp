package com.bobbi.weatherandroidapp.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.bobbi.weatherandroidapp.data.Weather;
import com.bobbi.weatherandroidapp.util.di.Local;
import com.bobbi.weatherandroidapp.util.di.Remote;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */
@Singleton
public class WeatherRepository implements WeatherDataSource {
    private final WeatherDataSource mWeatherRemoteDataSource;

    private final WeatherDataSource mWeatherLocalDataSource;

    @VisibleForTesting
    @Nullable
    Map<Object, Weather> mCachedWeathers;

    @VisibleForTesting
    boolean mCacheIsDirty = false;

    @Inject
    public WeatherRepository(
            @Remote WeatherDataSource weatherRemoteDataSource,
            @Local WeatherDataSource weatherLocalDataSource) {
        mWeatherRemoteDataSource = weatherRemoteDataSource;
        mWeatherLocalDataSource = weatherLocalDataSource;
    }

    @Override
    public Observable<Weather> getWeatherByCityName(@NonNull String cityName) {
        checkNotNull(cityName);

        final Weather cachedWeather = getWeatherByCityNameFromCache(cityName);

        if (cachedWeather != null) {
            return Observable.just(cachedWeather);
        }

        if (mCachedWeathers == null) {
            mCachedWeathers = new LinkedHashMap<>();
        }

        final Observable<Weather> localWeather = getWeatherByCityNameFromLocal(cityName);
        final Observable<Weather> remoteWeather = getWeatherByCityNameFromRemote(cityName);

        localWeather.map(weather -> {
            if (weather == null) {
                return remoteWeather;
            }
            return localWeather;
        });

        return remoteWeather;
    }

    @Override
    public void saveWeather(@NonNull Weather weather) {
        checkNotNull(weather);
        mWeatherLocalDataSource.saveWeather(weather);

        if (mCachedWeathers == null) {
            mCachedWeathers = new LinkedHashMap<>();
        }
        mCachedWeathers.put(weather.getCoord(), weather);
    }

    @Override
    public void refreshWeather() {
        mCacheIsDirty = true;
    }

    @NonNull
    private Observable<Weather> getWeatherByCityNameFromRemote(@NonNull String cityName) {
        return mWeatherRemoteDataSource.getWeatherByCityName(cityName)
                .doOnNext(weather -> {
                    mWeatherLocalDataSource.saveWeather(weather);
                    mCachedWeathers.put(cityName, weather);
                })
                .first();
    }

    @Nullable
    private Observable<Weather> getWeatherByCityNameFromLocal(@NonNull String cityName) {
        return mWeatherLocalDataSource.getWeatherByCityName(cityName)
                .doOnNext(weather -> mCachedWeathers.put(cityName, weather))
                .first();
    }

    @Nullable
    private Weather getWeatherByCityNameFromCache(@NonNull String cityName) {
        if (mCachedWeathers == null || mCachedWeathers.isEmpty()) {
            return null;
        } else {
            return mCachedWeathers.get(cityName);
        }
    }
}
