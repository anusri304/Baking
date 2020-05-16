package com.example.baking.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.baking.domain.Recipe;

@Dao
public interface RecipeDao {

    @Insert
    void insertRecipe(Recipe recipe);

    @Query("SELECT * from Recipe where id=1")
    Recipe getFavouriteRecipe();
}
