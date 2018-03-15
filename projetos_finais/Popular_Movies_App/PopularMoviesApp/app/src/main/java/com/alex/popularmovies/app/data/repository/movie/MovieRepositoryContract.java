package com.alex.popularmovies.app.data.repository.movie;

import com.alex.popularmovies.app.data.exception.DataException;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.DefaultRepository;

import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface MovieRepositoryContract extends DefaultRepository<Movie> {

    List<Movie> moviesByPopularity(int limit, int offset) throws DataException;

    List<Movie> moviesByTopRate(int limit, int offset) throws DataException;

    boolean hasCache() throws DataException;

    List<Movie> getCurrentCache() throws DataException;
}
