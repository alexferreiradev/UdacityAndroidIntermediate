package com.alex.popularmovies.app.ui.view;

import android.os.Bundle;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.model.Movie;
import com.alex.popularmovies.app.ui.presenter.MoviesPresenter;

public class DetailsActivity extends BaseActivity<Movie, MoviesPresenter.View, MoviesPresenter> implements MoviesPresenter.View<Movie> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

    @Override
    public void initializeArgumentsFromIntent() {

    }
}
