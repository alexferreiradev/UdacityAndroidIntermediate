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
	private static final String LOAD_NEXT_STEP = "task_load_next_step";
	private Long stepId;
	private Step step;
	private StepContract.FragmentView fragmentView;
	private int currentPosition;

	public StepPresenter(StepContract.View mView, Context mContext, Bundle savedInstanceState, RecipeRepositoryContract mRepository) {
		super(mView, mContext, savedInstanceState, mRepository);
	}

	@Override
	protected void initialize() {
		BackgroundTask task = new BackgroundTask();
		task.execute(LOAD_STEP);
	}

	@Override
	protected void backgroundFinished(@NonNull Object o) {
		if (o instanceof Step) {
			step = (Step) o;
			fragmentView.bindViewModel(step);
		}
		if (mView.isDualPanel()) {
			return; // nao dual panel
		}

		this.currentPosition = currentPosition + 1;
		mView.setStepInListToSelected(currentPosition);
	}

	@Override
	protected Object loadInBackground(String[] strings) {
		try {
			switch (strings[0]) {
				case LOAD_NEXT_STEP:
					return mRepository.recoverStep(stepId + 1);
				default:
					return mRepository.recoverStep(stepId);
			}
		} catch (ConnectionException e) {
			Log.e(TAG, "Erro de conexão", e);
		} catch (Exception e) {
			Log.e(TAG, "Erro interno", e);
		}

		return null;
	}

	@Override
	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	@Override
	public void selectNextStep(int currentPosition) {
		if (currentPosition < 0) {
			return; // nao dual panel
		}

		BackgroundTask task = new BackgroundTask();
		task.execute(LOAD_NEXT_STEP);
	}

	@Override
	public void setFragmentView(StepContract.FragmentView fragmentView) {
		this.fragmentView = fragmentView;
	}

	@Override
	public void changeToStep(Long selectedStepId, int position) {
		this.currentPosition = position;
		BackgroundTask task = new BackgroundTask();
		stepId = selectedStepId;
		task.execute(LOAD_STEP);
	}

	@Override
	public void playerFoundError(ExoPlaybackException error, long currentPosition) {
		fragmentView.showErroMsgInPlayer(mContext.getString(R.string.player_error));
		mView.showErrorMsg(mContext.getString(R.string.player_error));
	}
}
