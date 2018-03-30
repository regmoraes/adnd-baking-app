package com.regmoraes.bakingapp.application.di.module;

import com.regmoraes.bakingapp.domain.RecipesManager;
import com.regmoraes.bakingapp.data.RecipesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module(includes = {ApiModule.class, LocalRepositoryModule.class})
public final class DomainModule {

    @Provides
    @Singleton
    public RecipesManager recipesManager(RecipesRepository.Remote recipesRemoteRepository) {

        return new RecipesManager(recipesRemoteRepository);
    }
}
