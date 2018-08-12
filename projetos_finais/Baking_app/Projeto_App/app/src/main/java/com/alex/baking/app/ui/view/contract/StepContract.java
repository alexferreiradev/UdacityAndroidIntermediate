package com.alex.baking.app.ui.view.contract;

import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.presenter.BasePresenter;
import com.alex.baking.app.ui.presenter.IPresenter;
import com.alex.baking.app.ui.view.fragment.SimpleFragment;
import com.google.android.exoplayer2.ExoPlaybackException;

public interface StepContract {

	interface View extends BasePresenter.View {
		boolean isDualPanel();
	}

	interface FragmentView extends SimpleFragment<Step, Presenter> {

		void showErroMsgInPlayer(String msg);

		void setNextBtVisility(boolean visible);
	}

	interface Presenter extends IPresenter {

		void setStepId(Long stepId);

		void selectNextStep();

		void setFragmentView(FragmentView fragmentView);

		void changeToStep(Long selectedStepId, int position);

		void playerFoundError(ExoPlaybackException error, long currentPosition);
	}
}
