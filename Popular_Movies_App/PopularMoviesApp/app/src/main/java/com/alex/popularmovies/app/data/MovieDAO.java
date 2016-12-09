package com.alex.popularmovies.app.data;

import android.content.Context;

import com.alex.popularmovies.app.model.Movie;

import java.util.List;

/**
 * Created by Alex on 09/12/2016.
 */

public class MovieDAO implements IDao<Movie> {

    private Context mContext;

    public MovieDAO(Context context) {
        this.mContext = context;

    }

    @Override
    public Movie saveOrUpdate(Movie obj) {

        return null;
    }

    @Override
    public Movie delete(Movie obj) {
        return null;
    }

    @Override
    public List<Movie> search(String key, String value, String orderBy, int limit) {
        return null;
    }

    @Override
    public Movie get(String key, String value) {
        return null;
    }
}
