package com.regmoraes.bakingapp.application.di.component;

import com.regmoraes.bakingapp.application.di.module.AndroidModule;
import com.regmoraes.bakingapp.application.di.module.DomainModule;
import com.regmoraes.bakingapp.application.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Singleton
@Component(modules = {AndroidModule.class, NetworkModule.class, DomainModule.class})
public interface ApplicationComponent {

    PresentationComponents.Recipes recipesComponent();

    PresentationComponents.RecipeDetail recipeDetailComponent();

    PresentationComponents.StepDetailActivityComponent stepDetailActivityComponent();

    WidgetComponent widgetComponent();
}
