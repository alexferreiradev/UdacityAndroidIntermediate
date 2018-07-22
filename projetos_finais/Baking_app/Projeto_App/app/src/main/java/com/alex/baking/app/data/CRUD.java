package com.alex.baking.app.data;

import com.alex.baking.app.data.model.BaseModel;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;

/**
 * Created by Alex on 17/12/2017.
 */

public interface CRUD<ModelType extends BaseModel> {

	ModelType create(ModelType model);

	ModelType recover(Long id) throws ConnectionException;

	ModelType update(ModelType model);

	ModelType delete(ModelType model);

}
