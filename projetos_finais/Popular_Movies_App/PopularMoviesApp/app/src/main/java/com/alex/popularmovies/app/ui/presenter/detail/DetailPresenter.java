package com.alex.popularmovies.app.ui.presenter.detail;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alex.popularmovies.app.data.exception.DataException;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.movie.MovieRepositoryContract;
import com.alex.popularmovies.app.ui.presenter.BasePresenter;

/**
 * Created by Alex on 23/03/2017.
 */

public class DetailPresenter extends BasePresenter<DetailContract.View, Movie, MovieRepositoryContract> implements DetailContract.Presenter {

    private static final String TAG = DetailPresenter.class.getSimpleName();

    public DetailPresenter(DetailContract.View mView, Context mContext, Bundle savedInstanceState, MovieRepositoryContract mRepository) {
        super(mView, mContext, savedInstanceState, mRepository);
    }

    @Override
    protected void initialize() {
        AsyncTask<Long, Integer, Movie> findMovieById = new FindMovieById(this);
        findMovieById.execute(mView.getMovieId());
    }

    public void markAsFavorite(Movie movie) {
        throw new RuntimeException("Metodo nao disponivel nesta versao");
    }

    public void setFavoriteOnRepository(Movie movie) {
        throw new RuntimeException("Metodo nao disponivel nesta versao");
    }

    public void setMovieView(Movie movie) {
        hideProgressView();
        if (movie == null) {
            mView.showErrorMsg("Erro ao buscar filme");
            mView.closeAndShowMovieGrid();
        } else {
            mView.bindMovieViewData(movie);
        }
    }

    @Nullable
    private Movie getMovieFromRepository(Long movieId) {
        try {
            return mRepository.recover(movieId);
        } catch (DataException e) {
            Log.e(TAG, "Erro durante recuperacao de filme por id: ", e);
        }

        return null;
    }

    private static class FindMovieById extends AsyncTask<Long, Integer, Movie> {

        private DetailPresenter presenter;

        FindMovieById(DetailPresenter presenter) {
            this.presenter = presenter;
        }

        @Override
        protected void onPreExecute() {
            presenter.showProgressView();
        }

        @Override
        protected Movie doInBackground(Long... params) {
            return presenter.getMovieFromRepository(params[0]);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            presenter.setMovieView(movie);
        }

    }
}
