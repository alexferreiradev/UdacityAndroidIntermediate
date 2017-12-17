package com.alex.popularmovies.app.ui.presenter.main;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.exception.DataException;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.movie.MovieRepositoryContract;
import com.alex.popularmovies.app.ui.presenter.BaseListPresenter;

import java.util.List;

/**
 * Created by Alex on 23/03/2017.
 */

public class MoviesPresenter extends BaseListPresenter<MoviesContract.View, Movie, MovieRepositoryContract> implements MoviesContract.Presenter {

    private static final String TAG = MoviesPresenter.class.getSimpleName();
    private ListMovieByKey movieByKey;

    public MoviesPresenter(MoviesContract.View mView, Context mContext, Bundle savedInstanceState, MovieRepositoryContract mRepository) {
        super(mView, mContext, savedInstanceState, mRepository);
    }

    @Override
    protected void setEmptyView() {
        mView.setEmptyView(mContext.getString(R.string.none_movie_loaded));
    }

    @Override
    protected void loadDataFromSource() {
        movieByKey = new ListMovieByKey(ListMovieByKey.POPULAR_KEY, this);
        movieByKey.execute();
    }

    @Override
    public void selectItemClicked(Movie item) {
        Log.i(TAG, "Filme selecionado: " + item);
        mView.showDataView(item);
    }

    @Override
    protected void applyFilterFromAdapter() {
        movieByKey = new ListMovieByKey(ListMovieByKey.POPULAR_KEY, this);
        movieByKey.execute();
    }

    public void setMovieGrid(List<Movie> movies) {
        hideProgressView();
        if (movies == null) {
            mView.showErrorMsg("Erro ao tentar listar filmes.");
        } else if (movies.isEmpty()) {
            mView.destroyListAdapter();
        } else {
            if (isNewAdapter()) {
                mView.createListAdapter(movies);
            } else {
                mView.addAdapterData(movies);
            }
        }
    }

    private List<Movie> getMoviesFromRepository(String key) {
        mOffset = mOffset + mLoadItemsLimit;
        try {
            switch (key) {
                case ListMovieByKey.POPULAR_KEY:
                    return mRepository.moviesByPopularity(mLoadItemsLimit, mOffset);
                case ListMovieByKey.TOP_VOTE_KEY:
                    mRepository.moviesByTopRate(mLoadItemsLimit, mOffset);
            }

            return mRepository.moviesByPopularity(mLoadItemsLimit, mOffset);
        } catch (DataException e) {
            Log.e(TAG, "Erro ao tentar listar filmes", e);
        }

        return null;
    }

    private static class ListMovieByKey extends AsyncTask<String, Integer, List<Movie>> {

        static final String POPULAR_KEY = "popular";
        static final String TOP_VOTE_KEY = "top_vote";
        private String key;
        private MoviesPresenter presenter;

        ListMovieByKey(String key, MoviesPresenter presenter) {
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
