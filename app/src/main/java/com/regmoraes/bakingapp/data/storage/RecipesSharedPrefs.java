package com.regmoraes.bakingapp.data.storage;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.regmoraes.bakingapp.data.RecipesRepository;
import com.regmoraes.bakingapp.data.model.Recipe;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RecipesSharedPrefs implements RecipesRepository.Local {

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public RecipesSharedPrefs(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    @Override
    public void saveRecipe(Recipe recipe) {

        String recipeJson = gson.toJson(recipe, Recipe.class);

        sharedPreferences.edit().putString(Recipe.class.getSimpleName(), recipeJson).apply();
    }

    @Override
    public Recipe loadRecipe() {

        try {

            String recipeJson =
                    sharedPreferences.getString(Recipe.class.getSimpleName(), "{}");

            return gson.fromJson(recipeJson, Recipe.class);

        } catch (Exception exception) {

            return null;
        }
    }
}
