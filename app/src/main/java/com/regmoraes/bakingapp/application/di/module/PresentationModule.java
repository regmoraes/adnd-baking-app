package com.regmoraes.bakingapp.application.di.module;

import com.regmoraes.bakingapp.domain.RecipesManager;
import com.regmoraes.bakingapp.domain.RecipesWidgetManager;
import com.regmoraes.bakingapp.application.di.scope.PresentationScope;
import com.regmoraes.bakingapp.presentation.recipe_detail.RecipeDetailViewModelFactory;
import com.regmoraes.bakingapp.presentation.recipes.RecipesViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public final class PresentationModule {

    @Provides
    @PresentationScope
    public RecipesViewModelFactory providesRecipesViewModelFactory(RecipesManager recipesManager){

        return new RecipesViewModelFactory(recipesManager);
    }

    @Provides
    @PresentationScope
    public RecipeDetailViewModelFactory providesRecipeDetailViewModelFactory(
            RecipesWidgetManager recipesWidgetManager){

        return new RecipeDetailViewModelFactory(recipesWidgetManager);
    }

}