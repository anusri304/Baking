package com.example.baking.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.example.baking.R;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.bean.Step;
import com.example.baking.fragment.RecipeInstructionFragment;
import com.example.baking.utils.ApplicationConstants;

public class RecipeInstructionActivity extends AppCompatActivity {
    Step step;
    RelativeLayout relativeLayout;
    int totalSteps, selectedIndex;
    RecipePresentationBean recipePresentationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_instruction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null) {
            recipePresentationBean = getIntent().getParcelableExtra(ApplicationConstants.RECIPE);
            totalSteps = recipePresentationBean.getSteps().size();
            selectedIndex = getIntent().getIntExtra(ApplicationConstants.SELECTED_INDEX, 0);
            step = recipePresentationBean.getSteps().get(selectedIndex);
            setTitle(recipePresentationBean.getName());

            RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment(false);
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_instruction_fragment, recipeInstructionFragment)
                    .commit();

        } else {
            closeOnError();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.recipe_instruction_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
