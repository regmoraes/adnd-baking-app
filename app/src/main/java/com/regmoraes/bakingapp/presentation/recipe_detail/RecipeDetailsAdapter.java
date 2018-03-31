package com.regmoraes.bakingapp.presentation.recipe_detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regmoraes.bakingapp.data.model.Ingredient;
import com.regmoraes.bakingapp.data.model.Step;
import com.regmoraes.bakingapp.databinding.AdapterIngredientListItemBinding;
import com.regmoraes.bakingapp.databinding.AdapterStepListHeaderBinding;
import com.regmoraes.bakingapp.databinding.AdapterStepListItemBinding;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class RecipeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Ingredient> ingredients;
    private List<Step> steps;
    private OnStepClickListener listener;

    private int headersCount = 1;

    private final int VIEW_TYPE_INGREDIENT = 1;
    private final int VIEW_TYPE_STEP_LIST_HEADER = 2;
    private final int VIEW_TYPE_STEP = 3;

    public RecipeDetailsAdapter(OnStepClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case VIEW_TYPE_INGREDIENT: {

                AdapterIngredientListItemBinding itemBinding = AdapterIngredientListItemBinding.inflate(inflater, parent, false);
                return new IngredientViewHolder(itemBinding);
            }

            case VIEW_TYPE_STEP: {

                AdapterStepListItemBinding itemBinding = AdapterStepListItemBinding.inflate(inflater, parent, false);
                return new StepViewHolder(itemBinding);
            }

            default: {

                AdapterStepListHeaderBinding itemBinding = AdapterStepListHeaderBinding.inflate(inflater, parent, false);
                return new StepListHeaderViewHolder(itemBinding);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(position < ingredients.size()) {

            Ingredient ingredient = ingredients.get(position);

            if (ingredient != null) ((IngredientViewHolder) holder).bind(ingredient);

            return;
        }

        /* Header item doesn't need binding */

        if(position > ingredients.size()) {

            int stepPosition = position - (ingredients.size() + headersCount);

            Step step = steps.get(stepPosition);

            if (step != null) ((StepViewHolder) holder).bind(step);
        }
    }

    @Override
    public int getItemCount() {

        int totalItems = 0;

        if(ingredients != null) totalItems += ingredients.size();

        if(steps != null) totalItems += steps.size();

        return totalItems + headersCount;
    }

    @Override
    public int getItemViewType(int position) {

        if(position < ingredients.size()) {
            return VIEW_TYPE_INGREDIENT;

        } else if(position == ingredients.size()) {
            return VIEW_TYPE_STEP_LIST_HEADER;

        } else {
            return VIEW_TYPE_STEP;
        }
    }

    void setData(List<Ingredient> ingredients, List<Step> steps) {

        this.ingredients = ingredients;
        this.steps = steps;
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        private AdapterIngredientListItemBinding itemBinding;

        IngredientViewHolder(AdapterIngredientListItemBinding itemBinding) {
            super(itemBinding.getRoot());

            this.itemBinding = itemBinding;
        }

        void bind(Ingredient ingredient) {
            itemBinding.setIngredient(ingredient);
            itemBinding.executePendingBindings();
        }
    }

    class StepListHeaderViewHolder extends RecyclerView.ViewHolder {

        StepListHeaderViewHolder(AdapterStepListHeaderBinding itemBinding) {
            super(itemBinding.getRoot());
        }
    }


    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterStepListItemBinding itemBinding;

        StepViewHolder(AdapterStepListItemBinding itemBinding) {
            super(itemBinding.getRoot());

            this.itemBinding = itemBinding;
            this.itemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int stepPosition = getAdapterPosition() - (ingredients.size() + headersCount);

            Step step = steps.get(stepPosition);

            listener.onStepClicked(step);
        }

        void bind(Step step) {
            itemBinding.setStep(step);
            itemBinding.executePendingBindings();
        }
    }

    interface OnStepClickListener {

        void onStepClicked(Step step);
    }

}
