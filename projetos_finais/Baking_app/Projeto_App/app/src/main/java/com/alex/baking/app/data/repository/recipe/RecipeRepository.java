package com.alex.baking.app.data.repository.recipe;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.BaseRepository;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.cache.MemoryCache;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.queryspec.remote.RecipeQuery;
import com.alex.baking.app.data.source.queryspec.remote.RecipeRelationsRemoteQuery;
import com.alex.baking.app.data.source.queryspec.sql.BaseSqlSpecification;
import com.alex.baking.app.data.source.queryspec.sql.SqlQuery;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.data.source.sql.BakingContract;
import com.alex.baking.app.debug.BakingWidget;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Alex on 02/04/2017.
 */

public class RecipeRepository extends BaseRepository<Recipe> implements RecipeRepositoryContract {
	private static final String TAG = RecipeRepository.class.getSimpleName();
	private Context context;
	private DefaultSource<Ingredient, URL> mRemoteIngredientSource;
	private DefaultSource<Step, URL> mRemoteStepSource;
	private DefaultSource<Ingredient, SqlQuery> ingredientLocalSource;

	public RecipeRepository(Context context, MemoryCache<Recipe> cacheSource, DefaultSource<Recipe, SqlQuery> localSource, DefaultSource<Recipe, URL> remoteSource) {
		super(cacheSource, localSource, remoteSource);
		this.context = context;
	}

	@Override
	public List<Recipe> getRecipeList(int limit, int offset) throws ConnectionException {
		List<Recipe> recipeList = mRemoteSource.recover(new RecipeQuery(limit, offset));
		if (!recipeList.isEmpty()) {
			saveRecipeOrRecoverIdFromLocalSource(limit, offset, recipeList);
		}

		return recipeList;
	}

	private void saveRecipeOrRecoverIdFromLocalSource(int limit, int offset, List<Recipe> recipeList) throws ConnectionException {
		for (Recipe recipe : recipeList) {
			String idFromAPI = recipe.getIdFromAPI();
			String[] selectionArgs = {idFromAPI};
			SqlQuery sqlQuery = SqlQuery.builder()
					.setSelection(BakingContract.RecipeEntry.COLUMN_ID_FROM_API + "=?")
					.setUri(BakingContract.RecipeEntry.CONTENT_URI)
					.setSelectionArgs(selectionArgs)
					.build();
			BaseSqlSpecification sqlSpecification = new BaseSqlSpecification(sqlQuery, limit, offset);
			List<Recipe> recipeListByIdFromAPI = mLocalSource.recover(sqlSpecification);

			boolean recipeByIdWasFound = !recipeListByIdFromAPI.isEmpty();
			if (recipeByIdWasFound) {
				Recipe recipeWithId = recipeListByIdFromAPI.get(0);
				recipe.setId(recipeWithId.getId());
			} else {
				Recipe recipeWithId = mLocalSource.create(recipe);
				recipe.setId(recipeWithId.getId());
			}
		}
	}

	private static void notifyWidgetToUpdate(Context context) {
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		ComponentName thisWidget = new ComponentName(Objects.requireNonNull(context), BakingWidget.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetRoot);

		BakingWidget.sendUpdateBroadcastToAllWidgets(context);
		Log.d(TAG, "Disparado update para widgets: " + Arrays.toString(appWidgetIds));
	}

	private void saveIngredientInLocalSource(List<Ingredient> ingredientList) {
		for (Ingredient ingredient : ingredientList) {
			Ingredient ingredientWithId = ingredientLocalSource.create(ingredient);
			ingredient.setId(ingredientWithId.getId());
		}
		notifyWidgetToUpdate(context);
	}

	@Override
	public List<Ingredient> getIngredientListByRecipe(Long recipeId, int limit, int offset) throws ConnectionException {
		List<Ingredient> ingredientList = mRemoteIngredientSource.recover(new RecipeRelationsRemoteQuery(limit, offset, recipeId));
		if (!ingredientList.isEmpty()) {
			SqlQuery sqlQuery = SqlQuery.builder()
					.setUri(BakingContract.IngredientEntry.buildIngredientByRecipeIdUri(recipeId))
					.build();
			QuerySpecification<SqlQuery> querySpec = new BaseSqlSpecification(sqlQuery, limit, offset);
			List<Ingredient> ingredientListWithID = ingredientLocalSource.recover(querySpec);

			boolean ingredientsByRecipeNotFound = ingredientListWithID.isEmpty();
			if (ingredientsByRecipeNotFound) {
				saveIngredientInLocalSource(ingredientList); // Sera usado em widget
			} else {
				ingredientList = ingredientListWithID;
			}
		}

		return ingredientList;
	}

	@Override
	public List<Step> getStepListByRecipe(Long recipeId, int limit, int offset) throws ConnectionException {
		return mRemoteStepSource.recover(new RecipeRelationsRemoteQuery(limit, offset, recipeId));
	}

	@Override
	public Step recoverStep(Long stepId) throws ConnectionException {
		return mRemoteStepSource.recover(stepId);
	}

	public void setRemoteIngredientSource(DefaultSource<Ingredient, URL> remoteIngredientSource) {
		this.mRemoteIngredientSource = remoteIngredientSource;
	}

	public void setRemoteStepSource(DefaultSource<Step, URL> remoteStepSource) {
		this.mRemoteStepSource = remoteStepSource;
	}

	public void setIngredientLocalSource(DefaultSource<Ingredient, SqlQuery> ingredientLocalSource) {
		this.ingredientLocalSource = ingredientLocalSource;
	}
}
