package com.alex.baking.app.ui.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.recipe.RecipeRepositoryContract;
import com.alex.baking.app.ui.view.contract.StepContract;

public class StepPresenter extends BasePresenter<StepContract.View, Step, RecipeRepositoryContract, String, Double, Object> implements StepContract.Presenter {

	private Long stepId;
	private Step step;

	protected StepPresenter(StepContract.View mView, Context mContext, Bundle savedInstanceState, RecipeRepositoryContract mRepository) {
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
			mView.bindViewModel(step);
		}
	}

	@Override
	protected Object loadInBackgroud(String[] strings) {
		return mRepository.recoverStep(stepId);
	}

	@Override
	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}
}
