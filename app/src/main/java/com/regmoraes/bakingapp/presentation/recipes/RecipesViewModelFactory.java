package com.regmoraes.bakingapp.presentation.recipes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.regmoraes.bakingapp.domain.RecipesManager;

import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RecipesViewModelFactory implements ViewModelProvider.Factory {

    private RecipesManager recipesManager;

    public RecipesViewModelFactory(RecipesManager recipesManager) {
        this.recipesManager = recipesManager;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipesViewModel.class)) {

            Timber.d("New ViewModel created");

            return (T) new RecipesViewModel(recipesManager);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
