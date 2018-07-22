package com.alex.popularmovies.app.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.MoviesType;
import com.alex.popularmovies.app.data.repository.movie.MovieRepository;
import com.alex.popularmovies.app.data.source.cache.BaseCache;
import com.alex.popularmovies.app.data.source.cache.MovieCache;
import com.alex.popularmovies.app.data.source.remote.RemoteMovieSource;
import com.alex.popularmovies.app.data.source.remote.RemoteReviewSource;
import com.alex.popularmovies.app.data.source.remote.RemoteVideoSource;
import com.alex.popularmovies.app.data.source.remote.network.NetworkResourceManager;
import com.alex.popularmovies.app.data.source.sql.MovieSql;
import com.alex.popularmovies.app.ui.adapter.ListViewAdaper;
import com.alex.popularmovies.app.ui.adapter.MovieGridAdapter;
import com.alex.popularmovies.app.ui.presenter.main.MoviesContract;
import com.alex.popularmovies.app.ui.presenter.main.MoviesPresenter;
import com.alex.popularmovies.app.ui.view.moviedetail.DetailsActivity;

import java.util.List;

public class MainActivity extends BaseActivity<Movie, MoviesContract.View, MoviesPresenter> implements MoviesContract.View {

	public static final String TAG = MainActivity.class.getSimpleName();
	private static final String SAVED_LIST_TYPE_KEY = "saved_list_type_key";
	private static final String SAVED_LIST_POSITION_KEY = "saved_list_position_key";

	private GridView gvMovies;
	private TextView tvEmpty;
	private ListViewAdaper<Movie> mAdapter;

	public MainActivity() {
		super(null);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mPresenter != null) {
			MoviesType currentListType = mPresenter.getCurrentListType();
			int firstVisiblePosition = gvMovies.getFirstVisiblePosition();

			outState.putString(SAVED_LIST_TYPE_KEY, currentListType.name());
			outState.putInt(SAVED_LIST_POSITION_KEY, firstVisiblePosition);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_LIST_POSITION_KEY)) {
			int firstPositionVisible = savedInstanceState.getInt(SAVED_LIST_POSITION_KEY, 0);

			if (mPresenter != null) {
				mPresenter.setListScroolPosition(firstPositionVisible);
			}
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (mPresenter != null) {
			if (mPresenter.getCurrentListType() == MoviesType.FAVORITE) {
				mPresenter.setListType(MoviesType.FAVORITE);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String savedListType = MoviesType.MOST_POPULAR.name();
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(SAVED_LIST_TYPE_KEY)) {
				savedListType = savedInstanceState.getString(SAVED_LIST_TYPE_KEY);
			}
		}

		BaseCache<Movie> movieCache = MovieCache.getInstance();
		MovieRepository movieRepository = new MovieRepository(movieCache,
				new MovieSql(this),
				new RemoteMovieSource(new NetworkResourceManager()),
				new RemoteReviewSource(new NetworkResourceManager()),
				new RemoteVideoSource(new NetworkResourceManager()));

		mPresenter = new MoviesPresenter(this, this, savedInstanceState, movieRepository, MoviesType.valueOf(savedListType));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.movie_list, menu);

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		MenuItem menuPopular = menu.findItem(R.id.popular_movies);
		MenuItem menuTopvoted = menu.findItem(R.id.top_voted_movies);
		MenuItem menuFavoriteMovies = menu.findItem(R.id.favorite_movies);
		switch (mPresenter.getCurrentListType()) {
			case MOST_POPULAR:
				menuPopular.setVisible(false);

				menuTopvoted.setVisible(true);
				menuFavoriteMovies.setVisible(true);
				break;
			case TOP_VOTED:
				menuTopvoted.setVisible(false);

				menuPopular.setVisible(true);
				menuFavoriteMovies.setVisible(true);
				break;
			case FAVORITE:
				menuFavoriteMovies.setVisible(false);

				menuPopular.setVisible(true);
				menuTopvoted.setVisible(true);
				break;
		}

		return true;
	}

	@Override
	public void updateMenuItems() {
		invalidateOptionsMenu();
	}

	@Override
	public void setGridScroolPosition(int position) {
		Log.d(TAG, "Selecionando posicao: " + position + " para grid scrool.");
		View v = gvMovies.getChildAt(0);
		int topOffset = (v == null) ? 0 : (v.getTop() - gvMovies.getPaddingTop());

		gvMovies.smoothScrollToPositionFromTop(position, topOffset);
	}

	@Override
	public int getFirstVisibleItemPosition() {
		return gvMovies.getFirstVisiblePosition();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.top_voted_movies:
				Log.d(TAG, "Top voted menu selecionado");
				mPresenter.setListType(MoviesType.TOP_VOTED);
				break;
			case R.id.popular_movies:
				Log.d(TAG, "Popular menu selecionado");
				mPresenter.setListType(MoviesType.MOST_POPULAR);
				break;
			case R.id.favorite_movies:
				Log.d(TAG, "Favoritos menu selecionado");
				mPresenter.setListType(MoviesType.FAVORITE);
				break;
			default:
				return false;
		}

		return true;
	}

	@Override
	public void initializeWidgets(Bundle savedInstanceState) {
		super.initializeWidgets(savedInstanceState);
		gvMovies = findViewById(R.id.gvMovies);
		tvEmpty = findViewById(R.id.tvEmpty);
		gvMovies.setEmptyView(tvEmpty);
		gvMovies.setOnItemClickListener(this);
		gvMovies.setOnScrollListener(this);
	}

	@Override
	public void initializeArgumentsFromIntent() {
	}

	@Override
	public void createListAdapter(List<Movie> results) {
		mAdapter = new MovieGridAdapter(this, results);
		gvMovies.setAdapter(mAdapter);
	}

	@Override
	public void addAdapterData(List<Movie> result) {
		mAdapter.addAllModel(result);
	}

	@Override
	public void removeAdapterData(List<Movie> result) {
		mAdapter.removeAllModel(result);
	}

	@Override
	public ListAdapter getAdapter() {
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
		intent.putExtra(DetailsActivity.EXTRA_PARAM_MOVIE_ID, data.getIdFromApi());
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
		Log.v(TAG, "scrool chamado = total = " + totalItemCount + " first visible item position: " + firstVisibleItem);

		if (mPresenter != null) {
			mPresenter.loadMoreData(firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String logMsg = "Item selecionado, posicao: " + position + ", id: " + id;
		Log.d(TAG, logMsg);

		mPresenter.selectItemClicked((Movie) mAdapter.getItem(position), position);
	}
}
