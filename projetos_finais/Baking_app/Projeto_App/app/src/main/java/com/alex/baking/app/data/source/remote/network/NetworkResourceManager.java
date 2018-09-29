package com.alex.baking.app.data.source.remote.network;

import android.util.Log;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.util.Util;

import javax.net.ssl.HttpsURLConnection;
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
	public String getStringResourceFromURL(URL url) throws ConnectionException {
		String msgErroException = "Erro interno desconhecido";
		HttpURLConnection connection = null;
		String stringResource;
		InputStream inputStream;

		try {
			connection = (HttpURLConnection) url.openConnection();
			if (connection == null) {
				msgErroException = "Não pode ser aberta conexão para URL: " + url;
				throw new ConnectionException(msgErroException);
			}

			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.connect();

			int responseCode = connection.getResponseCode();
			if (responseCode != HttpsURLConnection.HTTP_OK) {
				throw new RuntimeException("HTTP error code pela api: " + responseCode);
			}

			inputStream = connection.getInputStream();
			stringResource = Util.readStream(inputStream);
			inputStream.close();
		} catch (ConnectionException e) {
			Log.e(TAG, msgErroException, e);
			throw e;
		} catch (UnknownHostException e) {
			msgErroException = "Erro de host desconhecido";
			Log.e(TAG, msgErroException, e);
			throw new RuntimeException(msgErroException, e);
		} catch (Exception e) {
			Log.e(TAG, msgErroException, e);
			throw new RuntimeException(msgErroException, e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		return stringResource;
	}

}
