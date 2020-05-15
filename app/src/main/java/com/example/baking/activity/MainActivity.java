package com.example.baking.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baking.R;
import com.example.baking.RecipeService;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.adapter.RecipeRecyclerViewAdapter;
import com.example.baking.transport.RecipeTransportBean;
import com.example.baking.utils.ApplicationConstants;
import com.example.baking.utils.BakingUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeRecyclerViewAdapter.ListItemClickListener {

    private static Retrofit retrofit = null;
    RecyclerView recyclerView;
    List<RecipePresentationBean> recipeList = new ArrayList<>();
    //TODO: Remove logs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_recipe);

        Log.d(this.getClass().getName(), "Create ");
        getRecipes();
    }

    private void getRecipes() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApplicationConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        RecipeService recipeService = retrofit.create(RecipeService.class);
        Call<List<RecipeTransportBean>> call = recipeService.getRecipes();
        call.enqueue(new Callback<List<RecipeTransportBean>>() {
            @Override
            public void onResponse(Call<List<RecipeTransportBean>> call, Response<List<RecipeTransportBean>> response) {
                List<RecipeTransportBean> recipes = response.body();

                Log.d(this.getClass().getName(), "recipes " + recipes.size());
                setData(recipes);
                // recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                Log.d(this.getClass().getName(), "Number of recipes received: " + recipes.size());
            }

            @Override
            public void onFailure(Call<List<RecipeTransportBean>> call, Throwable throwable) {
                Log.e(this.getClass().getName(), throwable.toString());
            }
        });
    }

    private void setData(List<RecipeTransportBean> recipes) {
        recipeList.clear();
        for (RecipeTransportBean transportbean : recipes) {
            RecipePresentationBean recipePresentationBean = new RecipePresentationBean();
            recipePresentationBean.setName(transportbean.getName());
            recipePresentationBean.setImage(transportbean.getImage());
            recipePresentationBean.setServing(ApplicationConstants.SERVING.concat(transportbean.getServing()));
            recipePresentationBean.setIngredients(transportbean.getIngredients());
            recipePresentationBean.setSteps(transportbean.getSteps());
            recipeList.add(recipePresentationBean);
        }
        RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter(this, recipeList, this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, BakingUtils.getSpan(MainActivity.this));
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
     //   Log.d("Anandhi", "clicked");
        RecipePresentationBean recipePresentationBean = recipeList.get(clickedItemIndex);
        Intent intent = new Intent(this, RecipeStepActivity.class);
        intent.putExtra(ApplicationConstants.RECIPE, recipePresentationBean);
        startActivity(intent);
    }





}
