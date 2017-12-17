package com.alex.popularmovies.app.data.repository;

import com.alex.popularmovies.app.data.CRUD;
import com.alex.popularmovies.app.data.model.BaseModel;

/**
 * Created by Alex on 02/04/2017.
 */

public interface DefaultRepository<ModelType extends BaseModel> extends CRUD<ModelType> {

}
