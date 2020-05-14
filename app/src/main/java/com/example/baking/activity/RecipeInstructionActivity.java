package com.example.baking.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.baking.R;
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
    String recipeName;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_instruction);
        mPlayerView = (PlayerView) findViewById(R.id.playerView);
        Bitmap bitmap = null;
        Drawable drawable = null;
        if (getIntent() != null) {
            step = getIntent().getParcelableExtra(ApplicationConstants.STEP);
            recipeName = getIntent().getParcelableExtra(ApplicationConstants.RECIPE_NAME);
            setTitle(recipeName);
        } else {
            closeOnError();
        }

        if (step.getVideoURL() == null || step.getVideoURL().equalsIgnoreCase("")) {
            mPlayerView.setVisibility(View.INVISIBLE);
            relativeLayout = findViewById(R.id.relativeLayout);
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(R.drawable.errorvideo);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 400));
            imageView.setVisibility(View.VISIBLE);
            relativeLayout.addView(imageView);
        } else {
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
            initializePlayer(Uri.parse(step.getVideoURL()));
        }
        instructionTextView = (TextView) findViewById(R.id.instructionTxtView);

        instructionTextView.setText(step.getInstruction());
    }


    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
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
