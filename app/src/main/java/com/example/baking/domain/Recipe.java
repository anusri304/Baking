package com.example.baking.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
public class Recipe {
    @PrimaryKey
    public int id;
    private String name;
    private String ingredients;
    private String servings;
}
