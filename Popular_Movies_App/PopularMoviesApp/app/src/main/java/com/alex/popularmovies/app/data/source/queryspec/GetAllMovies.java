package com.alex.popularmovies.app.data.source.queryspec;

import com.alex.popularmovies.app.data.source.BaseQuerySpecification;
import com.alex.popularmovies.app.data.source.sql.PMContract;

/**
 * Created by Alex on 02/04/2017.
 */

public class GetAllMovies extends BaseQuerySpecification {

    public GetAllMovies() {
        super();
    }

    @Override
    protected void makeQuery() {
        mOrderBy = PMContract.MovieEntry.COLUMN_POPULARITY;
    }
}
