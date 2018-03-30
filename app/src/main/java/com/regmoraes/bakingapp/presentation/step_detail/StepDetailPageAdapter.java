package com.regmoraes.bakingapp.presentation.step_detail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.regmoraes.bakingapp.R;
import com.regmoraes.bakingapp.data.model.Step;

import java.util.List;

public final class StepDetailPageAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Step> steps;

    public StepDetailPageAdapter(Context context, FragmentManager fm, List<Step> steps) {
        super(fm);
        this.context = context;
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int i) {

        return StepDetailFragment.newInstance(steps.get(i));
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return String.format(context.getString(R.string.step_number), steps.get(position).getId());
    }
}
