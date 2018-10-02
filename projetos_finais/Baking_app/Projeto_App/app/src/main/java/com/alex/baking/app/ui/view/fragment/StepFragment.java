package com.alex.baking.app.ui.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
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
import com.google.android.exoplayer2.ExoPlaybackException;
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
public class StepFragment extends BaseFragment<Step, StepContract.Presenter> implements StepContract.FragmentView, StepPlayerListener.DispatchError {

	public static final String PLAYER_CURRENT_POS_SAVED_KEY = "PLAYER_CURRENT_POS_SAVED_KEY";
	public static final String PLAYER_STATE_SAVED_KEY = "PLAYER_STATE_SAVED_KEY";
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
	private long savedPlayerPos = -1;
	private Boolean savedPlayerState;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_step, container, false);
		ButterKnife.bind(this, view);

		player = createPlayer();
		player.setPlayWhenReady(true);
		stepPV.setPlayer(player);
		stepPV.setKeepContentOnPlayerReset(false);

		return view;
	}

	@Override
	protected void startWithArguments(Bundle arguments) {
	}

	@NonNull
	private ExoPlayer createPlayer() {
		TrackSelector trackSelector = new DefaultTrackSelector();
		ExoPlayer player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
		PlaybackStateCompat.Builder playbackBuilder = new PlaybackStateCompat.Builder();
		playbackBuilder.setActions(PlaybackStateCompat.ACTION_PLAY |
				PlaybackStateCompat.ACTION_PAUSE |
				PlaybackStateCompat.ACTION_PLAY_PAUSE |
				PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
		);

		mediaSession = createMediaSession(player, playbackBuilder);
		Player.EventListener playerListeners = new StepPlayerListener(mediaSession, player, this);
		player.addListener(playerListeners);

		return player;
	}

	private MediaSessionCompat createMediaSession(ExoPlayer player, PlaybackStateCompat.Builder playbackBuilder) {
		MediaSessionCompat mediaSession = new MediaSessionCompat(requireActivity(), "stepSession");

		mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
		mediaSession.setMediaButtonReceiver(null);
		mediaSession.setPlaybackState(playbackBuilder.build());
		mediaSession.setActive(true);
		mediaSession.setCallback(new StepMediaSessionCallbacks(player));

		return mediaSession;
	}

	@Override
	public void setPresenter(StepContract.Presenter presenter) {
		this.presenter = presenter;
		if (savedPlayerPos > -1) {
			presenter.restorePlayerState(savedPlayerPos, savedPlayerState);
		}
	}

	@Override
	public void showMsgInPlayer(String msg) {
		stepPV.setControllerAutoShow(false);
		stepPV.hideController();
		stepPV.setPlayer(null);
		stepPV.setShutterBackgroundColor(getResources().getColor(R.color.white));
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
	public void setPlayerState(boolean playWhenReady) {
		player.setPlayWhenReady(playWhenReady);
	}

	@Override
	public void stopPlayer() {
		player.setPlayWhenReady(false);
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

		MediaSource media = createMediaSource(step);
		player.prepare(media, false, false);
	}

	@NonNull
	private MediaSource createMediaSource(Step step) {
		DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(Objects.requireNonNull(getContext()), Util.getUserAgent(getContext(), getString(R.string.app_name)), new DefaultBandwidthMeter());
		return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(step.getVideoURL()));
	}

	@Override
	public void onPause() {
		savedPlayerState = player.getPlayWhenReady();
		savedPlayerPos = player.getCurrentPosition();

		super.onPause();// irá destruir o player, portanto, temos que salvar os estados antes
	}

	@Override
	public Step destroyView(Step model) {
		if (mediaSession != null) {
			mediaSession.setActive(false);
		}
		if (player != null) {
			player.release();
		}

		return null;
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong(PLAYER_CURRENT_POS_SAVED_KEY, savedPlayerPos);
		outState.putBoolean(PLAYER_STATE_SAVED_KEY, savedPlayerState);

		Log.d(TAG, "salvando estado de fragment Step: " + savedPlayerPos + savedPlayerState);
	}

	@Override
	public void setPlayerPosition(long currentPlayerPos) {
		player.seekTo(currentPlayerPos);
	}

	@Override
	public void setPlayerInView() {
		showMsgInPlayer(null);
		stepPV.setShutterBackgroundColor(getResources().getColor(R.color.black));
		stepPV.showController();
		stepPV.setControllerAutoShow(true);
		stepPV.setPlayer(player);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			long savedPos = savedInstanceState.getLong(PLAYER_CURRENT_POS_SAVED_KEY, -1);
			Object savedState = savedInstanceState.get(PLAYER_STATE_SAVED_KEY);

			if (savedPos > -1 && savedState != null) {
				Boolean savedStateBol = (Boolean) savedState;

				this.savedPlayerPos = savedPos;
				this.savedPlayerState = savedStateBol;
				Log.d(TAG, "Restaurando estado de fragment Step: " + savedPlayerPos + savedPlayerState);
			}
		}
	}

	@Override
	public void playerFoundError(Exception e, Long currentPos) {
		presenter.playerFoundError((ExoPlaybackException) e, currentPos);
	}
}
