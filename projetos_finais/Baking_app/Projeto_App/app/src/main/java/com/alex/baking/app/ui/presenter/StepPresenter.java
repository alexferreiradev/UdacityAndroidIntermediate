package com.alex.baking.app.ui.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.recipe.RecipeRepositoryContract;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.ui.view.contract.StepContract;

public class StepPresenter extends BasePresenter<StepContract.View, Step, RecipeRepositoryContract, String, Double, Object> implements StepContract.Presenter {

	private static final String TAG = StepPresenter.class.getSimpleName();
	private Long stepId;
	private Step step;
	private StepContract.FragmentView fragmentView;

	public StepPresenter(StepContract.View mView, Context mContext, Bundle savedInstanceState, RecipeRepositoryContract mRepository) {
		super(mView, mContext, savedInstanceState, mRepository);
	}

	@Override
	protected void initialize() {
		BackgroundTask task = new BackgroundTask();
		task.execute();
	}

	@Override
	protected void backgroudFinished(@NonNull Object o) {
		if (o instanceof Step) {
			step = (Step) o;
			fragmentView.bindViewModel(step);
		}
	}

	@Override
	protected Object loadInBackgroud(String[] strings) {
		try {
			return mRepository.recoverStep(stepId);
		} catch (ConnectionException e) {
			Log.e(TAG, "Erro interno");
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

		Step stepFromPosition = fragmentView.getStepFromPosition(currentPosition + 1);
		fragmentView.bindViewModel(stepFromPosition);
	}

	@Override
	public void setFragmentView(StepContract.FragmentView fragmentView) {
		this.fragmentView = fragmentView;
	}
}
