package com.alex.popularmovies.app.ui.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.model.Movie;
import com.alex.popularmovies.app.ui.presenter.MoviesPresenter;

import java.util.List;

public class MainActivity extends BaseActivity<Movie, MoviesPresenter.View, MoviesPresenter> implements MoviesPresenter.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initializeArgumentsFromIntent() {

    }

    @Override
    public void createListAdapter(List results) {

    }

    @Override
    public void addAdapterData(List<Movie> result) {

    }

    @Override
    public void removeAdapterData(List<Movie> result) {

    }

    @Override
    public BaseAdapter getAdapter() {
        return null;
    }

    @Override
    public void destroyListAdapter() {

    }

    @Override
    public void showAddOrEditDataView(Movie data) {

    }

    @Override
    public void showDataView(Movie data) {

    }

    @Override
    public void setEmptyView(String text) {

    }

    @Override
    public void toggleEmptyView() {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
