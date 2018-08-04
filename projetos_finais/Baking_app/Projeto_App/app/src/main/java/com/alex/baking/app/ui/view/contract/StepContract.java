package com.alex.baking.app.ui.view.contract;

import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.presenter.BasePresenter;
import com.alex.baking.app.ui.presenter.IPresenter;

public interface StepContract {

	interface View extends BasePresenter.View {
	}

	interface FragmentView {

		void bindViewModel(Step step);

		void setPresenter(Presenter presenter);

		Step getStepFromPosition(int pos);
	}

	interface Presenter extends IPresenter {

		void setStepId(Long stepId);

		void selectNextStep(int currentPosition);

		void setFragmentView(FragmentView fragmentView);
	}
}
