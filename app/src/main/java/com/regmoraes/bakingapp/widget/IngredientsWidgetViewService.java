package com.regmoraes.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsRemoteViewFactory(this.getApplicationContext());
    }
}


