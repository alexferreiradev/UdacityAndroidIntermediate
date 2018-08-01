package com.alex.baking.app.ui.view.contract;

import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.presenter.BasePresenter;
import com.alex.baking.app.ui.presenter.IPresenter;

public interface StepContract {

	interface View extends BasePresenter.View<Step> {
		void bindViewModel(Step step);
	}

	interface Presenter extends IPresenter {

		void setStepId(Long stepId);

		void selectNextStep();
	}
}
