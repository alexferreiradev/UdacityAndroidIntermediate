package com.alex.popularmovies.app.data.source.remote;

import android.util.Log;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.exception.NullConnectionException;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;
import com.alex.popularmovies.app.data.source.queryspec.remote.MoviesRemoteQuery;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alex on 02/04/2017.
 */

public class RemoteMovieSource extends BaseRemoteSource<Movie> {
	private static final String TAG_NAME = RemoteMovieSource.class.getSimpleName();

	private static final String MOVIE_JSON_KEY_RESULTS = "results";
	private static final String MOVIE_JSON_KEY_TITLE = "original_title";
	private static final String MOVIE_JSON_KEY_ID = "id";
	private static final String MOVIE_JSON_KEY_POSTER_PATH = "poster_path";
	private static final String MOVIE_JSON_KEY_OVERVIEW = "overview";
	private static final String MOVIE_JSON_KEY_RELEASE_DATE = "release_date";
	private static final String MOVIE_JSON_KEY_VOTE_AVERAGE = "vote_average";

	public RemoteMovieSource() {
		super();
	}

	@Override
	public Movie create(Movie model) throws SourceException {
		throw new SourceException("Método não disponível para esta versao.");
	}

	@Override
	public Movie recover(Long id) throws SourceException, NullConnectionException {
		List<Movie> movies = recover(new MoviesRemoteQuery(1, 0, MoviesRemoteQuery.MovieFilter.POPULAR));
		if (movies.isEmpty()) {
			throw new SourceException("Não existe filme com este id: " + id);
		}

		return movies.get(0);
	}

	@Override
	public List<Movie> recover(QuerySpecification specification) throws SourceException, NullConnectionException {
		List<Movie> movies = new ArrayList<>();
		InputStream stream;
		String movieJsonString;
		HttpURLConnection connection = null;

		try {
			URL url = (URL) specification.getQuery();
			connection = (HttpURLConnection) url.openConnection();
			validateConnection(connection, url);

			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
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
					movies.addAll(getModelListFromJsonResults(movieJsonString));
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new SourceException("Erro de URL mal formada ao tentar abrir conexão.", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SourceException("Erro de IO ao tentar abrir conexão.", e);
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

	@Override
	protected List<Movie> getModelListFromJsonResults(String movieJsonString) {
		List<Movie> movies = new ArrayList<>();

		try {
			JSONObject jsonObject = new JSONObject(movieJsonString);
			JSONArray resultArray = jsonObject.getJSONArray(MOVIE_JSON_KEY_RESULTS);
			for (int i = 0; i < resultArray.length(); i++) {
				JSONObject movieJsonObject = (JSONObject) resultArray.get(i);
				Movie movie = parseJSONToModel(movieJsonObject);
				movies.add(movie);
			}
		} catch (Exception e) {
			Log.e(TAG_NAME, "Erro ao fazer parse de JSON: ", e);
		}

		return movies;
	}

	@Override
	protected Movie parseJSONToModel(JSONObject movieJsonObject) throws Exception {
		Movie movie = new Movie();
		movie.setId(movieJsonObject.getInt(MOVIE_JSON_KEY_ID));
		movie.setTitle(movieJsonObject.getString(MOVIE_JSON_KEY_TITLE));
		movie.setPosterPath(movieJsonObject.getString(MOVIE_JSON_KEY_POSTER_PATH));
		movie.setRating(movieJsonObject.getDouble(MOVIE_JSON_KEY_VOTE_AVERAGE));
		movie.setSynopsis(movieJsonObject.getString(MOVIE_JSON_KEY_OVERVIEW));

		String releaseStringDate = movieJsonObject.getString(MOVIE_JSON_KEY_RELEASE_DATE);
		Date releaseDateParsed = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(releaseStringDate);
		movie.setReleaseDate(releaseDateParsed);

		return movie;
	}
}
