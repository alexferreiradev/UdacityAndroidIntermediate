package com.alex.baking.app.data.source.cache;

import android.util.Log;
import com.alex.baking.app.data.model.BaseModel;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class BaseCache<ModelType extends BaseModel> implements MemoryCache<ModelType> {

	private static final String TAG = BaseCache.class.getSimpleName();

	private Boolean isDirty;
	protected List<ModelType> mCache;

	public BaseCache() {
		createCache();
		isDirty = true;
	}

	protected void createCache() {
		mCache = new ArrayList<>();
	}

	/**
	 * Sobrescreva caso seu modelo não possa usar ID como comparador no cache.
	 *
	 * @param model extends @link{BaseModel}
	 * @return String.valueOf(model.getId ());
	 */
	protected String getIndentificadorByModel(ModelType model) {
		return String.valueOf(model.getId());
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	public void updateCacheTo(List<ModelType> newValues) {
		createCache();
		addAllCache(newValues, 0);
		isDirty = false;
	}

	@Override
	public List<ModelType> getCache() {
		return mCache;
	}

	@Override
	public ModelType create(ModelType model) {
		throw new RuntimeException("Cache não suporta este método.");
	}

	@Override
	public ModelType recover(Long id) {
		if (id == null || id < 0) {
			throw new RuntimeException("Id inválido, nulo ou negativo");
		}

		if (mCache == null) {
			Log.w(TAG, "Tentando busca em cache que não possui dados.");
			throw new RuntimeException("Cache não inicializado");
		}

		for (ModelType model : mCache) {
			if (getIndentificadorByModel(model).equals(String.valueOf(id))) {
				return model;
			}
		}

		return null;
	}

	@Override
	public List<ModelType> recover(QuerySpecification specification) {
		throw new RuntimeException("Cache não suporta este método nesta versao.");
	}

	@Override
	public ModelType update(ModelType model) {
		throw new RuntimeException("Cache não suporta este método nesta versao.");
	}

	@Override
	public ModelType delete(ModelType model) {
		return mCache.remove(mCache.indexOf(model));
	}

	@Override
	public boolean isEmpty() {
		return mCache.isEmpty();
	}

	@Override
	public void addAllCache(List<ModelType> data, int offset) {
		mCache.addAll(data);
	}

	@Override
	public void removeAllCache(List<ModelType> data) {
		mCache.removeAll(data);
	}
}
