package com.bobbi.weatherandroidapp;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.bobbi.weatherandroidapp.events.AuthenticationErrorEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by bobbi.sinaga on 5/22/2017.
 */

public class BaseApplication extends Application {
    @Inject
    EventBus mEventBus;

    private BaseApplicationComponent mBaseApplicationComponent;

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        boolean isDebuggable = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));

        if (isDebuggable) {
            Timber.plant(new Timber.DebugTree());
        }

        mBaseApplicationComponent = DaggerBaseApplicationComponent
                .builder()
                .baseApplicationModule(new BaseApplicationModule(this))
                .build();

        mBaseApplicationComponent.inject(this);

        mEventBus.register(this);
    }

    @Override
    public void onTerminate() {
        mEventBus.unregister(this);
        super.onTerminate();
    }

    public BaseApplicationComponent getBaseApplicationComponent() {
        return mBaseApplicationComponent;
    }

    @Subscribe
    public void onEvent(AuthenticationErrorEvent event) {
        Timber.e("AuthenticationErrorEvent arise!");
    }
}
