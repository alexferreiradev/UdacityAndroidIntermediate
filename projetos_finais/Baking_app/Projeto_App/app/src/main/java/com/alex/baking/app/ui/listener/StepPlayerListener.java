package com.alex.baking.app.ui.listener;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import com.alex.baking.app.ui.view.contract.StepContract;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

public class StepPlayerListener implements Player.EventListener {

	private MediaSessionCompat md;
	private ExoPlayer player;
	private StepContract.Presenter presenter;

	public StepPlayerListener(MediaSessionCompat session, ExoPlayer player, StepContract.Presenter presenter) {
		this.md = session;
		this.player = player;
		this.presenter = presenter;
	}

	@Override
	public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

	}

	@Override
	public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

	}

	@Override
	public void onLoadingChanged(boolean isLoading) {

	}

	@Override
	public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
		PlaybackStateCompat.Builder playbackBuilder = new PlaybackStateCompat.Builder();
		switch (playbackState) {
			case Player.STATE_BUFFERING:
				playbackBuilder.setState(PlaybackStateCompat.STATE_BUFFERING, player.getCurrentPosition(), 1f);
				break;
			case Player.STATE_ENDED:
				playbackBuilder.setState(PlaybackStateCompat.STATE_STOPPED, player.getCurrentPosition(), 1f);
				break;
			case Player.STATE_READY:
				playbackBuilder.setState(PlaybackStateCompat.STATE_PLAYING, player.getCurrentPosition(), 1f);
				break;
		}
		md.setPlaybackState(playbackBuilder.build());
	}

	@Override
	public void onRepeatModeChanged(int repeatMode) {

	}

	@Override
	public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

	}

	@Override
	public void onPlayerError(ExoPlaybackException error) {
		PlaybackStateCompat.Builder playbackBuilder = new PlaybackStateCompat.Builder();
		playbackBuilder.setState(PlaybackStateCompat.STATE_ERROR, player.getCurrentPosition(), 1f);
		md.setPlaybackState(playbackBuilder.build());

		presenter.playerFoundError(error, player.getCurrentPosition());
	}

	@Override
	public void onPositionDiscontinuity(int reason) {

	}

	@Override
	public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

	}

	@Override
	public void onSeekProcessed() {

	}
}
