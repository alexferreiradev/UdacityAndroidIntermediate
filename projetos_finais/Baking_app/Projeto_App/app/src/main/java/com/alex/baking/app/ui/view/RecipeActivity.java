package com.alex.baking.app.ui.view;

import android.os.Bundle;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.ui.view.contract.RecipeContract;

import java.util.List;

public class RecipeActivity extends BaseActivity<Recipe, RecipeContract.View, RecipeContract.Presenter> implements RecipeContract.View {

	public final static String RECIPE_ID_EXTRA_PARAM_KEY = "recipe_id";

	@BindView(R.id.flRecipeContainer)
	private FrameLayout recipeContainerFL;
	@BindView(R.id.flStepContainer)
	private FrameLayout stepContainerFL;

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

	}

	@Override
	public boolean isDualPanel() {
		return false;
	}

	@Override
	public void replaceStepFragment(Long selectedStepId) {

	}

	@Override
	public void addIngredientToAdapter(List<Ingredient> ingredientList) {

	}

	@Override
	public void addStepToAdapter(List<Step> stepList) {

	}
}
