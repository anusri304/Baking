package com.example.baking.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.baking.dao.RecipeDao;
import com.example.baking.database.BakingRoomDatabase;
import com.example.baking.domain.Recipe;

public class RecipeRepository {
    private RecipeDao recipeDao;

    public RecipeRepository(Application application) {
        BakingRoomDatabase db = BakingRoomDatabase.getDatabase(application);
        recipeDao = db.recipeDao();
    }

    public Recipe getFavouriteRecipe() {
        return recipeDao.getFavouriteRecipe();
    }

    public void insertRecipe(Recipe recipe) {
        recipeDao.insertRecipe(recipe);
    }
}
