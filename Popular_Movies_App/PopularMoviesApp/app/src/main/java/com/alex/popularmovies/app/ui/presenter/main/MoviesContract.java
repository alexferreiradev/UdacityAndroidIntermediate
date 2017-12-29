package com.alex.popularmovies.app.ui.presenter.main;

import android.view.Menu;
import android.view.MenuItem;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.MoviesType;
import com.alex.popularmovies.app.ui.presenter.BaseListContract;
import com.alex.popularmovies.app.ui.presenter.IPresenter;

import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface MoviesContract {

    public interface Presenter extends IPresenter {
        void setMovieGrid(List<Movie> movies);

        void setListType(MoviesType moviesType);
    }

    public interface View extends BaseListContract.View<Movie> {

        boolean onOptionsItemSelected(MenuItem item);

        boolean onCreateOptionsMenu(Menu menu);

        void toogleMenuMovies();
    }
}
