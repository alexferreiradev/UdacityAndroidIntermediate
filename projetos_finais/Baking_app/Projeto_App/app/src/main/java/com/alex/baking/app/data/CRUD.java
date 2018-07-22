package com.alex.baking.app.data;

import com.alex.baking.app.data.exception.DataException;
import com.alex.baking.app.data.model.BaseModel;

/**
 * Created by Alex on 17/12/2017.
 */

public interface CRUD<ModelType extends BaseModel> {

	ModelType create(ModelType model) throws DataException;

	ModelType recover(Long id) throws DataException;

	ModelType update(ModelType model) throws DataException;

	ModelType delete(ModelType model) throws DataException;

}
