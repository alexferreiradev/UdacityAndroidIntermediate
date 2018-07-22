package com.alex.baking.app.data.source.remote.network;

import android.util.Log;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.data.source.remote.network.exception.NetworkResourceException;
import com.alex.baking.app.util.MoviesUtil;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by alex on 02/06/18.
 */

public class NetworkResourceManager implements NetworkResource {
	private static final String TAG = NetworkResourceManager.class.getSimpleName();

	@Override
	public String getStringResourceFromURL(URL url) throws NetworkResourceException, ConnectionException {
		HttpURLConnection connection = null;
		String stringResource;
		InputStream inputStream;

		try {
			connection = (HttpURLConnection) url.openConnection();
			if (connection == null) {
				String msg = "Não pode ser aberta conexão para URL: " + url;
				Log.e(TAG, msg);
				throw new ConnectionException(msg);
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
		} catch (ConnectionException e) {
			Log.e(TAG, "Url incorreta", e);
			throw new ConnectionException("Host nao identificavel", e);
		} catch (UnknownHostException e) {
			Log.e(TAG, "Dispositivo offline", e);
			throw new ConnectionException("Host nao identificavel", e);
		} catch (IOException e) {
			Log.e(TAG, "Erro IO", e);
			throw new NetworkResourceException(e);
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido", e);
			throw new NetworkResourceException(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		return stringResource;
	}

}
