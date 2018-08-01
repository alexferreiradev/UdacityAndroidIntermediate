package com.alex.baking.app.ui.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.recipe.RecipeRepository;
import com.alex.baking.app.data.source.cache.RecipeCache;
import com.alex.baking.app.data.source.remote.RecipeSource;
import com.alex.baking.app.data.source.remote.StepSource;
import com.alex.baking.app.data.source.remote.network.NetworkResourceManager;
import com.alex.baking.app.data.source.sql.RecipeSqlSource;
import com.alex.baking.app.ui.presenter.StepPresenter;
import com.alex.baking.app.ui.view.contract.RecipeContract;
import com.alex.baking.app.ui.view.fragment.BaseFragment;
import com.alex.baking.app.ui.view.fragment.RecipeFragment;
import com.alex.baking.app.ui.view.fragment.StepFragment;

import java.util.List;

public class RecipeActivity extends BaseActivity<Recipe, RecipeContract.View, RecipeContract.Presenter> implements RecipeContract.View {

	public final static String RECIPE_ID_EXTRA_PARAM_KEY = "recipe_id";

	@BindView(R.id.flRecipeContainer)
	FrameLayout recipeContainerFL;
	@BindView(R.id.flStepContainer)
	FrameLayout stepContainerFL;

	private StepFragment stepFragment;

	public RecipeActivity() {
		super("");
		setTitle(R.string.recipe_steps_label);
	}

	public RecipeActivity(String mTitle) {
		super(mTitle);
	}

	@Override
	public void initializeWidgets(Bundle savedInstanceState) {
		super.initializeWidgets(savedInstanceState);
		ButterKnife.bind(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);
	}

	@Override
	public void initializeArgumentsFromIntent() {

	}

	@Override
	public void bindViewModel(Recipe recipe) {
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
			stepFragment.setPresenter(new StepPresenter(stepFragment, this, null, mRepository));
			// TODO: 01/08/18 add param com bundle
			fm.beginTransaction().add(R.id.flStepContainer, stepFragment).commit();
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

	@Override
	public void addIngredientToAdapter(List<Ingredient> ingredientList) {
//		if (ingredientAdapter == null){
//			ingredientAdapter = new IngredientAdapter(ingredientList);
//			in
//		} else {
//			ingredientAdapter.addAllModel(ingredientList);
//		}
	}

	@Override
	public void addStepToAdapter(List<Step> stepList) {

	}
}
