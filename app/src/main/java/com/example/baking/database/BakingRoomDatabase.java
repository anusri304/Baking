package com.example.baking.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.baking.dao.RecipeDao;
import com.example.baking.domain.Recipe;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
public abstract class BakingRoomDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();
    private static final Object LOCK = new Object();

    private static volatile BakingRoomDatabase bakingRoomDatabase;
    private static final String DATABASE_NAME = "baking_database";

    public static BakingRoomDatabase getDatabase(final Context context) {
        if (bakingRoomDatabase == null) {
            synchronized (LOCK) {
                if (bakingRoomDatabase == null) {
                    bakingRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            BakingRoomDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return bakingRoomDatabase;
    }

}
