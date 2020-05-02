package com.example.baking;

import com.example.baking.transport.RecipeResponse;
import com.example.baking.transport.RecipeTransportBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface RecipeService {
    @GET("baking.json")
    Call<List<RecipeTransportBean>> getRecipes();
}
