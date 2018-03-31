package com.regmoraes.bakingapp.presentation.step_detail;

import android.content.Intent;
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

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class StepDetailActivity extends AppCompatActivity {

    private ActivityStepDetailBinding viewBinding;
    private List<Step> stepsExtra;
    private int selectedStepIdExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_detail);

        setExtras();

        setUpToolbar();

        setUpStepsTabs(stepsExtra);
    }

    private void setExtras() {

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            stepsExtra = extras.getParcelableArrayList(Step.class.getSimpleName());
            selectedStepIdExtra = extras.getInt(Intent.EXTRA_UID);
        }
    }

    private void setUpToolbar() {

        setSupportActionBar((Toolbar) viewBinding.toolbar);

        ActionBar supportActionBar = getSupportActionBar();

        if(supportActionBar != null) {
            supportActionBar.setTitle(R.string.step_detail_activity_title);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpStepsTabs(List<Step> steps) {

        StepDetailPageAdapter stepDetailPageAdapter = new StepDetailPageAdapter(this,
                getSupportFragmentManager().get, steps);

        viewBinding.pager.setAdapter(stepDetailPageAdapter);
        viewBinding.tabLayout.setupWithViewPager(viewBinding.pager);

        TabLayout.Tab selectedStepTab = viewBinding.tabLayout.getTabAt(selectedStepIdExtra);

        if(selectedStepIdExtra >= 0 && selectedStepTab != null) {
            selectedStepTab.select();
        }
    }
}
