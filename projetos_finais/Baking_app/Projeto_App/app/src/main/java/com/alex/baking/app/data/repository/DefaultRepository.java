package com.alex.baking.app.data.repository;

import com.alex.baking.app.data.CRUD;
import com.alex.baking.app.data.model.BaseModel;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public interface DefaultRepository<ModelType extends BaseModel> extends CRUD<ModelType> {

	void setCacheToDirty();

	boolean hasCache();

	List<ModelType> getCurrentCache();

}
