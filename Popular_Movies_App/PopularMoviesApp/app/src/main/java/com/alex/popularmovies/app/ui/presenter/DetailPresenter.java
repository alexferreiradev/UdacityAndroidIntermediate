package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.DefaultRepository;

/**
 * Created by Alex on 23/03/2017.
 */

public class DetailPresenter extends BasePresenter<DetailPresenter.View, Movie> {

    public DetailPresenter(View mView, Context mContext, Bundle savedInstanceState, DefaultRepository mRepository) {
        super(mView, mContext, savedInstanceState, mRepository);
    }

    @Override
    protected void initialize() {
        new AsyncTask<String, Integer, Movie>(){

            @Override
            protected Movie doInBackground(String... params) {
                return (Movie) mRepository.get(mView.getMovieId());
            }

            @Override
            protected void onPostExecute(Movie movie) {
                if (movie == null) {
                    mView.showErrorMsg("Nenhum filme encontrado.");
                }else{
                    mView.bindMovieViewData(movie);
                }
            }
        }.execute();
    }

    public void markAsFavorite(Movie movie) {
        if (movie.isFavorite()){
            mView.setFavOff();
            movie.setFavorite(false);
        } else{
            mView.setFavOn();
            movie.setFavorite(true);
        }

        setFavoriteOnRepository(movie);
    }

    private void setFavoriteOnRepository(Movie movie){
        // todo mRepository.update()
    }

    public interface View extends BasePresenter.View<Movie>, android.view.View.OnClickListener {
        Long getMovieId();

        void bindMovieViewData(Movie movie);

        void setFavOn();

        void setFavOff();
    }
}
