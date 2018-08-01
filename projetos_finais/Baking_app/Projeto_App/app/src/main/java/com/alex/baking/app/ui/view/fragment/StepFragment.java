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

public class StepFragment extends BaseFragment<Step, StepContract.Presenter> implements StepContract.View {

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
	public void setPresenter(StepContract.Presenter presenter) {
		this.presenter = presenter;
	}


	@Override
	public void startView(Step model) throws IllegalArgumentException {
		// TODO: 01/08/18 Add fragment de video

		shortDescriptionTV.setText(model.getShortDescription());
		descriptionTV.setText(model.getDescription());
		nextBt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.selectNextStep();
			}
		});
	}

	@Override
	public Step destroyView(Step model) {
		return null;
	}

	@Override
	public void bindViewModel(Step step) {

	}

	@Override
	public void setLoadProgressBarVisibility(boolean toVisible) {

	}

	@Override
	public void initializeWidgets(Bundle savedInstanceState) {

	}

	@Override
	public void initializeArgumentsFromIntent() {

	}

	@Override
	public void showErrorMsg(String msg) {

	}

	@Override
	public void showSuccessMsg(String msg) {

	}

	@Override
	public void setActionBarTitle(String title) {

	}
}
