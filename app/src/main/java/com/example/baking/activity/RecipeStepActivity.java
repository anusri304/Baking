package com.example.baking.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.baking.R;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.bean.Step;
import com.example.baking.utils.ApplicationConstants;

public class RecipeStepActivity extends AppCompatActivity {
    RecipePresentationBean recipePresentationBean =null;
    Step stepClicked= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_desc);
        if (getIntent() != null) {
            recipePresentationBean = getIntent().getParcelableExtra(ApplicationConstants.RECIPE);
            setTitle(recipePresentationBean.getName());
        }
    }
    }

