package com.example.baking.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.baking.R;
import com.example.baking.activity.bean.RecipePresentationBean;
import com.example.baking.bean.Ingredient;
import com.example.baking.bean.Step;
import com.example.baking.utils.ApplicationConstants;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
        checkOrientation();
        setContentView(R.layout.activity_step_instruction);
        instructionTextView = (TextView) findViewById(R.id.instructionTxtView);
        if (getIntent() != null) {
            recipePresentationBean = getIntent().getParcelableExtra(ApplicationConstants.RECIPE);
            totalSteps = recipePresentationBean.getSteps().size();
            selectedIndex = getIntent().getIntExtra(ApplicationConstants.SELECTED_INDEX, 0);
            step = recipePresentationBean.getSteps().get(selectedIndex);
            setTitle(recipePresentationBean.getName());
            initButton();
        } else {
            closeOnError();
        }
        initViews(step);


    }

    private void checkOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape

        }
    }

    private void initViews(Step step) {
        mPlayerView = (PlayerView) findViewById(R.id.playerView);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            mPlayerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        }

        if (step.getVideoURL() == null || step.getVideoURL().equalsIgnoreCase("")) {
            initImageView();

        } else {
           // initThumbNailPic(step);
            Log.d("Anandhi",step.getVideoURL());
            initializePlayer(Uri.parse(step.getVideoURL()));
        }
        initInstructionView(step);
    }

    private void initInstructionView(Step step) {
        instructionTextView.setText(step.getInstruction());
    }

    private void initThumbNailPic(Step step) {
        Bitmap bitmap = null;
        Drawable drawable = null;
        if (!step.getThumbnailURL().equalsIgnoreCase("")) {
            URL url = null;
            try {
                url = new URL(step.getThumbnailURL());
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_cake_black_100dp);
            drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
        }
        mPlayerView.setDefaultArtwork(drawable);

    }

    private void initImageView() {
        mPlayerView.setVisibility(View.INVISIBLE);
        relativeLayout = findViewById(R.id.relativeLayout);
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.errorvideo);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 400));
        imageView.setVisibility(View.VISIBLE);
        relativeLayout.addView(imageView);
    }

    private void initButton() {
        List<Step> steps = recipePresentationBean.getSteps();
        Button prevButton = findViewById(R.id.buttonPrev);
        Button nextButton = findViewById(R.id.buttonNext);
        if (selectedIndex == 0) {
            prevButton.setEnabled(false);
        } else if (selectedIndex == totalSteps - 1) {
            nextButton.setEnabled(false);
        }
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mExoPlayer!=null) {
                    mExoPlayer.stop();
                }
                launchIntent(selectedIndex-1);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mExoPlayer!=null) {
                    mExoPlayer.stop();
                }
                launchIntent(selectedIndex+1);
            }
        });
    }

    private void launchIntent(int selectedStep) {
        Intent intent = new Intent(RecipeInstructionActivity.this, RecipeInstructionActivity.class);
        intent.putExtra(ApplicationConstants.RECIPE, recipePresentationBean);
        intent.putExtra(ApplicationConstants.STEP, recipePresentationBean.getSteps().get(selectedStep));
        intent.putExtra(ApplicationConstants.TOTAL_STEPS,recipePresentationBean.getSteps().size());
        intent.putExtra(ApplicationConstants.SELECTED_INDEX,selectedStep);
        finish();
        startActivity(intent);
    }


    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            Log.d("Anandhi","if block");
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        }

            // Prepare the MediaSource.
            mPlayerView.setPlayer(mExoPlayer);
            Log.d("Anandhi","else block");
            String userAgent = Util.getUserAgent(this, getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.recipe_instruction_error_message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
        mExoPlayer = null;
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }
}
