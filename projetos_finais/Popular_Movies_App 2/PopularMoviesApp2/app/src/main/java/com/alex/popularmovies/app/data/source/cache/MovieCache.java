package com.alex.popularmovies.app.data.source.cache;

import android.util.Log;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class MovieCache extends BaseCache<Movie> {
	private static final String TAG = MovieCache.class.getSimpleName();
	private static MovieCache mInstance;

	/**
	 * Utilize o MovieCache.getInstance()
	 */
	private MovieCache() {
		super();
	}

	/**
	 * Cria uma instancia de cache para filmes.
	 */
	public synchronized static MovieCache getInstance() {
		if (mInstance == null) {
			mInstance = new MovieCache();
		}

		return mInstance;
	}

	@Override
	public Movie create(Movie model) throws SourceException {
		throw new SourceException("Cache não suporta este método.");
	}

	@Override
	public Movie recover(Long id) throws SourceException {
		if (id == null || id < 0) {
			throw new SourceException("Id inválido, nulo ou negativo");
		}

		if (mCache == null) {
			Log.w(TAG, "Tentando busca em cache que não possui dados.");
			throw new SourceException("Cache não inicializado");
		}

		for (Movie movie : mCache) {
			if (movie.getIdFromApi().equals(id)) {
				return movie;
			}
		}

		throw new SourceException("Id não encontrado");
	}

	@Override
	public List<Movie> recover(QuerySpecification specification) throws SourceException {
		throw new SourceException("Cache não suporta este método nesta versao.");
	}

	@Override
	public Movie update(Movie model) throws SourceException {
		throw new SourceException("Cache não suporta este método nesta versao.");
	}

	@Override
	public Movie delete(Movie model) throws SourceException {
		return mCache.remove(mCache.indexOf(model));
	}

	@Override
	protected void createCache() {
		mCache = new ArrayList<>();
	}

	@Override
	public boolean isEmpty() {
		return mCache.isEmpty();
	}

	@Override
	public void addAllCache(List<Movie> data, int offset) {
		mCache.addAll(data);
	}

	@Override
	public void removeAllCache(List<Movie> data) {
		mCache.removeAll(data);
	}
}
