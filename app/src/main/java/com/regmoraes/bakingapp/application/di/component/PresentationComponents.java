package com.regmoraes.bakingapp.application.di.component;

import com.regmoraes.bakingapp.application.di.module.PresentationModule;
import com.regmoraes.bakingapp.application.di.module.WidgetModule;
import com.regmoraes.bakingapp.application.di.scope.PresentationScope;
import com.regmoraes.bakingapp.presentation.recipe_detail.RecipeDetailFragment;
import com.regmoraes.bakingapp.presentation.recipes.RecipesActivity;
import com.regmoraes.bakingapp.presentation.recipes.RecipesFragment;
import com.regmoraes.bakingapp.presentation.step_detail.StepDetailActivity;
import com.regmoraes.bakingapp.presentation.step_detail.StepDetailFragment;

import dagger.Subcomponent;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface PresentationComponents {

    @PresentationScope
    @Subcomponent(modules = PresentationModule.class)
    interface Recipes {

        void inject(RecipesActivity recipesActivity);

        void inject(RecipesFragment recipesFragment);
    }

    @PresentationScope
    @Subcomponent(modules = {PresentationModule.class, WidgetModule.class})
    interface RecipeDetail {

        void inject(RecipeDetailFragment recipeDetailFragment);

        void inject(StepDetailFragment stepDetailFragment);
    }

    @PresentationScope
    @Subcomponent(modules = PresentationModule.class)
    interface StepDetailActivityComponent {

        void inject(StepDetailActivity stepDetailActivity);
    }
}
