package com.alex.popularmovies.app.data.source.cache;

import com.alex.popularmovies.app.data.model.BaseModel;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public abstract class BaseCache<ModelType extends BaseModel> implements MemoryCache<ModelType> {

    protected Boolean isDirty;
    protected List<ModelType> mCache;

    BaseCache() {
        createCache();
        isDirty = true;
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

    protected abstract void createCache();

}
