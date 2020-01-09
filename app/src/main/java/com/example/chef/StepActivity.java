package com.example.chef;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class StepActivity extends AppCompatActivity {
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    TextView mStepTitle;
    TextView mDescription;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        mStepTitle = findViewById(R.id.step_title_tv);
        mDescription = findViewById(R.id.Description_tv);
        simpleExoPlayerView = findViewById(R.id.playerView);

        Step step = (Step) getIntent().getSerializableExtra("step");

        mStepTitle.setText("Step " + (step.getId()+1) + ": " + step.getShortDescription());

        mDescription.setText(step.getDescription());

        String videoUrl = step.getVideoURL();
        if(videoUrl==null || videoUrl=="") {
            simpleExoPlayerView.setVisibility(View.GONE);
        }else{
            initializePlayer(videoUrl);
        }


    }

    private void initializePlayer(String videoUrl){

        //noinspection deprecation
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                this,
                new DefaultTrackSelector(), new DefaultLoadControl());

        Uri uri = Uri.parse(videoUrl);

        //noinspection deprecation
        ExtractorMediaSource audioSource = new ExtractorMediaSource(
                uri,
                new DefaultDataSourceFactory(this, "MyExoplayer"),
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
