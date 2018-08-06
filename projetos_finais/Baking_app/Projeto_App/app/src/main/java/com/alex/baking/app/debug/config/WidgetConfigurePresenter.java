package com.alex.baking.app.debug.config;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.repository.recipe.RecipeRepositoryContract;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.debug.BakingWidget;
import com.alex.baking.app.ui.presenter.BasePresenter;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class WidgetConfigurePresenter extends BasePresenter<WidgetConfigureContract.View, Recipe, RecipeRepositoryContract, String, Integer, Object> implements WidgetConfigureContract.Presenter {

	private static final String TAG = WidgetConfigurePresenter.class.getSimpleName();
	private static final String LOAD_RECIPE_LIST_TASK_ID = "load_recipe_list";
	private int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	WidgetConfigurePresenter(WidgetConfigureContract.View mView, Context mContext, Bundle savedInstanceState, RecipeRepositoryContract mRepository) {
		super(mView, mContext, savedInstanceState, mRepository);
	}

	@Override
	protected void initialize() {
		BackgroundTask task = new BackgroundTask();
		task.execute(LOAD_RECIPE_LIST_TASK_ID);
	}

	@Override
	public void selectRecipe(Recipe recipe) {
		if (recipe != null && recipe.getId() != null) {
			WidgetConfigurationUtil.saveRecipeConfig(recipe, widgetId, mContext);
		} else {
			Log.w(TAG, "Recipe configurado para widget não é válido, sem ID");
			mView.showErrorMsg("Selecione um prato antes");

			return;
		}

		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
		BakingWidget.updateAppWidget(mContext, appWidgetManager, widgetId);

		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		((Activity) mContext).setResult(RESULT_OK, resultValue);
		((Activity) mContext).finish();
	}

	@Override
	public void setWidgetId(int widgetId) {
		this.widgetId = widgetId;
	}

	@Override
	protected Object loadInBackground(String[] strings) {
		try {
			return mRepository.getRecipeList(100, 0);
		} catch (ConnectionException e) {
			Log.e(TAG, "Erro de conexao", e);
		} catch (Exception e) {
			Log.e(TAG, "Erro interno", e);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void backgroundFinished(@NonNull Object result) {
		List<Recipe> recipeList = (List<Recipe>) result;
		if (recipeList.isEmpty()) {
			Log.w(TAG, "Lista vazia retornada pelo repositorio");
			return;
		}

		mView.addSpinnerData(recipeList);
	}
}
