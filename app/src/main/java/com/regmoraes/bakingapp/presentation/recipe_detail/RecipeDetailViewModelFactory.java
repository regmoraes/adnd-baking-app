package com.regmoraes.bakingapp.presentation.recipe_detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.regmoraes.bakingapp.domain.RecipesWidgetManager;

import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RecipeDetailViewModelFactory implements ViewModelProvider.Factory {

    private RecipesWidgetManager recipesWidgetManager;

    public RecipeDetailViewModelFactory(RecipesWidgetManager recipesWidgetManager) {
        this.recipesWidgetManager = recipesWidgetManager;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipeDetailViewModel.class)) {

            Timber.d("New ViewModel created");

            return (T) new RecipeDetailViewModel(recipesWidgetManager);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
