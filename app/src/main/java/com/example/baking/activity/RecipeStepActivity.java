package com.example.baking.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.example.baking.R;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.bean.Step;
import com.example.baking.fragment.RecipeInstructionFragment;
import com.example.baking.utils.ApplicationConstants;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class RecipeStepActivity extends AppCompatActivity {
    RecipePresentationBean recipePresentationBean = null;
    Step stepClicked = null;
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private TextView instructionTextView;
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
        if (getIntent() != null) {
            recipePresentationBean = getIntent().getParcelableExtra(ApplicationConstants.RECIPE);
            Log.d("Anandhi", String.valueOf(recipePresentationBean.getSteps().size()));
            setTitle(recipePresentationBean.getName());
        }
        if (findViewById(R.id.recipe_instruction_linear_layout) != null) {
            mTwoPane = true;
            RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment(mTwoPane);

            FragmentManager fragmentManager = getSupportFragmentManager();
            recipeInstructionFragment.setRecipePresentationBean(recipePresentationBean);
            recipeInstructionFragment.setSelectedIndex(0);
            Log.d("pass step","step"+recipePresentationBean.getSteps().get(0));
            recipeInstructionFragment.setStep(recipePresentationBean.getSteps().get(0));
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_instruction_fragment, recipeInstructionFragment)
                    .commit();
            //recipeInstructionFragment.hideButtons();
        }
    }
}

