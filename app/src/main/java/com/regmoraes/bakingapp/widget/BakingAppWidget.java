package com.regmoraes.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.regmoraes.bakingapp.R;
import com.regmoraes.bakingapp.application.BakingApp;
import com.regmoraes.bakingapp.application.di.component.WidgetComponent;
import com.regmoraes.bakingapp.data.model.Recipe;
import com.regmoraes.bakingapp.domain.RecipesWidgetManager;
import com.regmoraes.bakingapp.presentation.recipe_detail.RecipeMasterDetailActivity;

import javax.inject.Inject;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    @Inject RecipesWidgetManager recipesWidgetManager;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        WidgetComponent widgetComponent =
                ((BakingApp) context.getApplicationContext()).getApplicationComponent().widgetComponent();
        widgetComponent.inject(this);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipesWidgetManager);
        }
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, RecipesWidgetManager recipesWidgetManager) {

        RemoteViews views = getIngredientsListRemoteView(context);

        Recipe recipe = recipesWidgetManager.loadRecipe();

        if(recipe != null) {
            views.setTextViewText(R.id.appwidget_recipe_title, recipe.getName());
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateWidget(Context context) {

        Intent intent = new Intent(context, BakingAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, BakingAppWidget.class);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        context.sendBroadcast(intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_listView_ingredients);
    }

    private static RemoteViews getIngredientsListRemoteView(Context context) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredients);

        Intent intent = new Intent(context, IngredientsWidgetViewService.class);
        views.setRemoteAdapter(R.id.appwidget_listView_ingredients, intent);
        views.setEmptyView(R.id.appwidget_listView_ingredients, R.id.appwidget_empty_view);

        Intent bakingAppIntent = new Intent(context, RecipeMasterDetailActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, bakingAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.appwidget_listView_ingredients, pendingIntent);

        return views;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

