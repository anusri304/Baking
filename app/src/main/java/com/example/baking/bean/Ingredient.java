package com.example.baking.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Ingredient {
    double quantity;
    String measure;
    @SerializedName("ingredient")
    String name;
}
