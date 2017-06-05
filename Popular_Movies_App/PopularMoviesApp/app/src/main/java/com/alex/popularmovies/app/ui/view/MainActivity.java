package com.alex.popularmovies.app.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.MovieRepository;
import com.alex.popularmovies.app.ui.adapter.MovieGridAdapter;
import com.alex.popularmovies.app.ui.presenter.MoviesPresenter;

import java.util.List;

public class MainActivity extends BaseActivity<Movie, MoviesPresenter.View, MoviesPresenter> implements MoviesPresenter.View {

    private GridView gvMovies;
    private MovieGridAdapter mAdapter;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MoviesPresenter(this, this, savedInstanceState, new MovieRepository(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null){
            mPresenter.startPresenterView();
        }
    }

    @Override
    public void initializeWidgets(Bundle savedInstanceState) {
        super.initializeWidgets(savedInstanceState);
        gvMovies = (GridView) findViewById(R.id.gvMovies);
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);
        gvMovies.setEmptyView(tvEmpty);
    }

    @Override
    public void initializeArgumentsFromIntent() {
    }

    @Override
    public void createListAdapter(List results) {
        mAdapter = new MovieGridAdapter(this, results);
        gvMovies.setAdapter(mAdapter);
    }

    @Override
    public void addAdapterData(List<Movie> result) {
    }

    @Override
    public void removeAdapterData(List<Movie> result) {
    }

    @Override
    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void destroyListAdapter() {
        mAdapter = null;
        gvMovies.setAdapter(null);
    }

    @Override
    public void showAddOrEditDataView(Movie data) {
    }

    @Override
    public void showDataView(Movie data) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_PARAM_MOVIE_ID, data.getId());
        startActivity(intent);
    }

    @Override
    public void setEmptyView(String text) {
        tvEmpty.setText(text);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPresenter.selectItemClicked((Movie) mAdapter.getItem(position));
    }
}
