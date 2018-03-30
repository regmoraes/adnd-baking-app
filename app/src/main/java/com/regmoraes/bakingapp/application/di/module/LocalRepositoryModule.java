package com.regmoraes.bakingapp.application.di.module;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.regmoraes.bakingapp.data.RecipesRepository;
import com.regmoraes.bakingapp.data.storage.RecipesSharedPrefs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public final class LocalRepositoryModule {

    @Provides
    @Singleton
    RecipesRepository.Local providesLocalRecipesRepository(SharedPreferences sharedPreferences,
                                                           Gson gson) {

        return new RecipesSharedPrefs(sharedPreferences, gson);
    }
}
