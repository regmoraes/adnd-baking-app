package com.regmoraes.bakingapp.presentation.recipes;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.regmoraes.bakingapp.data.model.Recipe;
import com.regmoraes.bakingapp.domain.RecipesManager;
import com.regmoraes.bakingapp.presentation.ViewModelResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public final class RecipesViewModel extends ViewModel {

    private final MutableLiveData<ViewModelResponse<List<Recipe>>> recipesResponse = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private RecipesManager recipesManager;

    public RecipesViewModel(RecipesManager recipesManager) {
        this.recipesManager = recipesManager;
    }

    public void loadRecipes() {

        disposables.add(
                recipesManager.getRecipes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__-> recipesResponse.setValue(ViewModelResponse.loading(null)))
                        .subscribe(
                                recipes -> recipesResponse.setValue(ViewModelResponse.success(recipes)),
                                error -> recipesResponse.setValue(ViewModelResponse.error(error.getMessage(), null))
                        )
        );
    }

    public MutableLiveData<ViewModelResponse<List<Recipe>>> getRecipesResponse() {
        return recipesResponse;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
