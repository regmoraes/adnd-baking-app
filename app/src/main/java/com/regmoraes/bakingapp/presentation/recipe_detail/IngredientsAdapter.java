package com.regmoraes.bakingapp.presentation.recipe_detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.regmoraes.bakingapp.data.model.Ingredient;
import com.regmoraes.bakingapp.databinding.AdapterIngredientListItemBinding;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<Ingredient> ingredients;
    
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        AdapterIngredientListItemBinding itemBinding = AdapterIngredientListItemBinding.inflate(inflater, parent, false);

        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Ingredient ingredient = ingredients.get(position);

        if(ingredient != null) holder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {

        if(ingredients != null) {
            return ingredients.size();
        } else {
            return 0;
        }
    }

    void setData(List<Ingredient> ingredients) {

        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterIngredientListItemBinding itemBinding;

        ViewHolder(AdapterIngredientListItemBinding itemBinding) {
            super(itemBinding.getRoot());

            this.itemBinding = itemBinding;
        }

        void bind(Ingredient ingredient) {
            itemBinding.setIngredient(ingredient);
            itemBinding.executePendingBindings();
        }
    }
}
