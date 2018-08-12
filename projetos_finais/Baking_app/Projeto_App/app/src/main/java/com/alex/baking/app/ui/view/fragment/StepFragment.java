package com.alex.baking.app.ui.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.listener.StepMediaSessionCallbacks;
import com.alex.baking.app.ui.listener.StepPlayerListener;
import com.alex.baking.app.ui.view.contract.StepContract;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

@SuppressWarnings("Convert2Lambda")
public class StepFragment extends BaseFragment<Step, StepContract.Presenter> implements StepContract.FragmentView {

	private static final String TAG = StepFragment.class.getSimpleName();
	@BindView(R.id.tvDescription)
	TextView descriptionTV;
	@BindView(R.id.tvShortDescription)
	TextView shortDescriptionTV;
	@BindView(R.id.btNextStep)
	Button nextBt;
	@BindView(R.id.pvStep)
	PlayerView stepPV;
	private MediaSessionCompat mediaSession;
	private ExoPlayer player;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_step, container, false);
		ButterKnife.bind(this, view);

		return view;
	}

	@Override
	protected void startWithArguments(Bundle arguments) {
	}

	@NonNull
	private ExoPlayer createPlayer(Step step) {
		TrackSelector trackSelector = new DefaultTrackSelector();
		player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
		player.setPlayWhenReady(true);
		DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(Objects.requireNonNull(getContext()), Util.getUserAgent(getContext(), getString(R.string.app_name)), new DefaultBandwidthMeter());
		MediaSource media = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(step.getVideoURL()));
		player.prepare(media);
		mediaSession = new MediaSessionCompat(getContext(), "stepSession");
		mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
		mediaSession.setMediaButtonReceiver(null);
		PlaybackStateCompat.Builder playbackBuilder = new PlaybackStateCompat.Builder();
		playbackBuilder.setActions(PlaybackStateCompat.ACTION_PLAY |
				PlaybackStateCompat.ACTION_PAUSE |
				PlaybackStateCompat.ACTION_PLAY_PAUSE |
				PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
		);
		mediaSession.setPlaybackState(playbackBuilder.build());
		mediaSession.setActive(true);
		mediaSession.setCallback(new StepMediaSessionCallbacks(player));
		Player.EventListener playerListeners = new StepPlayerListener(mediaSession, player, presenter);
		player.addListener(playerListeners);

		return player;
	}

	@Override
	public void setPresenter(StepContract.Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void showErroMsgInPlayer(String msg) {
		stepPV.setCustomErrorMessage(msg);
	}

	@Override
	public void setNextBtVisility(boolean visible) {
		if (visible) {
			nextBt.setVisibility(View.VISIBLE);
		} else {
			nextBt.setVisibility(View.GONE);
		}
	}

	@Override
	public void startView(Step step) throws IllegalArgumentException {
		shortDescriptionTV.setText(step.getShortDescription());
		descriptionTV.setText(step.getDescription());
		nextBt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.selectNextStep();
			}
		});

		stepPV.setPlayer(createPlayer(step));
	}

	@Override
	public Step destroyView(Step model) {
		mediaSession.setActive(false);
		player.setPlayWhenReady(false);

		return null;
	}
}
