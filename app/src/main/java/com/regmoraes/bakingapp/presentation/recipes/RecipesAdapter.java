package com.regmoraes.bakingapp.presentation.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regmoraes.bakingapp.data.model.Recipe;
import com.regmoraes.bakingapp.R;
import com.regmoraes.bakingapp.databinding.AdapterRecipeListItemBinding;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipe> recipes;
    private OnItemClickListener listener;

    public RecipesAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        AdapterRecipeListItemBinding itemBinding = AdapterRecipeListItemBinding.inflate(inflater, parent, false);

        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Recipe recipe = recipes.get(position);

        if(recipe != null) holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {

        if(recipes != null) {
            return recipes.size();
        } else {
            return 0;
        }
    }

    void setData(List<Recipe> recipes) {

        this.recipes = recipes;
        notifyDataSetChanged();
    }

    interface OnItemClickListener {

        void onRecipeClicked(Recipe recipe);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private AdapterRecipeListItemBinding itemBinding;

        ViewHolder(AdapterRecipeListItemBinding itemBinding) {
            super(itemBinding.getRoot());

            this.itemBinding = itemBinding;
            this.itemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(view.getId() == R.id.cardView_recipe) {

                Recipe recipe = recipes.get(getAdapterPosition());

                listener.onRecipeClicked(recipe);
            }
        }

        void bind(Recipe recipe) {
            itemBinding.setRecipe(recipe);
            itemBinding.executePendingBindings();
        }
    }
}
