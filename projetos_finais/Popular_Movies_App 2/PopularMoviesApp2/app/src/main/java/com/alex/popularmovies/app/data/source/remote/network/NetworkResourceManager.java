package com.alex.popularmovies.app.data.source.remote.network;

import com.alex.popularmovies.app.data.source.remote.network.exception.NetworkResourceException;
import com.alex.popularmovies.app.data.source.remote.network.exception.NullConnectionException;
import com.alex.popularmovies.app.util.MoviesUtil;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alex on 02/06/18.
 */

public class NetworkResourceManager implements NetworkResource {

	@Override
	public String getStringResourceFromURL(URL url) throws NetworkResourceException {
		HttpURLConnection connection = null;
		String stringResource = null;
		InputStream inputStream;

		try {
			connection = (HttpURLConnection) url.openConnection();
			if (connection == null) {
				throw new NullConnectionException("Não pode ser aberta conexão para URL: " + url);
			}
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.connect();

			int responseCode = connection.getResponseCode();
			if (responseCode != HttpsURLConnection.HTTP_OK) {
				throw new NetworkResourceException("HTTP error code pela api: " + responseCode);
			}

			inputStream = connection.getInputStream();
			stringResource = MoviesUtil.readStream(inputStream);
			inputStream.close();
		} catch (IOException e) {
			throw new NetworkResourceException(e);
		} catch (NullConnectionException e) {
			throw new NetworkResourceException(e);
		} catch (Exception e) {
			throw new NetworkResourceException(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		return stringResource;
	}

}
