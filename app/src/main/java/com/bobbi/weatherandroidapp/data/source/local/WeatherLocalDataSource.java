package com.bobbi.weatherandroidapp.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bobbi.weatherandroidapp.data.Weather;
import com.bobbi.weatherandroidapp.data.source.WeatherDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static com.bobbi.weatherandroidapp.data.source.local.WeatherPersistenceContract.WeatherEntry.COLUMN_NAME_CITY;
import static com.bobbi.weatherandroidapp.data.source.local.WeatherPersistenceContract.WeatherEntry.COLUMN_NAME_JSON_DATA;
import static com.bobbi.weatherandroidapp.data.source.local.WeatherPersistenceContract.WeatherEntry.COLUMN_NAME_LAT;
import static com.bobbi.weatherandroidapp.data.source.local.WeatherPersistenceContract.WeatherEntry.COLUMN_NAME_LNG;
import static com.bobbi.weatherandroidapp.data.source.local.WeatherPersistenceContract.WeatherEntry.TABLE_NAME;
import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */
@Singleton
public class WeatherLocalDataSource implements WeatherDataSource{
    @NonNull
    private final BriteDatabase mDatabaseHelper;

    @NonNull
    private Func1<Cursor, Weather> mWeatherMapperFunction;

    private final ObjectMapper jsonObjectMapper = new ObjectMapper();

    @Inject
    public WeatherLocalDataSource(@NonNull Context context) {
        checkNotNull(context, "context cannot be null");
        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
        SqlBrite sqlBrite = SqlBrite.create();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
        mWeatherMapperFunction = this::getWeather;
    }

    @Nullable
    private Weather getWeather(@NonNull Cursor c) {
        String jsonData = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_JSON_DATA));

        try {
            return jsonObjectMapper.readValue(jsonData, Weather.class);
        } catch (IOException e) {
            Timber.e(e);
            return null;
        }
    }

    /*@Override
    public Observable<Weather> getWeatherByCoordinate(@NonNull Weather.Coord coord) {
        String sql = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?",
                COLUMN_NAME_JSON_DATA, TABLE_NAME, COLUMN_NAME_LAT, COLUMN_NAME_LNG);

        return mDatabaseHelper
                .createQuery(TABLE_NAME, sql, coord.getLat().toString(), coord.getLon().toString())
                .mapToOneOrDefault(mWeatherMapperFunction, null);
    }*/

    @Override
    public Observable<Weather> getWeatherByCityName(@NonNull String cityName) {
        String sql = String.format("SELECT %s FROM %s WHERE %s = ?",
                COLUMN_NAME_JSON_DATA, TABLE_NAME, COLUMN_NAME_CITY);

        return mDatabaseHelper
                .createQuery(TABLE_NAME, sql, cityName)
                .mapToOneOrDefault(mWeatherMapperFunction, null);
    }

    @Override
    public void saveWeather(@NonNull Weather weather) {
        checkNotNull(weather);

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_LAT, weather.getCoord().getLat());
            values.put(COLUMN_NAME_LNG, weather.getCoord().getLon());
            final String weatherJsonData;

            weatherJsonData = jsonObjectMapper.writeValueAsString(weather);

            values.put(COLUMN_NAME_JSON_DATA, weatherJsonData);

            mDatabaseHelper.insert(TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (JsonProcessingException e) {
            Timber.e(e);
        }
    }

    @Override
    public void refreshWeather() {
        //do not need to implemented
    }
}
