package com.alex.popularmovies.app.data.source.cache;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.source.DefaultSource;

import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface MemoryCache<ModelType extends BaseModel> extends DefaultSource<ModelType> {

    boolean isNewCache();

    boolean isDirty();

    void setDirty(boolean isDirty);

    void updateCacheTo(List<ModelType> models);

    void addAllCache(List<ModelType> models, int offset);

    void removeAllCache(List<ModelType> models);
}
