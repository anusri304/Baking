package com.example.baking.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import com.example.baking.domain.Recipe;
import com.example.baking.repository.RecipeRepository;
import com.example.baking.utils.MyApplication;
import com.example.baking.widget.BakingAppWidgetProvider;

public class DisplayIngredientService extends IntentService implements LifecycleObserver {
    public static final String ACTION_DISPLAY_INGREDIENT_WIDGETS = "com.example.android.baking.action.display_ingredient_widgets";
    RecipeRepository recipeRepository;
    //RecipeViewModel recipeViewModel;


    public DisplayIngredientService() {
        super("DisplayIngredientService");
//        RecipeViewModelFactory factory = new RecipeViewModelFactory(MyApplication.getInstance());
//        recipeViewModel = new RecipeViewModel(MyApplication.getInstance());
        recipeRepository = new RecipeRepository(MyApplication.getInstance());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DISPLAY_INGREDIENT_WIDGETS.equals(action)) {
                handleActionDisplayIngredients();
            }
        }

    }

    private void handleActionDisplayIngredients() {
       Recipe recipe = recipeRepository.getFavouriteRecipe();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        BakingAppWidgetProvider.updateIngredientWidget(this,appWidgetManager,recipe.getIngredients(),appWidgetIds);

    }

    public static void startActionDisplayIngredientWidgets(Context context) {
        Intent intent = new Intent(context, DisplayIngredientService.class);
        intent.setAction(ACTION_DISPLAY_INGREDIENT_WIDGETS);
        context.startService(intent);
    }

}
