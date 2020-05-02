package com.example.baking.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Step {
    int id;
    String shortDescription;
    @SerializedName("description")
    String instruction;
    String videoURL;
    String thumbnailURL;
}
