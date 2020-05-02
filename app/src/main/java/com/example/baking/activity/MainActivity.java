package com.example.baking.activity;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baking.R;
import com.example.baking.RecipeService;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.adapter.RecipeRecyclerViewAdapter;
import com.example.baking.transport.RecipeTransportBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private static Retrofit retrofit = null;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= findViewById(R.id.rv_movies);

        Log.d(this.getClass().getName(), "Create " );
        getRecipes();
    }

    private void getRecipes() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        RecipeService recipeService = retrofit.create(RecipeService.class);
        Call<List<RecipeTransportBean>> call = recipeService.getRecipes();
        call.enqueue(new Callback<List<RecipeTransportBean>>() {
            @Override
            public void onResponse(Call<List<RecipeTransportBean>> call, Response<List<RecipeTransportBean>> response) {
                List<RecipeTransportBean> recipes = response.body();

                Log.d(this.getClass().getName(), "recipes "+recipes.size() );
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
        List<RecipePresentationBean> recipeList = new ArrayList<>();
        for(RecipeTransportBean transportbean:recipes){
            RecipePresentationBean recipePresentationBean = new RecipePresentationBean();
            recipePresentationBean.setName(transportbean.getName());
            recipeList.add(recipePresentationBean);
        }
        RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter(this, recipeList);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, 1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
    }
}