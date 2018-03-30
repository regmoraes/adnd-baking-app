package com.regmoraes.bakingapp.presentation.recipe_detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regmoraes.bakingapp.data.model.Step;
import com.regmoraes.bakingapp.databinding.AdapterStepListItemBinding;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<Step> steps;
    private OnStepClickListener listener;

    public StepsAdapter(OnStepClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        AdapterStepListItemBinding itemBinding = AdapterStepListItemBinding.inflate(inflater, parent, false);

        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Step step = steps.get(position);

        if(step != null) holder.bind(steps.get(position));
    }

    @Override
    public int getItemCount() {

        if(steps != null) {
            return steps.size();
        } else {
            return 0;
        }
    }

    void setData(List<Step> steps) {

        this.steps = steps;
        notifyDataSetChanged();
    }

    interface OnStepClickListener {

        void onStepClicked(Step step);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterStepListItemBinding itemBinding;

        ViewHolder(AdapterStepListItemBinding itemBinding) {
            super(itemBinding.getRoot());

            this.itemBinding = itemBinding;
            this.itemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Step step = steps.get(getAdapterPosition());

            listener.onStepClicked(step);
        }

        void bind(Step step) {
            itemBinding.setStep(step);
            itemBinding.executePendingBindings();
        }
    }
}
