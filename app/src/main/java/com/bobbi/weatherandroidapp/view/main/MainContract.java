package com.bobbi.weatherandroidapp.view.main;

import android.support.annotation.NonNull;

import com.bobbi.weatherandroidapp.BasePresenter;
import com.bobbi.weatherandroidapp.BaseView;
import com.bobbi.weatherandroidapp.data.Weather;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void showWeather(Weather weather);
    }

    interface Presenter extends BasePresenter {
        void showMyCurrentWeather();
    }
}
