package com.alex.baking.app.data.repository.recipe;

import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.BaseRepository;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.cache.MemoryCache;
import com.alex.baking.app.data.source.queryspec.remote.RecipeQuery;
import com.alex.baking.app.data.source.queryspec.remote.RecipeRelationsQuery;
import com.alex.baking.app.data.source.queryspec.sql.BaseSqlSpecification;
import com.alex.baking.app.data.source.queryspec.sql.SqlQuery;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.data.source.sql.BakingContract;

import java.net.URL;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class RecipeRepository extends BaseRepository<Recipe> implements RecipeRepositoryContract {
	private static final String TAG = RecipeRepository.class.getSimpleName();
	private DefaultSource<Ingredient, URL> mRemoteIngredientSource;
	private DefaultSource<Step, URL> mRemoteStepSource;

	public RecipeRepository(MemoryCache<Recipe> cacheSource, DefaultSource<Recipe, SqlQuery> localSource, DefaultSource<Recipe, URL> remoteSource) {
		super(cacheSource, localSource, remoteSource);
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

			boolean recipeByIdWasFound = recipeListByIdFromAPI.isEmpty();
			if (recipeByIdWasFound) {
				Recipe recipeWithId = mLocalSource.create(recipe);
				recipe.setId(recipeWithId.getId());
			} else {
				Recipe recipeWithId = recipeListByIdFromAPI.get(0);
				recipe.setId(recipeWithId.getId());
			}
		}
	}

	@Override
	public List<Ingredient> getIngredientListByRecipe(Long recipeId, int limit, int offset) throws ConnectionException {
		return mRemoteIngredientSource.recover(new RecipeRelationsQuery(limit, offset, recipeId));
	}

	@Override
	public List<Step> getStepListByRecipe(Long recipeId, int limit, int offset) throws ConnectionException {
		return mRemoteStepSource.recover(new RecipeRelationsQuery(limit, offset, recipeId));
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
}
