package com.alex.baking.app.ui.view;

import android.os.Bundle;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.view.contract.StepContract;

public class StepActivity extends BaseActivity<Step, StepContract.View, StepContract.Presenter> implements StepContract.View {

	@BindView(R.id.flStepContainer)
	FrameLayout stepContainerFL;

	public StepActivity() {
		super("");
		setTitle(getString(R.string.recipe_steps_label));
	}

	public StepActivity(String mTitle) {
		super(mTitle);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step);
	}

	@Override
	public void initializeArgumentsFromIntent() {

	}

	@Override
	public void bindViewModel(Step step) {

	}
}
