package com.example.baking.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baking.R;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.adapter.RecipeRecyclerViewAdapter;
import com.example.baking.bean.Ingredient;
import com.example.baking.domain.Recipe;
import com.example.baking.executors.AppExecutors;
import com.example.baking.repository.RecipeRepository;
import com.example.baking.service.RecipeService;
import com.example.baking.transport.RecipeTransportBean;
import com.example.baking.utils.ApplicationConstants;
import com.example.baking.utils.BakingUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.test.espresso.idling.*;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeRecyclerViewAdapter.ListItemClickListener {

    private static Retrofit retrofit = null;
    RecyclerView recyclerView;
    List<RecipePresentationBean> recipeList = new ArrayList<>();
    String ingredientDesc = "";
    StringBuffer ingredientBuffer = new StringBuffer();
    CountingIdlingResource  espressoTestIdlingResource = new CountingIdlingResource(ApplicationConstants.NETWORK_CALL);

    // RecipeViewModel recipeViewModel;
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
        espressoTestIdlingResource.increment();
        RecipeService recipeService = retrofit.create(RecipeService.class);
        Call<List<RecipeTransportBean>> call = recipeService.getRecipes();
        call.enqueue(new Callback<List<RecipeTransportBean>>() {
            @Override
            public void onResponse(Call<List<RecipeTransportBean>> call, Response<List<RecipeTransportBean>> response) {
                List<RecipeTransportBean> recipes = response.body();
                espressoTestIdlingResource.decrement();

                Log.d(this.getClass().getName(), "recipes " + recipes.size());
                setData(recipes);
                Log.d(this.getClass().getName(), "Number of recipes received: " + recipes.size());
            }

            @Override
            public void onFailure(Call<List<RecipeTransportBean>> call, Throwable throwable) {
                Log.e(this.getClass().getName(), throwable.toString());
                espressoTestIdlingResource.decrement();
            }
        });
    }

    private void setData(List<RecipeTransportBean> recipes) {
        recipeList.clear();
        for (RecipeTransportBean transportbean : recipes) {
            RecipePresentationBean recipePresentationBean = new RecipePresentationBean();
            recipePresentationBean.setId(transportbean.getId());
            recipePresentationBean.setName(transportbean.getName());
            recipePresentationBean.setImage(transportbean.getImage());
            recipePresentationBean.setServing(ApplicationConstants.SERVING.concat(transportbean.getServing()));
            recipePresentationBean.setIngredients(transportbean.getIngredients());
            recipePresentationBean.setSteps(transportbean.getSteps());
            insertRecipe(recipePresentationBean);
            recipeList.add(recipePresentationBean);
        }
        RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter(this, recipeList, this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, BakingUtils.getSpan(MainActivity.this));
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        RecipePresentationBean recipePresentationBean = recipeList.get(clickedItemIndex);
        Intent intent = new Intent(this, RecipeStepActivity.class);
        intent.putExtra(ApplicationConstants.RECIPE, recipePresentationBean);
        startActivity(intent);
    }


    private void insertRecipe(RecipePresentationBean recipePresentationBean) {
        List<Ingredient> ingredients = recipePresentationBean.getIngredients();
        String ingredientDesc="";
        for (Ingredient ingredient : ingredients) {
            ingredientDesc = ingredientDesc + BakingUtils.formatNumber(ingredient.getQuantity()) + " " + ingredient.getMeasure() + " " + ingredient.getName() + "\n";

        }
        Recipe recipe = new Recipe();
        recipe.setId(recipePresentationBean.getId());
        recipe.setName(recipePresentationBean.getName());
        recipe.setServings(recipePresentationBean.getServing());
        recipe.setIngredients(ingredientDesc);
        AppExecutors.getInstance().diskIO().execute(() -> {
            saveRecipe(recipe);

        });

    }

    private void saveRecipe(Recipe recipe){
        RecipeRepository recipeRepository = new RecipeRepository((Application)getApplicationContext());
        recipeRepository.insertRecipe(recipe);

    }

    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        return espressoTestIdlingResource;
    }


}
