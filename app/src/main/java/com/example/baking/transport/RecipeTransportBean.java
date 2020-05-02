package com.example.baking.transport;

import com.example.baking.bean.Ingredient;
import com.example.baking.bean.Step;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class RecipeTransportBean {
    int id;
    String name;
    @SerializedName("ingredients")
    List<Ingredient> ingredients;
    @SerializedName("steps")
    List<Step> steps;
}
