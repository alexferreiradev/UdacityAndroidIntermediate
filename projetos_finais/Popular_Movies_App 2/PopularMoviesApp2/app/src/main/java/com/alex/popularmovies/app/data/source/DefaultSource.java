package com.alex.popularmovies.app.data.source;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;
import com.alex.popularmovies.app.data.source.remote.network.exception.ConnectionException;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public interface DefaultSource<ModelType extends BaseModel> {

	ModelType create(ModelType model) throws SourceException;

	ModelType recover(Long id) throws SourceException, ConnectionException;

	List<ModelType> recover(QuerySpecification specification) throws SourceException, ConnectionException;

	ModelType update(ModelType model) throws SourceException;

	ModelType delete(ModelType model) throws SourceException;
}
