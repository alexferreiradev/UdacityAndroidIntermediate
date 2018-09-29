package com.alex.baking.app.data.source.remote;

import android.util.Log;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.remote.network.NetworkResource;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeSource extends BaseRemoteSource<Recipe> {

	private static final String TAG = RecipeSource.class.getSimpleName();

	public RecipeSource(NetworkResource networkResource) {
		super(networkResource);
	}

	@Override
	protected List<Recipe> getModelListFromJsonResults(String jsonString) {
		List<Recipe> recipeList = new ArrayList<>();

		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject recipeJSONObj = jsonArray.getJSONObject(i);
				Recipe recipe = parseJSONToModel(recipeJSONObj);

				recipeList.add(recipe);
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

		return recipeList;
	}

	@Override
	protected Recipe parseJSONToModel(JSONObject jsonObject) throws Exception {
		Recipe recipe = new Recipe();
		recipe.setIdFromAPI(jsonObject.getString("id"));
		recipe.setNome(jsonObject.getString("name"));
		recipe.setServing(jsonObject.getString("servings"));
		recipe.setImage(jsonObject.getString("image"));

		return recipe;
	}

	@Override
	public Recipe create(Recipe model) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public Recipe recover(Long id) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public List<Recipe> recover(QuerySpecification specification) throws ConnectionException {
		URL url = (URL) specification.getQuery();
		String stringJSON = mNetworkResource.getStringResourceFromURL(url);
		return getModelListFromJsonResults(stringJSON);
	}

	@Override
	public Recipe update(Recipe model) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public Recipe delete(Recipe model) {
		throw new RuntimeException("Não implementado");
	}
}
