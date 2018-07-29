package com.alex.baking.app.data.source.cache;

import com.alex.baking.app.data.model.Recipe;

import java.util.ArrayList;

/**
 * Created by Alex on 02/04/2017.
 */

public class RecipeCache extends BaseCache<Recipe> {
	private static final String TAG = RecipeCache.class.getSimpleName();
	private static RecipeCache mInstance;

	/**
	 * Utilize o RecipeCache.getInstance()
	 */
	private RecipeCache() {
		super();
	}

	/**
	 * Cria uma instancia de cache para filmes.
	 */
	public synchronized static RecipeCache getInstance() {
		if (mInstance == null) {
			mInstance = new RecipeCache();
		}

		return mInstance;
	}

	@Override
	protected void createCache() {
		mCache = new ArrayList<>();
	}

	@Override
	protected String getIndentificadorByModel(Recipe model) {
		return String.valueOf(model.getId());
	}
}
