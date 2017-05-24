package com.bobbi.weatherandroidapp.view.main;

import com.bobbi.weatherandroidapp.BaseApplicationComponent;
import com.bobbi.weatherandroidapp.util.di.FragmentScoped;

import dagger.Component;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */
@FragmentScoped
@Component(dependencies = BaseApplicationComponent.class, modules = {MainPresenterModule.class})
public interface MainComponent{

    void inject(MainActivity mainActivity);

}
