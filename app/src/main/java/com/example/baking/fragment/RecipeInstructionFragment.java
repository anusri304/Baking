package com.example.baking.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.baking.R;
import com.example.baking.activity.RecipeInstructionActivity;
import com.example.baking.activity.bean.RecipePresentationBean;
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
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.URL;
import java.util.List;

public class RecipeInstructionFragment extends Fragment {
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private TextView instructionTextView;

    static Step step;
    RelativeLayout relativeLayout;
    int totalSteps;
    int selectedIndex;
    RecipePresentationBean recipePresentationBean;
    Button prevButton, nextButton;
    boolean isTwoPane;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception

    }

    // Mandatory empty constructor
    public RecipeInstructionFragment(boolean isTwoPane) {
        this.isTwoPane = isTwoPane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_instruction, container, false);
        if (getActivity().getIntent() != null) {
            if (!isTwoPane) {
                recipePresentationBean = getActivity().getIntent().getParcelableExtra(ApplicationConstants.RECIPE);
                totalSteps = recipePresentationBean.getSteps().size();
                selectedIndex = getActivity().getIntent().getIntExtra(ApplicationConstants.SELECTED_INDEX, 0);
                step = recipePresentationBean.getSteps().get(selectedIndex);
                initButton(rootView);
            } else {
                hideButtons(rootView);
            }
        } else {
            closeOnError();
        }
        initViews(step, rootView);
        return rootView;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    private void initViews(Step step, View rootView) {
        mPlayerView = (PlayerView) rootView.findViewById(R.id.playerView);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            // In landscape only for phone
             RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) mPlayerView.getLayoutParams();  //View.getLayoutParams() returns the                                                                                                          LayoutParams object of the view
             layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        }

        if (step.getVideoURL() == null || step.getVideoURL().equalsIgnoreCase("")) {
            initImageView(rootView, step);

        } else {
            initThumbNailPic(step);
        }
        initInstructionView(rootView, step);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer(Uri.parse(step.getVideoURL()));
    }

    private void initInstructionView(View rootView, Step step) {
        instructionTextView = (TextView) rootView.findViewById(R.id.instructionTxtView);
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

    private void initImageView(View rootView, Step step) {
        mPlayerView.setVisibility(View.INVISIBLE);
        relativeLayout = rootView.findViewById(R.id.relativeLayout);

        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 600));
        imageView.setVisibility(View.VISIBLE);
        relativeLayout.addView(imageView);

        // If video url is not available try loading the image from the thumbnail URL.
        // If there is no thumbnail URL, load the error video image

        Glide.with(getActivity())
                .load(step.getThumbnailURL())
                .placeholder(R.drawable.errorvideo)
                .error(R.drawable.errorvideo)
                .into(imageView);
    }

    private void initButton(View rootView) {
        List<Step> steps = recipePresentationBean.getSteps();
        prevButton = rootView.findViewById(R.id.buttonPrev);
        nextButton = rootView.findViewById(R.id.buttonNext);
        if (selectedIndex == 0) {
            prevButton.setEnabled(false);
        } else if (selectedIndex == totalSteps - 1) {
            nextButton.setEnabled(false);
        }
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mExoPlayer != null) {
                    mExoPlayer.stop();
                }
                launchIntent(selectedIndex - 1);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mExoPlayer != null) {
                    mExoPlayer.stop();
                }
                launchIntent(selectedIndex + 1);
            }
        });
    }

    private void launchIntent(int selectedStep) {
        Intent intent = new Intent(getActivity(), RecipeInstructionActivity.class);
        intent.putExtra(ApplicationConstants.RECIPE, recipePresentationBean);
        intent.putExtra(ApplicationConstants.STEP, recipePresentationBean.getSteps().get(selectedStep));
        intent.putExtra(ApplicationConstants.TOTAL_STEPS, recipePresentationBean.getSteps().size());
        intent.putExtra(ApplicationConstants.SELECTED_INDEX, selectedStep);
        getActivity().finish();
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
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        }

        // Prepare the MediaSource.
        mPlayerView.setPlayer(mExoPlayer);
        String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);

    }


    private void closeOnError() {
        getActivity().finish();
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



    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    public void hideButtons(View view) {
        prevButton = view.findViewById(R.id.buttonPrev);
        nextButton = view.findViewById(R.id.buttonNext);
        prevButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public void setRecipePresentationBean(RecipePresentationBean recipePresentationBean) {
        this.recipePresentationBean = recipePresentationBean;
    }
}
