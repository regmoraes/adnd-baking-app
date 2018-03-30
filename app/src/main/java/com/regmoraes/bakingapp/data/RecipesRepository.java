package com.regmoraes.bakingapp.data;


import com.regmoraes.bakingapp.data.model.Recipe;

import java.util.List;

import io.reactivex.Single;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface RecipesRepository {

    interface Remote {

        Single<List<Recipe>> getRecipes();
    }

    interface Local {

        void saveRecipe(Recipe recipe);

        Recipe loadRecipe();
    }
}
