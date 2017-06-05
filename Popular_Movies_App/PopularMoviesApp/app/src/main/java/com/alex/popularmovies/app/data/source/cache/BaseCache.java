package com.alex.popularmovies.app.data.source.cache;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.source.DefaultSource;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public abstract class BaseCache<ModelType extends BaseModel> implements DefaultSource<ModelType> {

    protected Boolean isDirty;
    protected List<ModelType> mCache;

    protected BaseCache() {
        createCache();
        isDirty = false;
    }

    public Boolean getDirty() {
        return isDirty;
    }

    public void setDirty(Boolean dirty) {
        isDirty = dirty;
    }

    protected abstract void createCache();
    protected abstract void destroyCache();
    protected abstract void addCache(List<ModelType> data);
    protected abstract void removeCache(List<ModelType> data);

}
