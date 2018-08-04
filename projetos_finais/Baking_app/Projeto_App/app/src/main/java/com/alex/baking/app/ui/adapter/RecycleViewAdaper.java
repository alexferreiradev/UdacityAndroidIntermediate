package com.alex.baking.app.ui.adapter;

import com.alex.baking.app.data.model.BaseModel;

import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface RecycleViewAdaper<ModelType extends BaseModel> {

	void addAllModel(List<ModelType> models);

	void removeAllModel(List<ModelType> models);
}
