package com.alex.popularmovies.app.data.source;

import com.alex.popularmovies.app.data.model.BaseModel;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public interface DefaultSource<ModelType extends BaseModel> {

    public ModelType insert(ModelType data);
    public void update(ModelType data);
    public void delete(ModelType data);
    public List<ModelType> query(BaseQuerySpecification specification);
}
