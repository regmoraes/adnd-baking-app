package com.regmoraes.bakingapp.presentation.step_detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.regmoraes.bakingapp.data.model.Step;
import com.regmoraes.bakingapp.databinding.FragmentStepDetailBinding;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class StepDetailFragment extends Fragment {

    public static final String EXTRA_PLAYER_TIME_POSITION = "player-time-position";

    private FragmentStepDetailBinding viewBinding;

    private ExoPlayer exoPlayer;
    private long playerTimePosition;

    private Step stepExtra;

    public static StepDetailFragment newInstance(Step step) {

        Bundle args = new Bundle();
        args.putParcelable(Step.class.getSimpleName(), step);

        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            stepExtra = getArguments().getParcelable(Step.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewBinding = FragmentStepDetailBinding.inflate(inflater, container, false);

        viewBinding.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && stepExtra != null) {
            viewBinding.setStep(stepExtra);
        }

        if(savedInstanceState != null){
            playerTimePosition = savedInstanceState.getLong(EXTRA_PLAYER_TIME_POSITION, 0L);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23 && stepExtra != null) {
            setUpPlayer(stepExtra.getVideoURL());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || exoPlayer == null) && stepExtra != null) {
            setUpPlayer(stepExtra.getVideoURL());
        }
    }

    private void setUpPlayer(String stepUrlPath) {

        if(stepUrlPath != null && !stepUrlPath.isEmpty()) {

            TrackSelector trackSelector = new DefaultTrackSelector();

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            exoPlayer.prepare(buildMediaSource(Uri.parse(stepUrlPath)), false, true);
            exoPlayer.seekTo(playerTimePosition);
            exoPlayer.setPlayWhenReady(true);

            viewBinding.playerView.setPlayer(exoPlayer);

        } else {
            viewBinding.playerView.setVisibility(View.GONE);
        }
    }


    private ExtractorMediaSource buildMediaSource(Uri uri) {

        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(getActivity(), "BakingApp"))
                .createMediaSource(uri);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(exoPlayer != null) {
            outState.putLong(EXTRA_PLAYER_TIME_POSITION, exoPlayer.getCurrentPosition());
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playerTimePosition = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
        }
    }
}
