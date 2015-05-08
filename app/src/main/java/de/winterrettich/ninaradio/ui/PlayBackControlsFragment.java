package de.winterrettich.ninaradio.ui;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.otto.Subscribe;

import de.winterrettich.ninaradio.R;
import de.winterrettich.ninaradio.RadioApplication;
import de.winterrettich.ninaradio.event.PlaybackEvent;

public class PlayBackControlsFragment extends Fragment implements View.OnClickListener {

    private boolean mIsPlaying = false;
    private ImageButton mPlayPauseButton;
    private Drawable mPlayDrawable;
    private Drawable mPauseDrawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playback_controls, container, false);

        mPlayPauseButton = (ImageButton) rootView.findViewById(R.id.play_pause);
        mPlayPauseButton.setOnClickListener(this);

        mPlayDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_play_arrow_black_36dp);
        mPauseDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_pause_black_36dp);

        RadioApplication.sBus.register(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        if (mIsPlaying) {
            RadioApplication.sBus.post(new PlaybackEvent(PlaybackEvent.Type.PAUSE));
        } else {
            RadioApplication.sBus.post(new PlaybackEvent(PlaybackEvent.Type.PLAY));
        }
    }

    @Subscribe
    public void handlePlaybackEvent(PlaybackEvent event) {
        switch (event.type) {
            case PLAY:
                mPlayPauseButton.setImageDrawable(mPauseDrawable);
                mIsPlaying = true;
                break;
            case PAUSE:
                mPlayPauseButton.setImageDrawable(mPlayDrawable);
                mIsPlaying = false;
                break;
            case STOP:
                break;
        }
    }
}
