package com.alex.baking.app.ui.listener;

import android.support.v4.media.session.MediaSessionCompat;
import com.google.android.exoplayer2.ExoPlayer;

public class StepMediaSessionCallbacks extends MediaSessionCompat.Callback {

	private ExoPlayer player;

	public StepMediaSessionCallbacks(ExoPlayer player) {
		this.player = player;
	}

	@Override
	public void onPlay() {
		player.setPlayWhenReady(true);
		super.onPlay();
	}

	@Override
	public void onPause() {
		player.setPlayWhenReady(false);
		super.onPause();
	}

	@Override
	public void onSkipToPrevious() {
		player.seekTo(0);
		super.onSkipToPrevious();
	}
}
