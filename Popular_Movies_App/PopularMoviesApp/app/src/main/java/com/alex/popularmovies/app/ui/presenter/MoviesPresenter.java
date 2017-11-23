package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.DefaultRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 23/03/2017.
 */

public class MoviesPresenter extends BaseListPresenter<MoviesPresenter.View, Movie> {


    public MoviesPresenter(View mView, Context mContext, Bundle savedInstanceState, DefaultRepository mRepository) {
        super(mView, mContext, savedInstanceState, mRepository);
    }

    @Override
    protected void setEmptyView() {
        mView.setEmptyView(mContext.getString(R.string.none_movie_loaded));
    }

    @Override
    protected void loadDataFromSource() {
        final AsyncTask<String, Integer, List<Movie>> execute = new AsyncTask<String, Integer, List<Movie>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.setLoadProgressBarVisibility(true);
            }

            @Override
            protected List<Movie> doInBackground(String... params) {
                mOffset = mOffset + mLoadItemsLimit;
                return mRepository.query(null, null, null, null, null);
            }

            @Override
            protected void onPostExecute(List<Movie> movies) {
                mView.setLoadProgressBarVisibility(false);
                if (movies == null || movies.isEmpty()) {
                    if (isNewAdapter()) {
                        mView.destroyListAdapter();
                    }
                    return;
                }
                if (isNewAdapter())
                    mView.createListAdapter(movies);
                else
                    mView.addAdapterData(movies);
            }
        };

        execute.execute();
    }

    @Override
    public void selectItemClicked(Movie item) {
        mView.showDataView(item);
    }

    @Override
    protected void applyFilterFromAdapter() {
        new AsyncTask<String, Integer, List<Movie>>() {

            @Override
            protected List<Movie> doInBackground(String... params) {
                List<Movie> movies = new ArrayList<Movie>();

                MovieFilterType filterType = null;
                if (mFilterKey == MovieFilterType.TITLE.name())
                    filterType = MovieFilterType.TITLE;

                for (int i = 0; i < mView.getAdapter().getCount(); i++) {
                    switch (filterType) {
                        case NAME:
                            Movie movie = (Movie) mView.getAdapter().getItem(i);
                            String filterKey = movie.getTitle();
                            if (filterKey.equalsIgnoreCase(mFilterValue))
                                movies.add(movie);
                            break;
                    }
                }
                return movies;
            }

            @Override
            protected void onPostExecute(List<Movie> movies) {
                if (movies == null || movies.isEmpty()) {
                    mView.showSuccessMsg(mContext.getString(R.string.filter_error));
                    return;
                } else {
                    mView.showSuccessMsg(mContext.getString(R.string.filter_success));
                    mView.createListAdapter(movies);
                }
            }
        }.execute();
    }

    public enum MovieFilterType {
        NAME, TITLE
    }

    public interface View extends BaseListContract.View<Movie> {

    }
}
