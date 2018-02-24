package com.example.lenovo.final_bakingapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Created by Lenovo on 10/11/2017.
 */

public class Steps_Details_Frag extends Fragment implements ExoPlayer.EventListener, Full_Async_task_and_Main_Detail_Exo_Adapters.Exo_Adapt.onClickListener {

    private Full_Async_task_and_Main_Detail_Exo_Adapters.Exo_Adapt adapt;
    private RecyclerView rec;
    private Bak_Attr stepsAttr;
    private ArrayList<Bak_Attr> stepsList;

    private OnFragmentInteractionListener B_Listen;
    private int id;
    private Boolean tab;


    private SimpleExoPlayerView exo_player;
    private SimpleExoPlayer play;
    private MediaSessionCompat media;
    private PlaybackStateCompat.Builder state;
    private ImageView img_view;
    private long pos=0;


    public Steps_Details_Frag() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        int Activity = 0;

        try {
            if (getActivity().getClass().getSimpleName().equals(Steps_Details_Activ.class.getSimpleName()) || getActivity().getClass().getSimpleName().equals(Freg_Details_Activ.class.getSimpleName())) {
                Activity = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Activity == 1 && getActivity().getIntent() != null)
        {


            stepsAttr = new Bak_Attr();

            stepsList = new ArrayList<>();

            stepsList = getActivity().getIntent().getParcelableArrayListExtra("list");

            id = getActivity().getIntent().getExtras().getInt("idx", 0);
            tab = getActivity().getIntent().getExtras().getBoolean("tab");
            stepsAttr = stepsList.get(id);
        } else if (getArguments() != null)
        {

            stepsAttr = new Bak_Attr();

            id = getArguments().getInt("iD");
            pos=getArguments().getLong("iDxe");
            tab = getArguments().getBoolean("tab");
            stepsList = getArguments().getParcelableArrayList("list");
            stepsAttr = stepsList.get(id);
        }
        if(savedInstanceState!=null)
        {
            id = savedInstanceState.getInt("iD");
            pos=savedInstanceState.getLong("iDxe");

        }
        try {
            getActivity().setTitle(stepsAttr.getShortDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.frag_steps_details_layout, container, false);

        if (savedInstanceState != null) {
            id = savedInstanceState.getInt("iD", 0);
            stepsAttr = stepsList.get(id);
        }

        img_view = (ImageView) root.findViewById(R.id.img_view_2);
        rec = (RecyclerView) root.findViewById(R.id.rec);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rec.setHasFixedSize(true);
        rec.setLayoutManager(layoutManager);
        adapt = new Full_Async_task_and_Main_Detail_Exo_Adapters.Exo_Adapt(stepsAttr, getContext(), this);
        rec.setAdapter(adapt);

        exo_player = (SimpleExoPlayerView) root.findViewById(R.id.exo);

        checkSum();
        return root;
    }

    void checkSum() {
        if (!stepsAttr.getVideoURL().equals("")) {
            img_view.setVisibility(View.INVISIBLE);
            exo_player.setVisibility(View.VISIBLE);
            if (!dimension() && !tab) {
                rec.setVisibility(View.VISIBLE);
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            }
            initializeMediaSession();
            configurePlayer();
        } else {

            exo_player.setVisibility(View.VISIBLE);
            img_view.setVisibility(View.INVISIBLE);
            rec.setVisibility(View.VISIBLE);
        }
    }

    void configurePlayer()
    {
        if (play == null)
        {

            TrackSelector trackSelector = new DefaultTrackSelector();

            LoadControl loadControl = new DefaultLoadControl();

            play = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            exo_player.setPlayer(play);
            exo_player.setKeepScreenOn(true);
            play.addListener(this);
            play.prepare(MediaSource(stepsAttr.getVideoURL()));
            play.setPlayWhenReady(true);
            play.seekTo(pos);
        }

    }

    Boolean dimension() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return height > width;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            B_Listen = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionSelectRecipe");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        B_Listen = null;
    }

    @Override
    public void OnClick(int c) {
        if (c == 0) {
            id += 1;
            id %= stepsList.size();
            stepsAttr = stepsList.get(id);
            restExoPlayer(0, false);
            checkSum();
            adapt.changeDataSet(stepsAttr);
        } else {
            id -= id == 0 ? 0 : 1;
            restExoPlayer(0, false);
            stepsAttr = stepsList.get(id);
            restExoPlayer(0, false);
            checkSum();
            id %= stepsList.size();
            adapt.changeDataSet(stepsAttr);
        }

    }

    @Override
    public void change(int idx) {
        id = idx;
        stepsAttr = stepsList.get(idx);
        restExoPlayer(0, false);
        checkSum();
        adapt.changeDataSet(stepsAttr);
    }

    interface OnFragmentInteractionListener {
        void onFragmentInteraction(Object uri);
    }


    MediaSource MediaSource(String url) {


        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "ExoPlayer"));

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        return new ExtractorMediaSource(Uri.parse(url),
                dataSourceFactory, extractorsFactory, null, null);
    }

    private void initializeMediaSession() {

        media = new MediaSessionCompat(getContext(), "SingleStepPage");

        media.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        media.setMediaButtonReceiver(null);
        state = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        media.setPlaybackState(state.build());
        media.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                play.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                play.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                play.seekTo(0);
            }
        });
        media.setActive(true);
    }

    private void releasePlayer()
    {
        if (play != null)
        {
            pos=play.getCurrentPosition();


            play.stop();
            play.release();
            play = null;
        }
        if (media != null) {
            media.setActive(false);
        }
    }

    private void restExoPlayer(long position, boolean playWhenReady) {
        if (play == null) return;
        play.seekTo(position);
        play.setPlayWhenReady(playWhenReady);
        releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_BUFFERING) {
            B_Listen.onFragmentInteraction(true);
            state.setState(PlaybackStateCompat.STATE_BUFFERING, play.getCurrentPosition(), 1f);
        } else if (playbackState == ExoPlayer.STATE_READY && playWhenReady) {
            state.setState(PlaybackStateCompat.STATE_PLAYING, play.getCurrentPosition(), 1f);
            B_Listen.onFragmentInteraction(true);
        } else if (playbackState == ExoPlayer.STATE_READY) {

            state.setState(PlaybackStateCompat.STATE_PAUSED, play.getCurrentPosition(), 1f);
            B_Listen.onFragmentInteraction(true);
        }
        media.setPlaybackState(state.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();

        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();

        checkSum();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putInt("iD", id);
        outState.putLong("iDxe", pos);
        super.onSaveInstanceState(outState);
    }

    public void solv(int idx) {
        id = idx;
        stepsAttr = stepsList.get(idx);
        restExoPlayer(0, false);
        checkSum();
        adapt.changeDataSet(stepsAttr);
    }

}