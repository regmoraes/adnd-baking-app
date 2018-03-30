package com.regmoraes.bakingapp.application.di.module;

import com.regmoraes.bakingapp.data.RecipesRepository;
import com.regmoraes.bakingapp.data.api.RecipesApi;
import com.regmoraes.bakingapp.data.api.RecipesRestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module(includes = NetworkModule.class)
public final class ApiModule {

    @Provides
    @Singleton
    RecipesRestService providesRecipesRestService(Retrofit retrofit) {

        return retrofit.create(RecipesRestService.class);
    }

    @Provides
    @Singleton
    public RecipesRepository.Remote providesRecipesApi(RecipesRestService restService){

        return new RecipesApi(restService);
    }
}
