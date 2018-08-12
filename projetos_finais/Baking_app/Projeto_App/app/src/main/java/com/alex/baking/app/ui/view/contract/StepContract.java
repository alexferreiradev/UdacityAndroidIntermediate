package com.alex.baking.app.ui.view.contract;

import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.presenter.BasePresenter;
import com.alex.baking.app.ui.presenter.IPresenter;
import com.google.android.exoplayer2.ExoPlaybackException;

public interface StepContract {

	interface View extends BasePresenter.View {
		void setStepInListToSelected(int position);

		boolean isDualPanel();
	}

	interface FragmentView {

		void bindViewModel(Step step);

		void setPresenter(Presenter presenter);

		void showErroMsgInPlayer(String msg);
	}

	interface Presenter extends IPresenter {

		void setStepId(Long stepId);

		void selectNextStep(int currentPosition);

		void setFragmentView(FragmentView fragmentView);

		void changeToStep(Long selectedStepId, int position);

		void playerFoundError(ExoPlaybackException error, long currentPosition);
	}
}
