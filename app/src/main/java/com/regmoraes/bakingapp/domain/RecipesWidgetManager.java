package com.regmoraes.bakingapp.domain;

import com.regmoraes.bakingapp.data.RecipesRepository;
import com.regmoraes.bakingapp.data.model.Recipe;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RecipesWidgetManager {

    private RecipesRepository.Local recipesLocalRepository;

    public RecipesWidgetManager(RecipesRepository.Local recipesLocalRepository) {

        this.recipesLocalRepository = recipesLocalRepository;
    }

    public Recipe loadRecipe() {
        return recipesLocalRepository.loadRecipe();
    }

    public void saveRecipe(Recipe recipe) {
        recipesLocalRepository.saveRecipe(recipe);
    }
}
