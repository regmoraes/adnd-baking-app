package com.regmoraes.bakingapp.presentation.recipe_detail;

import android.arch.lifecycle.ViewModel;

import com.regmoraes.bakingapp.data.model.Recipe;
import com.regmoraes.bakingapp.domain.RecipesWidgetManager;
import com.regmoraes.bakingapp.presentation.SingleLiveEvent;

import io.reactivex.disposables.CompositeDisposable;

public final class RecipeDetailViewModel extends ViewModel {

    private final SingleLiveEvent<Boolean> recipeSavedLiveEvent = new SingleLiveEvent<>();

    private final CompositeDisposable disposables = new CompositeDisposable();

    private RecipesWidgetManager recipesWidgetManager;

    public RecipeDetailViewModel(RecipesWidgetManager recipesWidgetManager) {
        this.recipesWidgetManager = recipesWidgetManager;
    }

    public void addRecipeToWidget(Recipe recipe) {

        recipesWidgetManager.saveRecipe(recipe);
        recipeSavedLiveEvent.setValue(true);
    }

    public SingleLiveEvent<Boolean> getRecipeSavedLiveEvent() {
        return recipeSavedLiveEvent;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
