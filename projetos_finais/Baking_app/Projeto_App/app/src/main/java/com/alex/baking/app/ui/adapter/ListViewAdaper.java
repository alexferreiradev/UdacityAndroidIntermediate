package com.alex.baking.app.ui.adapter;

import android.widget.ListAdapter;
import com.alex.baking.app.data.model.BaseModel;

import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface ListViewAdaper<ModelType extends BaseModel> extends ListAdapter {

	void addAllModel(List<ModelType> models);

	void removeAllModel(List<ModelType> models);
}
