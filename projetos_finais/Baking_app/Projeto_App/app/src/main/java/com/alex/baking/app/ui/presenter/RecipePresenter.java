package com.alex.baking.app.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.recipe.RecipeRepositoryContract;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.ui.view.StepActivity;
import com.alex.baking.app.ui.view.contract.RecipeContract;

import java.util.List;

public class RecipePresenter extends BasePresenter<RecipeContract.View, Recipe, RecipeRepositoryContract, String, Double, Object> implements RecipeContract.Presenter {

	private static final String TAG = RecipePresenter.class.getSimpleName();

	private static final String LOAD_RECIPE = "load_recipe";
	private static final String LOAD_INGREDIENTS = "load_recipe_ingredients";
	private static final String LOAD_STEPS = "load_recipe_steps";
	private static final int LIMIT = 100;

	private Long recipeId;
	private Recipe recipe;

	protected RecipePresenter(RecipeContract.View mView, Context mContext, Bundle savedInstanceState, RecipeRepositoryContract mRepository) {
		super(mView, mContext, savedInstanceState, mRepository);
	}

	@Override
	protected void initialize() {
		BackgroundTask task = new BackgroundTask();
		task.execute(LOAD_RECIPE);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void backgroudFinished(@NonNull Object result) {
		if (result instanceof Recipe) {
			recipe = (Recipe) result;
			mView.bindViewModel(recipe);
		} else if (result instanceof List) {
			List resultList = (List) result;
			if (resultList.isEmpty()) {
				return;
			}

			if (resultList.get(0) instanceof Ingredient) {
				List<Ingredient> ingredientList = ((List<Ingredient>) resultList);
				mView.addIngredientToAdapter(ingredientList);
			} else {
				List<Step> stepList = ((List<Step>) resultList);
				mView.addStepToAdapter(stepList);
			}
		}
	}

	@Override
	protected Object loadInBackgroud(String... strings) {
		try {
			switch (strings[0]) {
				case LOAD_RECIPE:
					return mRepository.recover(recipeId);
				case LOAD_INGREDIENTS:
					return mRepository.getIngredientListByRecipe(recipeId, LIMIT, 0);
				case LOAD_STEPS:
					return mRepository.getStepListByRecipe(recipeId, LIMIT, 0);
			}
		} catch (ConnectionException e) {
			Log.e(TAG, "Erro interno");
		}

		return null;
	}

	@Override
	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	@Override
	public void selectStep(Long selectedStepId) {
		if (mView.isDualPanel()) {
			mView.replaceStepFragment(selectedStepId);
		} else {
			Intent intent = new Intent(mContext, StepActivity.class);
			intent.putExtra("", selectedStepId);

			mContext.startActivity(intent);
		}
	}
}
