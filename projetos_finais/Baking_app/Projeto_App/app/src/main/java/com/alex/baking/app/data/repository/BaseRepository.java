package com.alex.baking.app.data.repository;

import android.util.Log;
import com.alex.baking.app.data.model.BaseModel;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.cache.MemoryCache;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class BaseRepository<ModelType extends BaseModel> implements DefaultRepository<ModelType> {

	private final String TAG = this.getClass().getSimpleName();

	@SuppressWarnings("WeakerAccess")
	protected MemoryCache<ModelType> mCacheSource;
	@SuppressWarnings("WeakerAccess")
	protected DefaultSource<ModelType> mLocalSource;
	@SuppressWarnings("WeakerAccess")
	protected DefaultSource<ModelType> mRemoteSource;

	public BaseRepository(MemoryCache<ModelType> cacheSource, DefaultSource<ModelType> localSource, DefaultSource<ModelType> remoteSource) {
		this.mCacheSource = cacheSource;
		this.mLocalSource = localSource;
		this.mRemoteSource = remoteSource;
	}

	@SuppressWarnings("WeakerAccess")
	protected void createCache(List<ModelType> data) {
		updateCache(data, 0);
	}

	@SuppressWarnings("WeakerAccess")
	protected void destroyCache(List<ModelType> data) {
		mCacheSource.setDirty(true);
	}

	@SuppressWarnings("WeakerAccess")
	protected void updateCache(List<ModelType> data, int offset) {
		if (mCacheSource.isDirty() || offset == 0) {
			mCacheSource.updateCacheTo(data);
		} else {
			mCacheSource.addAllCache(data, offset);
		}
	}

	@Override
	public void setCacheToDirty() {
		Log.i(TAG, "setando cache para dirty");
		mCacheSource.setDirty(true);
	}

	@Override
	public boolean hasCache() {
		return !mCacheSource.isEmpty() && !mCacheSource.isDirty();
	}

	@Override
	public List<ModelType> getCurrentCache() {
		return mCacheSource.getCache();
	}

	@Override
	public ModelType create(ModelType model) {
		if (model == null) {
			return null;
		}

		final String modelSimpleName = model.getClass().getSimpleName();
		Long id = model.getId();
		if (id == null) {
			return mLocalSource.create(model);
		} else {
			throw new RuntimeException(modelSimpleName + " já existe na base local.");
		}
	}

	@Override
	public ModelType recover(Long id) throws ConnectionException {
		if (id == null) {
			return null;
		}

		if (!mCacheSource.isDirty()) {
			ModelType modelRecovered = mCacheSource.recover(id);
			if (modelRecovered != null) {
				return modelRecovered;
			} else {
				Log.w(TAG, "Não foi encontrado dado com ID: " + id + " no cache, tentando em outra origem");
				mCacheSource.setDirty(true);
			}
		}

		ModelType modelRecovered = mLocalSource.recover(id);
		if (modelRecovered == null) {
			Log.d(TAG, "Não foi encontrado dado com ID: " + id + " no banco local");
		}

		return modelRecovered;
	}

	@Override
	public ModelType update(ModelType model) {
		if (model == null) {
			return null;
		}
		final String modelSimpleName = model.getClass().getSimpleName();

		if (model.getId() == null) {
			throw new RuntimeException(modelSimpleName + " não possui ID valido para ser atualizado");
		}

		return mLocalSource.update(model);
	}

	@Override
	public ModelType delete(ModelType model) {
		if (model == null) {
			return null;
		}

		final String modelSimpleName = model.getClass().getSimpleName();
		Long id = model.getId();
		if (id == null) {
			return mLocalSource.delete(model);
		} else {
			throw new RuntimeException(modelSimpleName + " já existe na base local.");
		}
	}
}
