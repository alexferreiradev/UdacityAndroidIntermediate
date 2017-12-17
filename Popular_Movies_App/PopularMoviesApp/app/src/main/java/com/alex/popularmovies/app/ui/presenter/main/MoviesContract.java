package com.alex.popularmovies.app.ui.presenter.main;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.ui.presenter.BaseListContract;

import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface MoviesContract {

    public interface Presenter {
        void setMovieGrid(List<Movie> movies);
    }

    public interface View extends BaseListContract.View<Movie> {

    }
}
