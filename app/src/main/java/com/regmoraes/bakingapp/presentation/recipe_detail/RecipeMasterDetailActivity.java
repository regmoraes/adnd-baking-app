package com.regmoraes.bakingapp.presentation.recipe_detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.regmoraes.bakingapp.R;
import com.regmoraes.bakingapp.data.model.Recipe;
import com.regmoraes.bakingapp.data.model.Step;
import com.regmoraes.bakingapp.databinding.ActivityRecipeDetailBinding;
import com.regmoraes.bakingapp.presentation.step_detail.StepDetailActivity;
import com.regmoraes.bakingapp.presentation.step_detail.StepDetailFragment;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RecipeMasterDetailActivity extends AppCompatActivity
        implements RecipeDetailFragment.FragmentCallbacks {

    private ActivityRecipeDetailBinding viewBinding;

    private boolean twoPane;

    private Recipe recipeExtra;
    private String toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        twoPane = viewBinding.getRoot().findViewById(R.id.container_step_detail) != null;

        setExtras();

        setUpToolbar();

        if(savedInstanceState == null) {
            setUpFragments();
        }
    }

    private void setUpFragments() {

        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipeExtra);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_recipe_detail, recipeDetailFragment)
                .commit();
    }


    private void setExtras() {

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            recipeExtra = extras.getParcelable(Recipe.class.getSimpleName());
            toolbarTitle = recipeExtra != null ? recipeExtra.getName() : getString(R.string.recipe_details_activity_title);
        }
    }

    private void setUpToolbar() {

        setSupportActionBar((Toolbar) viewBinding.toolbar);

        ActionBar supportActionBar = getSupportActionBar();

        if(supportActionBar != null) {
            supportActionBar.setTitle(toolbarTitle);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onStepClicked(Step step) {

        if(twoPane) {

            StepDetailFragment stepDetailFragment =
                    StepDetailFragment.newInstance(step);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_step_detail, stepDetailFragment)
                    .commit();
        } else {

            Intent stepDetailIntent = new Intent(this, StepDetailActivity.class);
            stepDetailIntent.putParcelableArrayListExtra(Step.class.getSimpleName(), recipeExtra.getSteps());
            stepDetailIntent.putExtra(Intent.EXTRA_UID, step.getId());

            startActivity(stepDetailIntent);
        }
    }
}
