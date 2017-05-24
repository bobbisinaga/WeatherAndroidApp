package com.bobbi.weatherandroidapp.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */

public final class WeatherPersistenceContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private WeatherPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class WeatherEntry implements BaseColumns {
        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LNG = "lng";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_JSON_DATA = "json_data";
    }
}
