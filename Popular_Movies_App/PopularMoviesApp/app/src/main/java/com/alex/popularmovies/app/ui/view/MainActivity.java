package com.alex.popularmovies.app.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.movie.MovieRepository;
import com.alex.popularmovies.app.data.source.cache.BaseCache;
import com.alex.popularmovies.app.data.source.cache.MovieCache;
import com.alex.popularmovies.app.data.source.remote.RemoteMovie;
import com.alex.popularmovies.app.data.source.sql.MovieSql;
import com.alex.popularmovies.app.ui.adapter.MovieGridAdapter;
import com.alex.popularmovies.app.ui.presenter.main.MoviesContract;
import com.alex.popularmovies.app.ui.presenter.main.MoviesPresenter;

import java.util.List;

public class MainActivity extends BaseActivity<Movie, MoviesPresenter.View, MoviesPresenter> implements MoviesContract.View {

    public static final String TAG = MainActivity.class.getSimpleName();
    private GridView gvMovies;
    private MovieGridAdapter mAdapter;
    private TextView tvEmpty;

    MainActivity() {
        super(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseCache<Movie> movieCache = MovieCache.getInstance();
        mPresenter = new MoviesPresenter(this, this, savedInstanceState, new MovieRepository(movieCache, new MovieSql(this), new RemoteMovie()));
    }

    @Override
    public void initializeWidgets(Bundle savedInstanceState) {
        super.initializeWidgets(savedInstanceState);
        gvMovies = (GridView) findViewById(R.id.gvMovies);
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);
        gvMovies.setEmptyView(tvEmpty);
        gvMovies.setOnItemClickListener(this);
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
        StringBuilder stringBuild = new StringBuilder("Item selecionado,");
        stringBuild
                .append(" posicao: ").append(position)
                .append(", id: ").append(id);

        Log.d(TAG, stringBuild.toString());
        mPresenter.selectItemClicked((Movie) mAdapter.getItem(position));
    }
}
