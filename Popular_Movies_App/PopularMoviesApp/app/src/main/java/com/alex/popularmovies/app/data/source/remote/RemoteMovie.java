package com.alex.popularmovies.app.data.source.remote;

import android.net.Uri;
import android.util.Log;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.BaseQuerySpecification;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Alex on 02/04/2017.
 */

public class RemoteMovie extends BaseRemoteSource<Movie> {

    public static final String API_AUTHORITY = "api.themoviedb.org";
    public static final String API_VERSION = "3";
    public static final String API_MOVIE_PATH = "movie";
    public static final String API_SCHEME = "http";
    public static final String TAG_NAME = RemoteMovie.class.getSimpleName();

    public RemoteMovie(HttpURLConnection mHttpURLConnection) {
        super(mHttpURLConnection);
    }

    @Override
    public Movie insert(Movie data) {
        return null;
    }

    @Override
    public void update(Movie data) {

    }

    @Override
    public void delete(Movie data) {

    }

    @Override
    public List<Movie> query(BaseQuerySpecification specification) {
        List<Movie> movies = new ArrayList<Movie>();
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String movieJsonString = null;
        try {
            String movieId = specification.getmSelectionArgs()[0];
            if (movieId == null || movieId.isEmpty()) {
                Log.e(TAG_NAME, "Id passado para query é inválido.");
                return movies;
            }
            Uri.Builder builder = new Uri.Builder();
            builder
                    .scheme(API_SCHEME)
                    .authority(API_AUTHORITY)
                    .path(API_VERSION)
                    .appendPath(API_MOVIE_PATH)
                    .appendPath(movieId)
                    .appendQueryParameter("api_key", "APIkey-config"); // todo adicionar api_key em properties e ver no sunshine como recuperar config

            String apiUrl = builder.build().toString();
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            if (connection == null) {
                throw new IOException("Conexão não iniciada.");
            }

            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            stream = connection.getInputStream();
            if (stream != null) {
                movieJsonString = readStream(stream, 500);
            }
        } catch (ProtocolException e) {
            Log.e(TAG_NAME, "Erro de protocolo.");
        } catch (IOException e) {
            Log.e(TAG_NAME, "Erro de IO.");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.e(TAG_NAME, "Erro de IO ao tentar fechar stream.");
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        if (movieJsonString != null && !movieJsonString.isEmpty()) {
            // todo converter json para Lista de Movie
        }

        return movies;
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    public String readStream(InputStream stream, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }
}
