package com.bobbi.weatherandroidapp.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */

public class WeatherDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "WeatherApp.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String REAL_TYPE = " REAL";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WeatherPersistenceContract.WeatherEntry.TABLE_NAME + " (" +
                    WeatherPersistenceContract.WeatherEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    WeatherPersistenceContract.WeatherEntry.COLUMN_NAME_LAT + REAL_TYPE + COMMA_SEP +
                    WeatherPersistenceContract.WeatherEntry.COLUMN_NAME_LNG + REAL_TYPE + COMMA_SEP +
                    WeatherPersistenceContract.WeatherEntry.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
                    WeatherPersistenceContract.WeatherEntry.COLUMN_NAME_JSON_DATA + TEXT_TYPE +
                    " )";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
