package com.alex.baking.app.data.repository.recipe;

import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.BaseRepository;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.cache.MemoryCache;
import com.alex.baking.app.data.source.queryspec.remote.RecipeQuery;
import com.alex.baking.app.data.source.queryspec.remote.RecipeRelationsQuery;
import com.alex.baking.app.data.source.queryspec.sql.SqlQuery;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;

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
		return mRemoteSource.recover(new RecipeQuery(limit, offset));
	}

	@Override
	public List<Ingredient> getIngredientListByRecipe(Long recipeId, int limit, int offset) throws ConnectionException {
		return mRemoteIngredientSource.recover(new RecipeRelationsQuery(limit, offset, recipeId));
	}

	@Override
	public List<Step> getStepListByRecipe(Long recipeId, int limit, int offset) throws ConnectionException {
		return mRemoteStepSource.recover(new RecipeRelationsQuery(limit, offset, recipeId));
	}

	public void setRemoteIngredientSource(DefaultSource<Ingredient, URL> remoteIngredientSource) {
		this.mRemoteIngredientSource = remoteIngredientSource;
	}

	public void setRemoteStepSource(DefaultSource<Step, URL> remoteStepSource) {
		this.mRemoteStepSource = remoteStepSource;
	}
}
