package com.regmoraes.bakingapp.presentation.recipes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.regmoraes.bakingapp.R;
import com.regmoraes.bakingapp.databinding.ActivityRecipesBinding;
import com.regmoraes.bakingapp.presentation.ResourceIdler;
import com.regmoraes.bakingapp.presentation.SimpleIdlingResource;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RecipesActivity extends AppCompatActivity implements ResourceIdler {

    private ActivityRecipesBinding viewBinding;
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes);

        setUpToolbar();
    }

    private void setUpToolbar() {
        setSupportActionBar((Toolbar) viewBinding.toolbar);
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @NonNull
    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    @Override
    public SimpleIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
