package com.alex.popularmovies.app.data.source.cache;

import android.util.Log;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.BaseQuerySpecification;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.GetAllMovies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class MovieCache extends BaseCache<Movie> {

    private static final String TAG = MovieCache.class.getSimpleName();

    public MovieCache(List<Movie> data) {
        super();
        mCache = data;
    }

    @Override
    public Movie insert(Movie data) {
        mCache.add(data);
        return data;
    }

    @Override
    public void update(Movie data) {
        throw new UnsupportedOperationException("Cache não suporta update");
    }

    @Override
    public void delete(Movie data) {
        mCache.remove(mCache.indexOf(data));
    }

    @Override
    public List<Movie> query(BaseQuerySpecification specification) {
        if (specification instanceof GetAllMovies) {
            return mCache;
        }
        return null;
    }

    @Override
    public List<Movie> list(String sortOrderType) throws SourceException {
        return null;
    }

    @Override
    public Movie get(Long id) throws SourceException {
        if (id == null || id < 0) {
            throw new SourceException("Id inválido, nulo ou negativo");
        }

        if (mCache == null) {
            Log.w(TAG, "Tentando busca em cache que não possui dados.");
            return null;
        }

        for (Movie movie : mCache) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }

        return null;
    }

    @Override
    protected void createCache() {
        mCache = new ArrayList<Movie>();
    }

    @Override
    protected void addCache(List<Movie> data) {
        mCache.addAll(data);
    }

    @Override
    protected void removeCache(List<Movie> data) {
        mCache.removeAll(data);
    }
}
