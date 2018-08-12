package com.alex.baking.app.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.repository.recipe.RecipeRepository;
import com.alex.baking.app.data.repository.recipe.RecipeRepositoryContract;
import com.alex.baking.app.data.source.cache.RecipeCache;
import com.alex.baking.app.data.source.remote.IngredientSource;
import com.alex.baking.app.data.source.remote.RecipeSource;
import com.alex.baking.app.data.source.remote.StepSource;
import com.alex.baking.app.data.source.remote.network.NetworkResourceManager;
import com.alex.baking.app.data.source.sql.IngredientSqlSource;
import com.alex.baking.app.data.source.sql.RecipeSqlSource;
import com.alex.baking.app.data.source.sql.StepSqlSource;
import com.alex.baking.app.ui.presenter.RecipePresenter;
import com.alex.baking.app.ui.presenter.StepPresenter;
import com.alex.baking.app.ui.view.contract.RecipeContract;
import com.alex.baking.app.ui.view.contract.StepContract;
import com.alex.baking.app.ui.view.fragment.RecipeFragment;
import com.alex.baking.app.ui.view.fragment.StepFragment;

@SuppressWarnings("ConstantConditions")
public class RecipeActivity extends BaseActivity<Recipe, RecipeContract.View, RecipeContract.Presenter> implements RecipeContract.View, StepContract.View {

	private static final String TAG = RecipeActivity.class.getSimpleName();

	public final static String RECIPE_ID_EXTRA_PARAM_KEY = "recipe_id";

	@BindView(R.id.flRecipeContainer)
	FrameLayout recipeContainerFL;
	@BindView(R.id.flStepContainer)
	@Nullable
	FrameLayout stepContainerFL;
	@Nullable
	@BindView(R.id.tvEmptyStep)
	TextView emptyStepTV;

	private StepFragment stepFragment;
	private StepContract.Presenter stepPresenter;

	@Override
	public void initializeWidgets(Bundle savedInstanceState) {
		super.initializeWidgets(savedInstanceState);
		ButterKnife.bind(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);

		NetworkResourceManager networkResource = new NetworkResourceManager();
		RecipeRepositoryContract repo = new RecipeRepository(
				this, RecipeCache.getInstance(),
				new RecipeSqlSource(this),
				new RecipeSource(networkResource)
		);
		repo.setRemoteIngredientSource(new IngredientSource(networkResource));
		repo.setRemoteStepSource(new StepSource(networkResource));
		repo.setIngredientLocalSource(new IngredientSqlSource(this));
		repo.setStepLocalSource(new StepSqlSource(this));
		mPresenter = new RecipePresenter(this, this, savedInstanceState, repo);
		mTitle = getString(R.string.app_name);
	}

	@Override
	public void initializeArgumentsFromIntent() {
		Intent intent = getIntent();
		if (intent != null && intent.hasExtra(RECIPE_ID_EXTRA_PARAM_KEY)) {
			long recipeId = intent.getExtras().getLong(RECIPE_ID_EXTRA_PARAM_KEY, -1);
			Log.i(TAG, String.format("Iniciando com recipeId=%x", recipeId));

			mPresenter.setRecipeId(recipeId);
		} else {
			throw new IllegalArgumentException("Não foi passado ID de recipe");
		}

		FragmentManager fm = getSupportFragmentManager();
		RecipeFragment recipeFragment = new RecipeFragment();
		recipeFragment.setPresenter(mPresenter);
		mPresenter.setFragmentView(recipeFragment);
		fm.beginTransaction().replace(R.id.flRecipeContainer, recipeFragment).commit();

		if (isDualPanel()) {
			stepFragment = new StepFragment();
			RecipeRepository mRepository = new RecipeRepository(
					this, RecipeCache.getInstance(),
					new RecipeSqlSource(this),
					new RecipeSource(new NetworkResourceManager())
			);
			mRepository.setRemoteStepSource(new StepSource(new NetworkResourceManager()));
			mRepository.setStepLocalSource(new StepSqlSource(this));
			stepPresenter = new StepPresenter(this, this, null, mRepository);
			stepFragment.setPresenter(stepPresenter);
			stepPresenter.setFragmentView(stepFragment);
		}
	}

	@Override
	public boolean isDualPanel() {
		return stepContainerFL != null;
	}

	@Override
	public void selectStepItem(Long selectedStepId, int position) {
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.flStepContainer, stepFragment).commit();
		stepPresenter.changeToStep(selectedStepId, position);
	}

	@Override
	public void setEmptyStepSelectVisibility(boolean visible) {
		if (visible) {
			emptyStepTV.setVisibility(View.VISIBLE);
		} else {
			emptyStepTV.setVisibility(View.GONE);
		}
	}
}
