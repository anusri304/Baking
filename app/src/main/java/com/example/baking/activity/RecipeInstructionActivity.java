package com.example.baking.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.example.baking.R;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.bean.Step;
import com.example.baking.fragment.RecipeInstructionFragment;
import com.example.baking.utils.ApplicationConstants;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class RecipeInstructionActivity extends AppCompatActivity {
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private TextView instructionTextView;
    Step step;
    RelativeLayout relativeLayout;
    int totalSteps, selectedIndex;
    RecipePresentationBean recipePresentationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_instruction);

        if (getIntent() != null) {
            Log.d("Anandhi","RecipeInstructionActivity");
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


}
