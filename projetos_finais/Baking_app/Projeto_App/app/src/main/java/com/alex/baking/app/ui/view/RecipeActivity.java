package com.alex.baking.app.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.FrameLayout;
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
import com.alex.baking.app.data.source.sql.RecipeSqlSource;
import com.alex.baking.app.ui.presenter.RecipePresenter;
import com.alex.baking.app.ui.presenter.StepPresenter;
import com.alex.baking.app.ui.view.contract.RecipeContract;
import com.alex.baking.app.ui.view.contract.StepContract;
import com.alex.baking.app.ui.view.fragment.BaseFragment;
import com.alex.baking.app.ui.view.fragment.RecipeFragment;
import com.alex.baking.app.ui.view.fragment.StepFragment;

@SuppressWarnings("ConstantConditions")
public class RecipeActivity extends BaseActivity<Recipe, RecipeContract.View, RecipeContract.Presenter> implements RecipeContract.View, StepContract.View {

	public final static String RECIPE_ID_EXTRA_PARAM_KEY = "recipe_id";

	@BindView(R.id.flRecipeContainer)
	FrameLayout recipeContainerFL;
	@BindView(R.id.flStepContainer)
	@Nullable
	FrameLayout stepContainerFL;

	private StepFragment stepFragment;

	@Override
	public void initializeWidgets(Bundle savedInstanceState) {
		super.initializeWidgets(savedInstanceState);
		ButterKnife.bind(this);

		FragmentManager fm = getSupportFragmentManager();
		BaseFragment recipeFragment = new RecipeFragment();
		// TODO: 01/08/18 add param com bundle
		fm.beginTransaction().add(R.id.flRecipeContainer, recipeFragment).commit();
		if (isDualPanel()) {
			stepFragment = new StepFragment();
			RecipeRepository mRepository = new RecipeRepository(
					RecipeCache.getInstance(),
					new RecipeSqlSource(this),
					new RecipeSource(new NetworkResourceManager())
			);
			mRepository.setRemoteStepSource(new StepSource(new NetworkResourceManager()));
			stepFragment.setPresenter(new StepPresenter(this, this, null, mRepository));
			// TODO: 01/08/18 add param com bundle
			fm.beginTransaction().add(R.id.flStepContainer, stepFragment).commit();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);

		NetworkResourceManager networkResource = new NetworkResourceManager();
		RecipeRepositoryContract repo = new RecipeRepository(
				RecipeCache.getInstance(),
				new RecipeSqlSource(this),
				new RecipeSource(networkResource)
		);
		repo.setRemoteIngredientSource(new IngredientSource(networkResource));
		repo.setRemoteStepSource(new StepSource(networkResource));
		mPresenter = new RecipePresenter(this, this, savedInstanceState, repo);
		mTitle = getString(R.string.app_name);
	}

	@Override
	public void initializeArgumentsFromIntent() {
		Intent intent = getIntent();
		if (intent != null && intent.hasExtra(RECIPE_ID_EXTRA_PARAM_KEY)) {
			long recipeId = intent.getExtras().getLong(RECIPE_ID_EXTRA_PARAM_KEY, -1);
			mPresenter.setRecipeId(recipeId);
		} else {
			throw new IllegalArgumentException("Não foi passado ID de recipe");
		}
	}

	@Override
	public boolean isDualPanel() {
		return stepContainerFL != null;
	}

	@Override
	public void replaceStepFragment(Long selectedStepId) {
		FragmentManager fm = getSupportFragmentManager();
		if (stepFragment != null) {
			fm.beginTransaction().replace(R.id.flStepContainer, stepFragment).commit();
		} else {
			Log.e("recipe Act", "Erro de step fragment nulo"); // TODO: 01/08/18 - Não comitar, dont push this
		}
	}
}
