package com.alex.popularmovies.app.data.repository;

import android.content.Context;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.source.DefaultSource;
import com.alex.popularmovies.app.data.source.cache.BaseCache;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public abstract class BaseRepository<ModelType extends BaseModel> implements DefaultRepository<ModelType> {

    protected BaseCache<ModelType> mCacheSource;
    protected DefaultSource<ModelType> mLocalSource;
    protected DefaultSource<ModelType> mRemoteSource;

    public abstract MovieRepository reCreateInstance(Context context);
    protected abstract void createCache(List<ModelType> data);
    protected abstract void destroyCache(List<ModelType> data);
    protected abstract void updateCache(List<ModelType> data);

}
