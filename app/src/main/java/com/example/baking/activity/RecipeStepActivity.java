package com.example.baking.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.example.baking.R;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.bean.Step;
import com.example.baking.fragment.RecipeInstructionFragment;
import com.example.baking.utils.ApplicationConstants;

public class RecipeStepActivity extends AppCompatActivity {
    RecipePresentationBean recipePresentationBean = null;
    Step stepClicked = null;
    RelativeLayout relativeLayout;

    public boolean ismTwoPane() {
        return mTwoPane;
    }

    public void setmTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

    public boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_desc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent() != null) {
            recipePresentationBean = getIntent().getParcelableExtra(ApplicationConstants.RECIPE);
            setTitle(recipePresentationBean.getName());
        }
        if (findViewById(R.id.recipe_instruction_linear_layout) != null) {
            mTwoPane = true;
            RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment(mTwoPane);

            FragmentManager fragmentManager = getSupportFragmentManager();
            recipeInstructionFragment.setRecipePresentationBean(recipePresentationBean);
            recipeInstructionFragment.setSelectedIndex(0);
            recipeInstructionFragment.setStep(recipePresentationBean.getSteps().get(0));
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_instruction_fragment, recipeInstructionFragment)
                    .commit();
        }
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

