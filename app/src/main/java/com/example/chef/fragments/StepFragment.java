package com.example.chef.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.chef.R;
import com.example.chef.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;

import com.google.android.exoplayer2.ExoPlayerFactory;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class StepFragment extends Fragment {
    Step step;

    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    TextView mStepTitle;
    TextView mDescription;
    Context globalContext;

    public StepFragment(Step step){
        this.step = step;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);

        globalContext = container.getContext();
        mStepTitle = rootView.findViewById(R.id.step_title_tv);
        mDescription = rootView.findViewById(R.id.Description_tv);
        simpleExoPlayerView = rootView.findViewById(R.id.playerView);

        mStepTitle.setText("Step " + (step.getId()+1) + ": " + step.getShortDescription());

        mDescription.setText(step.getDescription());

        String videoUrl = step.getVideoURL();
        if(videoUrl==null || videoUrl=="") {
            simpleExoPlayerView.setVisibility(View.GONE);
        }else{
            initializePlayer(videoUrl);
        }

        return rootView;
    }

    private void initializePlayer(String videoUrl){

        //noinspection deprecation
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                globalContext,
                new DefaultTrackSelector(), new DefaultLoadControl());

        Uri uri = Uri.parse(videoUrl);

        //noinspection deprecation
        ExtractorMediaSource audioSource = new ExtractorMediaSource(
                uri,
                new DefaultDataSourceFactory(globalContext, "MyExoplayer"),
                new DefaultExtractorsFactory(),
                null,
                null
        );

        simpleExoPlayer.prepare(audioSource);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        simpleExoPlayer.setPlayWhenReady(true);
    }
}
