package com.regmoraes.bakingapp.application.di.component;

import com.regmoraes.bakingapp.application.di.module.WidgetModule;
import com.regmoraes.bakingapp.widget.BakingAppWidget;
import com.regmoraes.bakingapp.widget.IngredientsRemoteViewFactory;

import dagger.Subcomponent;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Subcomponent(modules = {WidgetModule.class})
public interface WidgetComponent {

    void inject(IngredientsRemoteViewFactory ingredientsRemoteViewFactory);
    void inject(BakingAppWidget bakingAppWidget);
}
