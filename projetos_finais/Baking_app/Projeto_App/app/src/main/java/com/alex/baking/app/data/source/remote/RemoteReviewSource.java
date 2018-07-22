package com.alex.baking.app.data.source.remote;

import android.util.Log;
import com.alex.baking.app.data.model.Review;
import com.alex.baking.app.data.source.exception.SourceException;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.remote.network.NetworkResource;
import com.alex.baking.app.data.source.remote.network.exception.NetworkResourceException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class RemoteReviewSource extends BaseRemoteSource<Review> {
	private static final String TAG_NAME = RemoteReviewSource.class.getSimpleName();

	private static final String REVIEW_JSON_KEY_AUTHOR = "author";
	private static final String REVIEW_JSON_KEY_CONTENT = "content";
	private static final String REVIEW_JSON_KEY_URL = "url";

	public RemoteReviewSource(NetworkResource networkResource) {
		super(networkResource);
	}

	@Override
	public Review create(Review model) throws SourceException {
		throw new SourceException("Método não disponível para esta versao.");
	}

	@Override
	public Review recover(Long id) throws SourceException {
		throw new SourceException("Método não disponível para esta versao.");
	}

	@Override
	public List<Review> recover(QuerySpecification specification) throws SourceException {
		List<Review> reviewList = new ArrayList<>();

		try {
			URL url = (URL) specification.getQuery();
			String stringResourceFromURL = mNetworkResource.getStringResourceFromURL(url);
			if (stringResourceFromURL != null && !stringResourceFromURL.isEmpty()) {
				reviewList.addAll(getModelListFromJsonResults(stringResourceFromURL));
			} else {
				Log.w(TAG_NAME, "Erro de resource nao valido: " + stringResourceFromURL);
				throw new SourceException("Não foi encontrado nenhum recurso valido para URL: " + url);
			}
		} catch (NetworkResourceException e) {
			throw new SourceException("Erro ao tentar criar string de recurso na internet.", e);
		} catch (Exception e) {
			throw new SourceException("Erro desconhecido ao tentar recuperar filmes da api.", e);
		}

		return reviewList;
	}

	@Override
	public Review update(Review model) throws SourceException {
		throw new SourceException("Método não disponível para esta versao.");
	}

	@Override
	public Review delete(Review model) throws SourceException {
		throw new SourceException("Método não disponível para esta versao.");
	}

	@Override
	protected List<Review> getModelListFromJsonResults(String jsonString) {
		List<Review> reviewList = new ArrayList<>();

		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray resultArray = jsonObject.getJSONArray(JSON_KEY_RESULTS);
			for (int i = 0; i < resultArray.length(); i++) {
				JSONObject arrayItemObj = (JSONObject) resultArray.get(i);
				Review review = parseJSONToModel(arrayItemObj);
				reviewList.add(review);
			}
		} catch (Exception e) {
			Log.e(TAG_NAME, "Erro ao fazer parse de JSON: ", e);
		}

		return reviewList;
	}

	@Override
	protected Review parseJSONToModel(JSONObject jsonObject) throws Exception {
		Review review = new Review();
		review.setIdFromAPI(jsonObject.getString(JSON_KEY_ID));
		review.setAuthor(jsonObject.getString(REVIEW_JSON_KEY_AUTHOR));
		review.setContent(jsonObject.getString(REVIEW_JSON_KEY_CONTENT));
		review.setUrl(jsonObject.getString(REVIEW_JSON_KEY_URL));

		return review;
	}
}
