package com.example.chef;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.chef.database.AppDatabase;
import com.example.chef.database.Favorite;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget extends AppWidgetProvider {
    private static AppDatabase mDb;
    String TEXT;

    AppWidgetManager appWidgetManager;
    int[] appWidgetIds;




    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        mDb = AppDatabase.getInstance(context);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);

        if(TEXT == null){
            TEXT = "You have to choose a recipe";
        }
        Log.d(TAG, "asdf;lksajdf;lasjd;lkfsajdl;f" + TEXT);

        views.setTextViewText(R.id.appwidget_text, TEXT);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        this.appWidgetManager = appWidgetManager;
        this.appWidgetIds = appWidgetIds;
        new TMDBQueryTask().execute(context);

    }

    public void theUpdater(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }




    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public class TMDBQueryTask extends AsyncTask<Context, Void, String> {
        Context context;
        @Override
        protected String doInBackground(Context... params) {
            context = params[0];
            mDb = AppDatabase.getInstance(context);
            Favorite IngredientsText = mDb.favoriteDao().loadFirstFavorite();

            return IngredientsText.getIngredients();
        }

        //after getting the api request results, we override onPostExecute function:
        @Override
        protected void onPostExecute(String theText) {
            if (theText != null && !theText.equals("")) {
                Log.d(TAG, "this text exist:\n" + theText);
                TEXT =theText;
            }


            theUpdater(context, appWidgetManager,appWidgetIds);

        }
    }

}


