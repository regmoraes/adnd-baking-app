package com.regmoraes.bakingapp.domain;

import com.regmoraes.bakingapp.data.RecipesRepository;
import com.regmoraes.bakingapp.data.model.Recipe;

import java.util.List;

import io.reactivex.Single;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RecipesManager {

    private RecipesRepository.Remote recipesRemoteRepository;

    public RecipesManager(RecipesRepository.Remote recipesRemoteRepository) {

        this.recipesRemoteRepository = recipesRemoteRepository;
    }

    public Single<List<Recipe>> getRecipes() {

        return recipesRemoteRepository.getRecipes();
    }
}
