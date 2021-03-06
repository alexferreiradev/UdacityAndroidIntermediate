package com.alex.baking.app.data.source.cache;

import com.alex.baking.app.data.model.BaseModel;
import com.alex.baking.app.data.source.DefaultSource;

import java.net.URL;
import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface MemoryCache<ModelType extends BaseModel> extends DefaultSource<ModelType, URL> {

	boolean isEmpty();

	boolean isDirty();

	void setDirty(boolean isDirty);

	void updateCacheTo(List<ModelType> models);

	void addAllCache(List<ModelType> models, int offset);

	void removeAllCache(List<ModelType> models);

	List<ModelType> getCache();
}
