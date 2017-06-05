package com.alex.popularmovies.app.data.source.cache;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.BaseQuerySpecification;
import com.alex.popularmovies.app.data.source.queryspec.GetAllMovies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class MovieCache extends BaseCache<Movie> {

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
        if (specification instanceof GetAllMovies){
            return mCache;
        }
        return null;
    }

    @Override
    protected void createCache() {
        mCache = new ArrayList<Movie>();
    }

    @Override
    protected void destroyCache() {
        mCache = null;
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
