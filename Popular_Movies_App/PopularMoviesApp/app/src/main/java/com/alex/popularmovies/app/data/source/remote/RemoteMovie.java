package com.alex.popularmovies.app.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.DefaultSource;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;
import com.alex.popularmovies.app.data.source.queryspec.remote.MoviesRemoteQuery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Alex on 02/04/2017.
 */

public class RemoteMovie implements DefaultSource<Movie> {
    private static final String TAG_NAME = RemoteMovie.class.getSimpleName();

    private static final String MOVIE_JSON_KEY_RESULTS = "results";
    private static final String MOVIE_JSON_KEY_TITLE = "original_title";
    private static final String MOVIE_JSON_KEY_ID = "id";
    private static final String MOVIE_JSON_KEY_POSTER_PATH = "poster_path";
    private static final String MOVIE_JSON_KEY_OVERVIEW = "overview";
    private static final String MOVIE_JSON_KEY_POPULARITY = "popularity";
    private static final String MOVIE_JSON_KEY_VOTE_COUNT = "vote_count";


    private static String readStream(InputStream stream) throws Exception {
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
    public Movie create(Movie model) throws SourceException {
        throw new SourceException("Método não disponível para esta versao.");
    }

    @Override
    public Movie recover(Long id) throws SourceException {
        List<Movie> movies = recover(new MoviesRemoteQuery(1, 0, String.valueOf(id)));
        if (movies.isEmpty()) {
            throw new SourceException("Não existe filme com este id: " + id);
        }

        return movies.get(0);
    }

    @Override
    public List<Movie> recover(QuerySpecification specification) throws SourceException {
        List<Movie> movies = new ArrayList<>();
        InputStream stream;
        String movieJsonString;
        HttpURLConnection connection = null;

        try {
            URL url = (URL) specification.getQuery();
            connection = (HttpURLConnection) url.openConnection();
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
                throw new SourceException("HTTP error code pela api: " + responseCode);
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
            throw new SourceException("Erro de IO ao tentar abrir conexão ou carregar imagens.", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SourceException("Erro desconhecido ao tentar recuperar filmes da api.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return movies;
    }

    @Override
    public Movie update(Movie model) throws SourceException {
        throw new SourceException("Método não disponível para esta versao.");
    }

    @Override
    public Movie delete(Movie model) throws SourceException {
        throw new SourceException("Método não disponível para esta versao.");
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
