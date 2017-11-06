package com.alex.popularmovies.app.data.source.remote;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.BaseQuerySpecification;
import com.alex.popularmovies.app.data.source.exception.SourceException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Alex on 02/04/2017.
 */

public class RemoteMovie extends BaseRemoteSource<Movie> {

    private static final String API_AUTHORITY = "api.themoviedb.org";
    private static final String API_VERSION = "3";
    private static final String API_MOVIE_PATH = "movie";
    private static final String API_SCHEME = "http";
    private static final String TAG_NAME = RemoteMovie.class.getSimpleName();

    private static final String MOVIE_JSON_KEY_RESULTS = "results";
    private static final String MOVIE_JSON_KEY_TITLE = "original_title";
    private static final String MOVIE_JSON_KEY_ID = "id";
    private static final String MOVIE_JSON_KEY_POSTER_PATH = "poster_path";
    private static final String MOVIE_JSON_KEY_OVERVIEW = "overview";
    private static final String MOVIE_JSON_KEY_POPULARITY = "popularity";
    private static final String MOVIE_JSON_KEY_VOTE_COUNT = "vote_count";

    public RemoteMovie(HttpURLConnection mHttpURLConnection) {
        super(mHttpURLConnection);
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    private static String readStream(InputStream stream)
            throws IOException {
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

    @Override
    public Movie insert(Movie data) throws SourceException {
        return null;
    }

    @Override
    public void update(Movie data) throws SourceException {

    }

    @Override
    public void delete(Movie data) throws SourceException {

    }

    @Override
    public List<Movie> query(BaseQuerySpecification specification) throws SourceException {
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
            builder.scheme(API_SCHEME)
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
                movieJsonString = readStream(stream);
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

        return movies;
    }

    @Override
    public List<Movie> list(String sortOrderType) throws SourceException {
        List<Movie> movies = new ArrayList<Movie>();
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String movieJsonString = null;
        Uri.Builder builder = new Uri.Builder();
        String api_key = ""; // todo colocar API carregada de config - olhar udacity projects
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .path("3")
                .appendPath("movie")
                .appendPath("popular") // todo adicionar troca para mais votado
                .appendQueryParameter("api_key", api_key);

        String apiUrl = builder.build().toString();
        URL url = null;
        try {
            url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            if (connection == null) {
                throw new SourceException("Conexão não pode ser iniciada.");
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
                movieJsonString = readStream(stream);
                if (!movieJsonString.isEmpty()) {
                    movies.addAll(getMoviesFromJsonResults(movieJsonString));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new SourceException("Erro de URL mal formada ao tentar abrir conexão.", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SourceException("Erro de IO ao tentar abrir conexão.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return movies;
    }

    private List<Movie> getMoviesFromJsonResults(String movieJsonString) {
        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(movieJsonString);
            JSONArray resultArray = jsonObject.getJSONArray(MOVIE_JSON_KEY_RESULTS);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject movieJsonObject = (JSONObject) resultArray.get(i);
                Movie movie = parseJSONToMovie(movieJsonObject);
                movies.add(movie);
            }
        } catch (Exception e) {
            Log.e(TAG_NAME, "Erro ao fazer parse de JSON: ", e);
        }

        return movies;
    }

    @NonNull
    private Movie parseJSONToMovie(JSONObject movieJsonObject) throws Exception {
        Movie movie = new Movie();
        movie.setTitle(movieJsonObject.getString(MOVIE_JSON_KEY_TITLE));
        movie.setId(movieJsonObject.getInt(MOVIE_JSON_KEY_ID));
        movie.setPosterPath(movieJsonObject.getString(MOVIE_JSON_KEY_POSTER_PATH));
        movie.setSynopsis(movieJsonObject.getString(MOVIE_JSON_KEY_OVERVIEW));
        movie.setPopularity(movieJsonObject.getDouble(MOVIE_JSON_KEY_POPULARITY));
        movie.setRating(movieJsonObject.getInt(MOVIE_JSON_KEY_VOTE_COUNT));

        return movie;
    }
}
