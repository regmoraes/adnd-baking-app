package com.regmoraes.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.regmoraes.bakingapp.R;
import com.regmoraes.bakingapp.application.BakingApp;
import com.regmoraes.bakingapp.application.di.component.WidgetComponent;
import com.regmoraes.bakingapp.data.model.Ingredient;
import com.regmoraes.bakingapp.data.model.Recipe;
import com.regmoraes.bakingapp.domain.RecipesWidgetManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private WidgetComponent widgetComponent;

    private Recipe recipe;
    private List<Ingredient> ingredients;

    @Inject
    RecipesWidgetManager recipesWidgetManager;

    public IngredientsRemoteViewFactory(Context applicationContext) {
        mContext = applicationContext;

        widgetComponent = ((BakingApp) mContext.getApplicationContext())
                .getApplicationComponent().widgetComponent();

        widgetComponent.inject(this);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        recipe = recipesWidgetManager.loadRecipe();
        ingredients = recipesWidgetManager.loadRecipe().getIngredients();
    }

    @Override
    public int getCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION || ingredients == null ||
                ingredients.isEmpty()) {
            return null;
        }

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.wigdet_recipe_ingredient_item);

        Ingredient ingredient = ingredients.get(position);

        String title = String.format(mContext.getString(R.string.ingredient_title),
                ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getName());

        views.setTextViewText(R.id.textView_ingredient_title, title);

        Intent fillIntent = new Intent();
        fillIntent.putExtra(Recipe.class.getSimpleName(), recipe);
        views.setOnClickFillInIntent(R.id.textView_ingredient_title, fillIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDestroy() {
        widgetComponent = null;
    }
}


