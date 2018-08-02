package com.alex.baking.app.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.view.contract.StepContract;

public class StepFragment extends BaseFragment<Step, StepContract.Presenter> implements StepContract.FragmentView {

	@BindView(R.id.tvDescription)
	TextView descriptionTV;
	@BindView(R.id.tvShortDescription)
	TextView shortDescriptionTV;
	@BindView(R.id.btNextStep)
	Button nextBt;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_step, container, false);
		ButterKnife.bind(this, view);

		return view;
	}

	@Override
	protected void startWithArguments(Bundle arguments) {
		presenter.startPresenterView();
	}

	@Override
	public void bindViewModel(Step step) {
		// TODO: 01/08/18 Add fragment de video

		shortDescriptionTV.setText(step.getShortDescription());
		descriptionTV.setText(step.getDescription());
		nextBt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.selectNextStep(-1);
			}
		});
	}

	@Override
	public void setPresenter(StepContract.Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public Step getStepFromPosition(int pos) {
		return null;
	}


	@Override
	public void startView(Step model) throws IllegalArgumentException {

	}

	@Override
	public Step destroyView(Step model) {
		return null;
	}
}
