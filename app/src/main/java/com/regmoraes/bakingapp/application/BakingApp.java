package com.regmoraes.bakingapp.application;

import android.app.Application;

import com.regmoraes.bakingapp.BuildConfig;
import com.regmoraes.bakingapp.application.di.component.ApplicationComponent;
import com.regmoraes.bakingapp.application.di.component.DaggerApplicationComponent;
import com.regmoraes.bakingapp.application.di.module.AndroidModule;

import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class BakingApp extends Application {

    private ApplicationComponent mApplicationComponents;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponents = createComponent();

        if(BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponents;
    }

    protected ApplicationComponent createComponent() {

        return DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }
}
