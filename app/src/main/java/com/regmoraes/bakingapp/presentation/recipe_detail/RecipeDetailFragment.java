package com.regmoraes.bakingapp.presentation.recipe_detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.regmoraes.bakingapp.R;
import com.regmoraes.bakingapp.application.BakingApp;
import com.regmoraes.bakingapp.data.model.Recipe;
import com.regmoraes.bakingapp.data.model.Step;
import com.regmoraes.bakingapp.databinding.FragmentRecipeDetailBinding;
import com.regmoraes.bakingapp.widget.BakingAppWidget;

import javax.inject.Inject;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RecipeDetailFragment extends Fragment implements RecipeDetailsAdapter.OnStepClickListener {

    @Inject
    public RecipeDetailViewModelFactory viewModelFactory;
    private RecipeDetailViewModel viewModel;
    private FragmentRecipeDetailBinding viewBinding;

    private RecipeDetailsAdapter ingredientsAdapter;
    private RecipeDetailsAdapter.OnStepClickListener listener;

    private Recipe recipe;

    public static RecipeDetailFragment newInstance(Recipe recipe) {

        Bundle args = new Bundle();
        args.putParcelable(Recipe.class.getSimpleName(), recipe);

        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if(getArguments() != null) {
            recipe = getArguments().getParcelable(Recipe.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewBinding = FragmentRecipeDetailBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(view.getContext());

        ingredientsAdapter = new RecipeDetailsAdapter(this);
        ingredientsAdapter.setData(recipe.getIngredients(), recipe.getSteps());
        viewBinding.recyclerViewIngredients.setLayoutManager(ingredientsLayoutManager);
        viewBinding.recyclerViewIngredients.setAdapter(ingredientsAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((BakingApp) context.getApplicationContext()).getApplicationComponent()
                .recipeDetailComponent().inject(this);

        try {
            listener = (RecipeDetailsAdapter.OnStepClickListener) getActivity();
        } catch (ClassCastException exception) {

            throw new ClassCastException("Activity must implement " +
                    RecipeDetailsAdapter.OnStepClickListener.class.getSimpleName());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity()!= null) {

            ((BakingApp) getActivity().getApplicationContext()).getApplicationComponent()
                    .recipeDetailComponent().inject(this);

            setUpViewModel();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.recipe_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.action_recipe_add_to_widget) {
            viewModel.addRecipeToWidget(recipe);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepClicked(Step step) {
        listener.onStepClicked(step);
    }

    private void setUpViewModel() {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipeDetailViewModel.class);

        observeViewModel();
    }

    private void observeViewModel() {

        viewModel.getRecipeSavedLiveEvent().observe(this, recipeSaved -> {

            if(recipeSaved != null && recipeSaved) {
                Toast.makeText(getContext(),
                        getString(R.string.widget_recipe_added), Toast.LENGTH_SHORT).show();

                BakingAppWidget.updateWidget(getContext());
            }
        });
    }

    interface FragmentCallbacks extends RecipeDetailsAdapter.OnStepClickListener{}
}
