package com.regmoraes.bakingapp.application.di.module;

import com.regmoraes.bakingapp.domain.RecipesWidgetManager;
import com.regmoraes.bakingapp.data.RecipesRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public final class WidgetModule {

    @Provides
    public RecipesWidgetManager recipesManager(RecipesRepository.Local recipesLocalRepository) {

        return new RecipesWidgetManager(recipesLocalRepository);
    }
}
