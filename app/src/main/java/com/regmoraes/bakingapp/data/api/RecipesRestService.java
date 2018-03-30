package com.regmoraes.bakingapp.data.api;

import com.regmoraes.bakingapp.data.model.Recipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface RecipesRestService {

    @GET("baking.json")
    Single<List<Recipe>> getRecipes();
}
