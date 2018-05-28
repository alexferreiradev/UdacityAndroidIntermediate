package com.alex.popularmovies.app.data.source.remote;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.source.DefaultSource;
import com.alex.popularmovies.app.data.source.exception.NullConnectionException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public abstract class BaseRemoteSource<ModelType extends BaseModel> implements DefaultSource<ModelType> {

	public BaseRemoteSource() {
	}

	protected String readStream(InputStream stream) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
		StringBuilder stringBuilder = new StringBuilder();
		do {
			String line = bufferedReader.readLine();
			if (line == null || !line.isEmpty()) {
				stringBuilder.append(line);
			}
		} while (bufferedReader.ready());

		return stringBuilder.toString();
	}

	protected void validateConnection(HttpURLConnection connection, URL url) throws NullConnectionException {
		if (connection == null) {
			throw new NullConnectionException("Não pode ser aberta conexão para URL: " + url);
		}
	}

	protected abstract List<ModelType> getModelListFromJsonResults(String movieJsonString);

	protected abstract ModelType parseJSONToModel(JSONObject movieJsonObject) throws Exception;
}
