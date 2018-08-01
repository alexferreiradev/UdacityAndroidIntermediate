package com.alex.baking.app.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.repository.recipe.RecipeRepositoryContract;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.ui.view.RecipeActivity;
import com.alex.baking.app.ui.view.contract.MainContract;

import java.util.List;

public class MainPresenter extends BaseListPresenter<MainContract.View, Recipe, RecipeRepositoryContract, String, Double, List<Recipe>> implements MainContract.Presenter {

	private static final String TAG = MainPresenter.class.getSimpleName();

	public MainPresenter(MainContract.View mView, Context mContext, Bundle savedInstanceState, RecipeRepositoryContract mRepository) {
		super(mView, mContext, savedInstanceState, mRepository);
	}

	@Override
	protected void backgroudFinished(@NonNull List<Recipe> recipes) {
		mView.createListAdapter(recipes);
	}

	@Override
	protected List<Recipe> loadInBackgroud(String... strings) {
		try {
			return mRepository.getRecipeList(mLoadItemsLimit, mOffset);
		} catch (ConnectionException e) {
			Log.e(TAG, "Erro interno ao carregar lista");
		}

		return null;
	}

	@Override
	protected void setEmptyView() {
		mView.setEmptyView(mContext.getString(R.string.none_recipe_loaded));
	}

	@Override
	protected void loadDataFromSource() {
		BackgroundTask task = new BackgroundTask();
		task.execute();
	}

	@Override
	public void selectItemClicked(Recipe item, int pos) {
		Intent intent = new Intent(mContext, RecipeActivity.class);
		intent.putExtra(RecipeActivity.RECIPE_ID_EXTRA_PARAM_KEY, item.getId());

		mContext.startActivity(intent);
	}
}
