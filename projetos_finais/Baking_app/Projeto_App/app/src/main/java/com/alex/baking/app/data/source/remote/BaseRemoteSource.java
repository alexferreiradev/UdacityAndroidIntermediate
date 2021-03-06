package com.alex.baking.app.data.source.remote;

import com.alex.baking.app.data.model.BaseModel;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.remote.network.NetworkResource;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

@SuppressWarnings("WeakerAccess")
public abstract class BaseRemoteSource<ModelType extends BaseModel> implements DefaultSource<ModelType, URL> {

	protected NetworkResource mNetworkResource;

	public BaseRemoteSource(NetworkResource networkResource) {
		mNetworkResource = networkResource;
	}

	protected abstract List<ModelType> getModelListFromJsonResults(String jsonString);

	protected abstract ModelType parseJSONToModel(JSONObject jsonObject) throws Exception;
}
