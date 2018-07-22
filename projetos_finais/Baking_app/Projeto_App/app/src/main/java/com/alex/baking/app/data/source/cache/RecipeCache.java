package com.alex.baking.app.data.source.cache;

import android.util.Log;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;

import java.util.ArrayList;
import java.util.List;

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
	public Recipe create(Recipe model) {
		throw new RuntimeException("Cache não suporta este método.");
	}

	@Override
	public Recipe recover(Long id) {
		if (id == null || id < 0) {
			throw new RuntimeException("Id inválido, nulo ou negativo");
		}

		if (mCache == null) {
			Log.w(TAG, "Tentando busca em cache que não possui dados.");
			throw new RuntimeException("Cache não inicializado");
		}

		for (Recipe Recipe : mCache) {
			if (Recipe.getId().equals(id)) {
				return Recipe;
			}
		}

		throw new RuntimeException("Id não encontrado");
	}

	@Override
	public List<Recipe> recover(QuerySpecification specification) {
		throw new RuntimeException("Cache não suporta este método nesta versao.");
	}

	@Override
	public Recipe update(Recipe model) {
		throw new RuntimeException("Cache não suporta este método nesta versao.");
	}

	@Override
	public Recipe delete(Recipe model) {
		return mCache.remove(mCache.indexOf(model));
	}

	@Override
	protected void createCache() {
		mCache = new ArrayList<>();
	}

	@Override
	public boolean isEmpty() {
		return mCache.isEmpty();
	}

	@Override
	public void addAllCache(List<Recipe> data, int offset) {
		mCache.addAll(data);
	}

	@Override
	public void removeAllCache(List<Recipe> data) {
		mCache.removeAll(data);
	}
}
