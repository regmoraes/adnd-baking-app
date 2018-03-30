package com.regmoraes.bakingapp.presentation.recipes;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regmoraes.bakingapp.application.BakingApp;
import com.regmoraes.bakingapp.data.model.Recipe;
import com.regmoraes.bakingapp.databinding.FragmentRecipesBinding;
import com.regmoraes.bakingapp.presentation.ResourceIdler;
import com.regmoraes.bakingapp.presentation.SimpleIdlingResource;
import com.regmoraes.bakingapp.presentation.recipe_detail.RecipeMasterDetailActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RecipesFragment extends Fragment implements RecipesAdapter.OnItemClickListener {

    @Inject public RecipesViewModelFactory viewModelFactory;
    private RecipesViewModel viewModel;
    private FragmentRecipesBinding viewBinding;

    private RecipesAdapter recipesAdapter;
    private SimpleIdlingResource simpleIdlingResource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewBinding = FragmentRecipesBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        viewBinding.recyclerViewRecipes.setLayoutManager(layoutManager);

        recipesAdapter = new RecipesAdapter(this);
        viewBinding.recyclerViewRecipes.setAdapter(recipesAdapter);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {

        Intent recipeDetailIntent = new Intent(getActivity(), RecipeMasterDetailActivity.class);
        recipeDetailIntent.putExtra(Recipe.class.getSimpleName(), recipe);
        startActivity(recipeDetailIntent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null) {

            ((BakingApp) getActivity().getApplication()).getApplicationComponent()
                    .recipesComponent().inject(this);

            simpleIdlingResource = ((ResourceIdler) getActivity()).getIdlingResource();

            setUpViewModel();
        }
    }

    private void setUpViewModel(){

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipesViewModel.class);

        if(viewModel.getRecipesResponse().getValue() == null) {

            simpleIdlingResource.setIdleState(false);

            viewModel.loadRecipes();
        }

        observeViewModel();
    }

    private void observeViewModel() {

        viewModel.getRecipesResponse().observe(this, recipesResponse -> {
            if(recipesResponse != null){

                switch (recipesResponse.status){

                    case SUCCESS:
                        showRecipes(recipesResponse.data);
                        break;

                    case LOADING:
                        showLoading();
                        break;

                    case ERROR:
                        showError();
                        break;

                    default:break;
                }

                simpleIdlingResource.setIdleState(true);
            }
        });
    }

    private void showRecipes(List<Recipe> recipes) {

        recipesAdapter.setData(recipes);

        viewBinding.recyclerViewRecipes.setVisibility(View.VISIBLE);

        viewBinding.progressBar.setVisibility(View.GONE);
        viewBinding.textViewRecipesLoadingError.setVisibility(View.GONE);
    }

    private void showLoading() {

        viewBinding.progressBar.setVisibility(View.VISIBLE);

        viewBinding.recyclerViewRecipes.setVisibility(View.GONE);
        viewBinding.textViewRecipesLoadingError.setVisibility(View.GONE);
    }

    private void showError() {

        viewBinding.textViewRecipesLoadingError.setVisibility(View.VISIBLE);

        viewBinding.progressBar.setVisibility(View.GONE);
    }
}
