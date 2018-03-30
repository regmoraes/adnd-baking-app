package com.regmoraes.bakingapp.application.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.regmoraes.bakingapp.R;
import com.regmoraes.bakingapp.application.BakingApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public final class AndroidModule {

    private final BakingApp mApplication;

    public AndroidModule(BakingApp mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    public Context providesApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    public SharedPreferences providesSharedPreferences(Context context) {

        return context
                .getSharedPreferences(context.getString(R.string.widget_shared_prefs), Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public Gson providesGson() {

        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .excludeFieldsWithoutExposeAnnotation()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .serializeNulls()
                .create();
    }
}
