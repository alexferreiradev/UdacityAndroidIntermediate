package com.alex.popularmovies.app.ui.presenter.main;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.exception.DataException;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.MoviesType;
import com.alex.popularmovies.app.data.repository.movie.MovieRepositoryContract;
import com.alex.popularmovies.app.ui.presenter.BaseListPresenter;

import java.util.List;

/**
 * Created by Alex on 23/03/2017.
 */

public class MoviesPresenter extends BaseListPresenter<MoviesContract.View, Movie, MovieRepositoryContract> implements MoviesContract.Presenter {

    private static final String TAG = MoviesPresenter.class.getSimpleName();

    private ListMovieByKey movieByKey;
    private MoviesType moviesType;

    public MoviesPresenter(MoviesContract.View mView, Context mContext, Bundle savedInstanceState, MovieRepositoryContract mRepository) {
        super(mView, mContext, savedInstanceState, mRepository);
        moviesType = MoviesType.MOST_POPULAR;
    }

    @Override
    protected void setEmptyView() {
        mView.setEmptyView(mContext.getString(R.string.none_movie_loaded));
    }

    @Override
    protected void loadDataFromSource() {
        movieByKey = new ListMovieByKey(moviesType, this);
        movieByKey.execute();
    }

    @Override
    public void selectItemClicked(Movie item) {
        Log.i(TAG, "Filme selecionado: " + item);
        mView.showDataView(item);
    }

    @Override
    protected void applyFilterFromAdapter() {
        movieByKey = new ListMovieByKey(moviesType, this);
        movieByKey.execute();
    }

    public void setMovieGrid(List<Movie> movies) {
        hideProgressView();
        if (movies == null) {
            mView.showErrorMsg("Erro ao tentar listar filmes.");
        } else if (!movies.isEmpty()) {
            if (isNewAdapter()) {
                mView.createListAdapter(movies);
            } else {
                mView.addAdapterData(movies);
            }
            mOffset = mView.getAdapter().getCount();
        }
    }

    @Override
    public void setListType(MoviesType moviesType) {
        mView.toogleMenuMovies();
        this.moviesType = moviesType;
        initialize();
    }

    private List<Movie> getMoviesFromRepository(MoviesType key) {
        try {
            switch (key) {
                case MOST_POPULAR:
                    return mRepository.moviesByPopularity(mLoadItemsLimit, mOffset);
                case TOP_VOTED:
                    return mRepository.moviesByTopRate(mLoadItemsLimit, mOffset);
            }

            return mRepository.moviesByPopularity(mLoadItemsLimit, mOffset);
        } catch (DataException e) {
            Log.e(TAG, "Erro ao tentar listar filmes", e);
        }

        return null;
    }

    @Override
    protected void initialize() {
        try {
            if (mRepository.hasCache()) {
                mView.createListAdapter(mRepository.getCurrentCache());
                mView.setGridPosByLastSelectedFilm();
                mView.setLoadProgressBarVisibility(false);
            } else {
                super.initialize();
            }
        } catch (DataException e) {
            Log.e(TAG, "Erro ao tentar iniciar presenter. Erro ao tentar utilizar repositorio.");
        }
    }

    private static class ListMovieByKey extends AsyncTask<String, Integer, List<Movie>> {
        private MoviesType key;
        private MoviesPresenter presenter;

        ListMovieByKey(MoviesType key, MoviesPresenter presenter) {
            this.key = key;
            this.presenter = presenter;
        }

        @Override
        protected void onPreExecute() {
            presenter.showProgressView();
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            return presenter.getMoviesFromRepository(key);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            presenter.setMovieGrid(movies);
        }
    }
}
