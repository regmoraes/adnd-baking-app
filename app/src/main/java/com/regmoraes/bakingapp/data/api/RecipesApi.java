package com.regmoraes.bakingapp.data.api;

import com.regmoraes.bakingapp.data.RecipesRepository;
import com.regmoraes.bakingapp.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RecipesApi implements RecipesRepository.Remote {

    private final RecipesRestService restService;
    private List<Recipe> simpleCache = new ArrayList<>();

    public RecipesApi(RecipesRestService restService) {
        this.restService = restService;
    }

    @Override
    public Single<List<Recipe>> getRecipes() {

        if(simpleCache != null && !simpleCache.isEmpty()) {
            return Single.just(simpleCache);
        } else {
            return restService.getRecipes().doOnSuccess( recipes -> simpleCache.addAll(recipes) );
        }
    }
}