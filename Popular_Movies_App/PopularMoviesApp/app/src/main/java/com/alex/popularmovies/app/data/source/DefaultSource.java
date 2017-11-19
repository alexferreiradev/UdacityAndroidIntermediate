package com.alex.popularmovies.app.data.source;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.source.exception.SourceException;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public interface DefaultSource<ModelType extends BaseModel> {

    public ModelType insert(ModelType data) throws SourceException;

    public void update(ModelType data) throws SourceException;

    public void delete(ModelType data) throws SourceException;

    public List<ModelType> query(BaseQuerySpecification specification) throws SourceException;

    List<ModelType> list(String sortOrderType) throws SourceException;
}
