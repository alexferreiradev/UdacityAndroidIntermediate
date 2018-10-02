package com.alex.baking.app.ui.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.recipe.RecipeRepositoryContract;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.ui.view.contract.StepContract;
import com.google.android.exoplayer2.ExoPlaybackException;

public class StepPresenter extends BasePresenter<StepContract.View, Step, RecipeRepositoryContract, String, Double, Object> implements StepContract.Presenter {

	private static final String TAG = StepPresenter.class.getSimpleName();
	private static final String LOAD_STEP = "task_load_step";

	private static final String STEPID_SAVED_KEY = "stepId_saved_key";
	private Long stepId;
	private StepContract.FragmentView fragmentView;
	private int currentPosition;
	private long currentPlayerPos = 0;
	private Boolean currentPlayerState = true;

	public StepPresenter(StepContract.View mView, Context mContext, Bundle savedInstanceState, RecipeRepositoryContract mRepository) {
		super(mView, mContext, savedInstanceState, mRepository);

		if (savedInstanceState != null) {
			long stepIdSaved = savedInstanceState.getLong(STEPID_SAVED_KEY, -1);
			if (stepIdSaved > 0) {
				stepId = stepIdSaved;
			}
		}
	}

	@Override
	protected void initialize() {
		BackgroundTask task = new BackgroundTask();
		task.execute(LOAD_STEP);
	}

	@Override
	protected void backgroundFinished(@NonNull Object o) {
		if (o instanceof Step) {
			Step step = (Step) o;
			fragmentView.setPlayerState(currentPlayerState);
			fragmentView.setPlayerPosition(currentPlayerPos);
			fragmentView.setPlayerInView();
			fragmentView.startView(step);
			fragmentView.setNextBtVisility(true);
		}

		if (mView.isDualPanel()) {
			fragmentView.setNextBtVisility(false);
		}
	}

	@Override
	protected Object loadInBackground(String[] strings) {
		try {
			return mRepository.recoverStep(stepId);
		} catch (ConnectionException e) {
			Log.e(TAG, "Erro de conexão", e);
		} catch (Exception e) {
			Log.e(TAG, "Erro interno", e);
		}

		return null;
	}

	@Override
	public void setStepId(Long stepId) {
		if (this.stepId == null) { // Quando tiver sido restaurado por savedInstance, estará diferente de null.
			this.stepId = stepId; // Utiliza argumentos de activity para setar
		}
	}

	@Override
	public void selectNextStep() {
		fragmentView.stopPlayer();
		fragmentView.showMsgInPlayer(mContext.getString(R.string.video_empty_text));
		preparePlayerToNewVideo();
		changeToStep(stepId + 1, currentPosition + 1);
	}

	private void preparePlayerToNewVideo() {
		currentPlayerPos = 0;
		currentPlayerState = true;
	}

	@Override
	public void setFragmentView(StepContract.FragmentView fragmentView) {
		this.fragmentView = fragmentView;
	}

	@Override
	public void changeToStep(Long selectedStepId, int position) {
		this.currentPosition = position;
		stepId = selectedStepId;

		BackgroundTask task = new BackgroundTask();
		task.execute(LOAD_STEP);
	}

	@Override
	public void playerFoundError(ExoPlaybackException error, long currentPosition) {
		Log.e(TAG, "Erro encontrado no player: " + error.getMessage());
		fragmentView.showMsgInPlayer(mContext.getString(R.string.player_error));
		mView.showErrorMsg(mContext.getString(R.string.player_error));
	}

	@Override
	public void saveDataState(Bundle bundle) {
		bundle.putLong(STEPID_SAVED_KEY, stepId);
	}

	@Override
	public void restorePlayerState(long savedPos, Boolean savedStateBol) {
		currentPlayerPos = savedPos;
		currentPlayerState = savedStateBol;
	}
}
