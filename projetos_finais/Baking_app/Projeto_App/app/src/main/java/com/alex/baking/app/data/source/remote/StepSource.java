package com.alex.baking.app.data.source.remote;

import android.util.Log;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.remote.network.NetworkResource;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepSource extends BaseRemoteSource<Step> {

	private static final String TAG = StepSource.class.getSimpleName();

	private Long recipeId;

	public StepSource(NetworkResource networkResource) {
		super(networkResource);
	}

	@Override
	protected List<Step> getModelListFromJsonResults(String jsonString) {
		List<Step> StepList = new ArrayList<>();

		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject recipeJSONObj = jsonArray.getJSONObject(i);
				if (recipeJSONObj.getLong("id") == recipeId) {
					JSONArray stepsJsonArray = recipeJSONObj.getJSONArray("steps");
					for (int j = 0; j < stepsJsonArray.length(); j++) {
						JSONObject ingredientObj = stepsJsonArray.getJSONObject(i);
						Step Step = parseJSONToModel(ingredientObj);

						StepList.add(Step);
					}
				}
			}
		} catch (JSONException e) {
			String msg = "Erro ao fazer parse de JSON";
			Log.e(TAG, msg, e);
			throw new RuntimeException(msg, e);
		} catch (Exception e) {
			String msg = "Erro ao fazer parse de JSON";
			Log.e(TAG, msg, e);
			throw new RuntimeException(msg, e);
		}

		return StepList;
	}

	@Override
	protected Step parseJSONToModel(JSONObject jsonObject) throws Exception {
		Step step = new Step();
		step.setId(jsonObject.getLong("id"));
		step.setDescription(jsonObject.getString("description"));
		step.setShortDescription(jsonObject.getString("shortDescription"));
		step.setThumbnailURL(jsonObject.getString("shortDescription"));
		step.setThumbnailURL(jsonObject.getString("thumbnailURL"));
		step.setVideoURL(jsonObject.getString("videoURL"));

		return step;
	}

	@Override
	public Step create(Step model) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public Step recover(Long id) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public List<Step> recover(QuerySpecification specification) throws ConnectionException {
		URL url = (URL) specification.getQuery();

		String query = url.getQuery();
		Pattern compile = Pattern.compile("(?<=id=)\\d+");
		Matcher matcher = compile.matcher(query);
		boolean match = matcher.find();
		String idFromQuery = match ? matcher.group() : "-1";
		recipeId = Long.valueOf(idFromQuery);
		String stringJSON = mNetworkResource.getStringResourceFromURL(url);

		return getModelListFromJsonResults(stringJSON);
	}

	@Override
	public Step update(Step model) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public Step delete(Step model) {
		throw new RuntimeException("Não implementado");
	}
}
