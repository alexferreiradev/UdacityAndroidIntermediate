package com.alex.baking.app.data.repository.movie;

import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.repository.BaseRepository;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.cache.MemoryCache;
import com.alex.baking.app.data.source.queryspec.remote.RecipeQuery;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class RecipeRepository extends BaseRepository<Recipe> implements RecipeRepositoryContract {
	private static final String TAG = RecipeRepository.class.getSimpleName();

	public RecipeRepository(MemoryCache<Recipe> cacheSource, DefaultSource<Recipe> localSource, DefaultSource<Recipe> remoteSource) {
		super(cacheSource, localSource, remoteSource);
	}

	@Override
	public List<Recipe> getRecipeList(int limit, int offset) throws ConnectionException {
		return mRemoteSource.recover(new RecipeQuery(limit, offset));
	}

	@Override
	public List<Ingredient> getIngredientListByRecipe(Long recipeId, int limit, int offset) {
		return null;
	}

	@Override
	public List<Step> getStepListByRecipe(Long recipeId, int limit, int offset) {
		return null;
	}
}
