package com.alex.popularmovies.app.data.source.remote;

import android.util.Log;
import com.alex.popularmovies.app.data.model.Video;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;
import com.alex.popularmovies.app.data.source.remote.network.NetworkResource;
import com.alex.popularmovies.app.data.source.remote.network.exception.NetworkResourceException;
import com.alex.popularmovies.app.data.source.remote.network.exception.NullConnectionException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class RemoteVideoSource extends BaseRemoteSource<Video> {
	private static final String TAG_NAME = RemoteVideoSource.class.getSimpleName();

	private static final String VIDEO_JSON_KEY_AUTHOR = "author";

	public RemoteVideoSource(NetworkResource networkResource) {
		super(networkResource);
	}

	@Override
	public Video create(Video model) throws SourceException {
		throw new SourceException("Método não disponível para esta versao.");
	}

	@Override
	public Video recover(Long id) throws SourceException, NullConnectionException {
		throw new SourceException("Método não disponível para esta versao.");
	}

	@Override
	public List<Video> recover(QuerySpecification specification) throws SourceException {
		List<Video> videoList = new ArrayList<>();

		try {
			URL url = (URL) specification.getQuery();
			String stringResourceFromURL = mNetworkResource.getStringResourceFromURL(url);
			if (stringResourceFromURL != null && !stringResourceFromURL.isEmpty()) {
				videoList.addAll(getModelListFromJsonResults(stringResourceFromURL));
			} else {
				Log.w(TAG_NAME, "Erro de resource nao valido: " + stringResourceFromURL);
				throw new SourceException("Não foi encontrado nenhum recurso valido para URL: " + url);
			}
		} catch (NetworkResourceException e) {
			throw new SourceException("Erro ao tentar criar string de recurso na internet.", e);
		} catch (Exception e) {
			throw new SourceException("Erro desconhecido ao tentar recuperar videos da api.", e);
		}

		return videoList;
	}

	@Override
	public Video update(Video model) throws SourceException {
		throw new SourceException("Método não disponível para esta versao.");
	}

	@Override
	public Video delete(Video model) throws SourceException {
		throw new SourceException("Método não disponível para esta versao.");
	}

	@Override
	protected List<Video> getModelListFromJsonResults(String jsonString) {
		List<Video> videoList = new ArrayList<>();

		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray resultArray = jsonObject.getJSONArray(JSON_KEY_RESULTS);
			for (int i = 0; i < resultArray.length(); i++) {
				JSONObject arrayItemObj = (JSONObject) resultArray.get(i);
				Video video = parseJSONToModel(arrayItemObj);
				videoList.add(video);
			}
		} catch (Exception e) {
			Log.e(TAG_NAME, "Erro ao fazer parse de JSON: ", e);
		}

		return videoList;
	}

	@Override
	protected Video parseJSONToModel(JSONObject jsonObject) throws Exception {
		Video video = new Video();
		video.setId(jsonObject.getLong(JSON_KEY_ID));
//		video.setName(jsonObject.getString(REVIEW_JSON_KEY_AUTHOR)); // TODO: 05/06/18 extrair keys e types from json
//		video.setSite(jsonObject.getString(REVIEW_JSON_KEY_AUTHOR));
//		video.setSize(jsonObject.getString(REVIEW_JSON_KEY_AUTHOR));
//		video.setType(jsonObject.getString(REVIEW_JSON_KEY_AUTHOR));
//		video.setIso639_1(jsonObject.getString(REVIEW_JSON_KEY_AUTHOR));
//		video.setIso3166_1(jsonObject.getString(REVIEW_JSON_KEY_AUTHOR));

		return video;
	}
}
