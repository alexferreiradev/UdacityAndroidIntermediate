package com.alex.popularmovies.app.data.repository;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.source.DefaultSource;
import com.alex.popularmovies.app.data.source.cache.BaseCache;
import com.alex.popularmovies.app.data.source.cache.MemoryCache;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public abstract class BaseRepository<ModelType extends BaseModel> implements DefaultRepository<ModelType> {

	protected MemoryCache<ModelType> mCacheSource;
	protected DefaultSource<ModelType> mLocalSource;
	protected DefaultSource<ModelType> mRemoteSource;

	public BaseRepository(BaseCache<ModelType> cacheSource, DefaultSource<ModelType> localSource, DefaultSource<ModelType> remoteSource) {
		this.mCacheSource = cacheSource;
		this.mLocalSource = localSource;
		this.mRemoteSource = remoteSource;
	}

	protected abstract void createCache(List<ModelType> data);

	protected abstract void destroyCache(List<ModelType> data);

	protected abstract void updateCache(List<ModelType> data, int offset);

}
