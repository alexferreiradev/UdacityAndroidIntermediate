package com.alex.baking.app.data.source.remote;

import android.util.Log;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.MeasureType;
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

public class IngredientSource extends BaseRemoteSource<Ingredient> {

	private static final String TAG = IngredientSource.class.getSimpleName();

	private Long recipeId;

	public IngredientSource(NetworkResource networkResource) {
		super(networkResource);
	}

	@Override
	protected List<Ingredient> getModelListFromJsonResults(String jsonString) {
		List<Ingredient> ingredientList = new ArrayList<>();

		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject recipeJSONObj = jsonArray.getJSONObject(i);
				if (recipeJSONObj.getLong("id") == recipeId) {
					JSONArray ingredients = recipeJSONObj.getJSONArray("ingredients");
					for (int j = 0; j < ingredients.length(); j++) {
						JSONObject ingredientObj = ingredients.getJSONObject(j);
						Ingredient Ingredient = parseJSONToModel(ingredientObj);

						ingredientList.add(Ingredient);
					}
				}
			}
		} catch (JSONException e) {
			String msg = "Erro de json ao fazer parse de JSON";
			Log.e(TAG, msg, e);
			throw new RuntimeException(msg, e);
		} catch (Exception e) {
			String msg = "Erro desconhecido ao fazer parse de JSON";
			Log.e(TAG, msg, e);
			throw new RuntimeException(msg, e);
		}

		return ingredientList;
	}

	@Override
	protected Ingredient parseJSONToModel(JSONObject jsonObject) throws Exception {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(null);
		ingredient.setIngredient(jsonObject.getString("ingredient"));
		String measure = jsonObject.getString("measure");
		ingredient.setMeasure(MeasureType.valueOf(measure.toUpperCase()));
		ingredient.setQuantity(jsonObject.getDouble("quantity"));

		return ingredient;
	}

	@Override
	public Ingredient create(Ingredient model) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public Ingredient recover(Long id) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public List<Ingredient> recover(QuerySpecification specification) throws ConnectionException {
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
	public Ingredient update(Ingredient model) {
		throw new RuntimeException("Não implementado");
	}

	@Override
	public Ingredient delete(Ingredient model) {
		throw new RuntimeException("Não implementado");
	}
}
