package com.alex.popularmovies.app.data.source.remote;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.source.DefaultSource;
import com.alex.popularmovies.app.data.source.remote.network.NetworkResource;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public abstract class BaseRemoteSource<ModelType extends BaseModel> implements DefaultSource<ModelType> {

	protected NetworkResource mNetworkResource;

	public BaseRemoteSource(NetworkResource networkResource) {
		mNetworkResource = networkResource;
	}

	protected abstract List<ModelType> getModelListFromJsonResults(String movieJsonString);

	protected abstract ModelType parseJSONToModel(JSONObject movieJsonObject) throws Exception;
}
