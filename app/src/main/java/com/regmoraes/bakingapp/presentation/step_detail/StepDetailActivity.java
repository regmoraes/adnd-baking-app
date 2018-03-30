package com.regmoraes.bakingapp.presentation.step_detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.regmoraes.bakingapp.R;
import com.regmoraes.bakingapp.data.model.Step;
import com.regmoraes.bakingapp.databinding.ActivityStepDetailBinding;
import com.regmoraes.bakingapp.presentation.SimpleIdlingResource;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class StepDetailActivity extends AppCompatActivity {

    private ActivityStepDetailBinding viewBinding;

    private StepDetailPageAdapter stepDetailPageAdapter;
    private ActionBar supportActionBar;

    private Bundle extras;
    private List<Step> stepsExtra;

    // The Idling Resource which will be null in production.
    @Nullable private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_detail);

        setExtras();

        setUpToolbar();

        setUpStepsTabs(stepsExtra);
    }

    private void setExtras() {

        extras = getIntent().getExtras();

        if(extras != null) {
            stepsExtra = extras.getParcelableArrayList(Step.class.getSimpleName());
        }
    }

    private void setUpToolbar() {

        setSupportActionBar((Toolbar) viewBinding.toolbar);

        supportActionBar = getSupportActionBar();

        if(supportActionBar != null) {
            supportActionBar.setTitle(R.string.step_detail_activity_title);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpStepsTabs(List<Step> steps) {

        stepDetailPageAdapter = new StepDetailPageAdapter(this,
                getSupportFragmentManager(), steps);

        viewBinding.pager.setAdapter(stepDetailPageAdapter);
        viewBinding.tabLayout.setupWithViewPager(viewBinding.pager);

        TabLayout.Tab initialTab = viewBinding.tabLayout.getTabAt(0);

        if(initialTab != null) initialTab.select();

//        if(extras != null) {
//
//            final int recipeId = extras.getInt(EXTRA_RECIPE_ID);
//            final int stepId = extras.getInt(EXTRA_STEP_ID);
//
//            final Integer previousStepId;
//
//            if(extras.containsKey(EXTRA_PREVIOUS_STEP)) {
//                previousStepId = extras.getInt(EXTRA_PREVIOUS_STEP);
//            } else {
//                previousStepId = null;
//            }
//
//            final Integer nextStepId;
//
//            if(extras.containsKey(EXTRA_NEXT_STEP)) {
//                nextStepId = extras.getInt(EXTRA_NEXT_STEP);
//            } else {
//                nextStepId = null;
//            }
//
//            StepDetailFragment stepDetailFragment =
//                    StepDetailFragment.newInstance(recipeId, stepId, previousStepId, nextStepId);
//
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.container_step_detail, stepDetailFragment)
//                    .commit();
//        }
    }
}
