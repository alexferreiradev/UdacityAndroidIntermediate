package com.alex.baking.app.ui.view;

import android.os.Bundle;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.ui.view.contract.RecipeContract;

public class RecipeActivity extends BaseActivity<Recipe, RecipeContract.View, RecipeContract.Presenter> implements RecipeContract.View {

	public RecipeActivity() {
		super("");
		setTitle(R.string.recipe_steps_label);
	}

	public RecipeActivity(String mTitle) {
		super(mTitle);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);
	}

	@Override
	public void initializeArgumentsFromIntent() {

	}
}
