package com.example.baking.service;

import com.example.baking.transport.RecipeTransportBean;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface RecipeService {
    @GET("baking.json")
    Call<List<RecipeTransportBean>> getRecipes();
}
