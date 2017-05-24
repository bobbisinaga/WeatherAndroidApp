package com.bobbi.weatherandroidapp.view.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */
@Module
public class MainPresenterModule {
    private final MainContract.View mView;

    public MainPresenterModule(MainContract.View mView) {
        this.mView = mView;
    }

    @Provides
    public MainContract.View providesMainView() {
        return mView;
    }
}
