package com.example.baking.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.baking.R;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.fragment.MasterListFragment;

import java.util.ArrayList;

public class RecipeStepActivity extends AppCompatActivity {
    RecipePresentationBean recipePresentationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_desc);
    }
}
