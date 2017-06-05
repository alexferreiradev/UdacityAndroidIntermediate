package com.alex.popularmovies.app.data.source.remote;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.BaseQuerySpecification;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class RemoteMovie extends BaseRemoteSource<Movie> {

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
        return null;
    }
}
