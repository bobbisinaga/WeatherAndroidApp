package com.bobbi.weatherandroidapp.view.main;

import android.support.annotation.NonNull;

import com.bobbi.weatherandroidapp.data.Weather;
import com.bobbi.weatherandroidapp.data.source.WeatherRepository;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */

public final class MainPresenter implements MainContract.Presenter{
    private final WeatherRepository mWeatherRepository;
    private final MainContract.View mMainView;
    private final CompositeSubscription mSubscriptions;

    @Inject
    MainPresenter(WeatherRepository weatherRepository, MainContract.View mainView) {
        mWeatherRepository = weatherRepository;
        mMainView = mainView;
        mSubscriptions = new CompositeSubscription();
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mMainView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        showMyCurrentWeather();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void showMyCurrentWeather() {
        final String cityName = "Jakarta";
        final Subscription subscription = mWeatherRepository
                .getWeatherByCityName(cityName)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //onNext
                        this::showWeather,
                        //onError
                        Timber::e,
                        //onCompleted
                        () -> Timber.i("Complete current loading weather")
                );
        mSubscriptions.add(subscription);
    }

    private void showWeather(@NonNull Weather weather) {
        Timber.i("The current weather %s", weather.toString());

        mMainView.showWeather(weather);
    }
}
