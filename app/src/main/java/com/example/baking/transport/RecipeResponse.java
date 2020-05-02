package com.example.baking.transport;

import lombok.Data;

import java.util.List;
@Data
public class RecipeResponse {
    List<RecipeTransportBean>   recipeTransportBeans;
}
