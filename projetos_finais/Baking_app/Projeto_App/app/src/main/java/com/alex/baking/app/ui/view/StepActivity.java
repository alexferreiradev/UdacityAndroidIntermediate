package com.alex.baking.app.ui.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.recipe.RecipeRepository;
import com.alex.baking.app.data.repository.recipe.RecipeRepositoryContract;
import com.alex.baking.app.data.source.cache.RecipeCache;
import com.alex.baking.app.data.source.remote.RecipeSource;
import com.alex.baking.app.data.source.remote.StepSource;
import com.alex.baking.app.data.source.remote.network.NetworkResourceManager;
import com.alex.baking.app.data.source.sql.RecipeSqlSource;
import com.alex.baking.app.ui.presenter.StepPresenter;
import com.alex.baking.app.ui.view.contract.StepContract;
import com.alex.baking.app.ui.view.fragment.StepFragment;

public class StepActivity extends BaseActivity<Step, StepContract.View, StepContract.Presenter> implements StepContract.View {

	public static final String STEP_ID_EXTRA_PARAM_KEY = "step_id";
	public static final String STEP_POSITION_EXTRA_PARAM_KEY = "step_pos";

	@BindView(R.id.flStepContainer)
	FrameLayout stepContainerFL;
	private StepFragment stepFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step);

		NetworkResourceManager networkResource = new NetworkResourceManager();
		RecipeRepositoryContract repo = new RecipeRepository(
				this, RecipeCache.getInstance(),
				new RecipeSqlSource(this),
				new RecipeSource(networkResource)
		);
		repo.setRemoteStepSource(new StepSource(networkResource));
		mPresenter = new StepPresenter(this, this, savedInstanceState, repo);
	}

	@Override
	public void initializeWidgets(Bundle savedInstanceState) {
		super.initializeWidgets(savedInstanceState);
		ButterKnife.bind(this);

		stepFragment = new StepFragment();
		stepFragment.setPresenter(mPresenter);
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().add(R.id.flStepContainer, stepFragment).commit();
	}

	@Override
	public void initializeArgumentsFromIntent() {

	}
}
