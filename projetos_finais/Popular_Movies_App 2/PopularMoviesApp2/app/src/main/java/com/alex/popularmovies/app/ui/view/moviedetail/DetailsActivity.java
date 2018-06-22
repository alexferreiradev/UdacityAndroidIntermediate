package com.alex.popularmovies.app.ui.view.moviedetail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.movie.MovieRepository;
import com.alex.popularmovies.app.data.source.cache.BaseCache;
import com.alex.popularmovies.app.data.source.cache.MovieCache;
import com.alex.popularmovies.app.data.source.remote.RemoteMovieSource;
import com.alex.popularmovies.app.data.source.remote.RemoteReviewSource;
import com.alex.popularmovies.app.data.source.remote.RemoteVideoSource;
import com.alex.popularmovies.app.data.source.remote.network.NetworkResourceManager;
import com.alex.popularmovies.app.data.source.sql.MovieSql;
import com.alex.popularmovies.app.ui.adapter.DetailViewPagerAdapter;
import com.alex.popularmovies.app.ui.presenter.detail.DetailContract;
import com.alex.popularmovies.app.ui.presenter.detail.DetailPresenter;
import com.alex.popularmovies.app.ui.view.BaseActivity;

public class DetailsActivity extends BaseActivity<Movie, DetailPresenter.View, DetailPresenter> implements DetailContract.View {

	private static final String TAG = DetailsActivity.class.getSimpleName();

	public static final String EXTRA_PARAM_MOVIE_ID = "Movie id";

	private long movieId = -1;
	private ViewPager detailsVP;
	private DetailViewPagerAdapter mPagerAdapter;

	public DetailsActivity() {
		super("Filme selecionado");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		BaseCache<Movie> movieCache = MovieCache.getInstance();
		MovieRepository movieRepository = new MovieRepository(movieCache,
				new MovieSql(this),
				new RemoteMovieSource(new NetworkResourceManager()),
				new RemoteReviewSource(new NetworkResourceManager()),
				new RemoteVideoSource(new NetworkResourceManager()));
		mPresenter = new DetailPresenter(this, this, savedInstanceState, movieRepository);
	}

	@Override
	public void initializeWidgets(Bundle savedInstanceState) {
		super.initializeWidgets(savedInstanceState);
		detailsVP = findViewById(R.id.vpDetails);
	}

	@Override
	public void initializeArgumentsFromIntent() {
		IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Não foi passado id do filme");
		if (getIntent() != null && getIntent().hasExtra(EXTRA_PARAM_MOVIE_ID)) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				throw illegalArgumentException;
			}

			movieId = extras.getLong(EXTRA_PARAM_MOVIE_ID, -1L);
			Log.d(TAG, "Argumento filme id: " + movieId);
			if (movieId < 0) {
				throw illegalArgumentException;
			}
		} else {
			throw illegalArgumentException;
		}
	}

	@Override
	public Long getMovieId() {
		return movieId;
	}

	@Override
	public void setDataAndStartFragments(Movie movie) {
		mPagerAdapter = new DetailViewPagerAdapter(getSupportFragmentManager(), mPresenter, movie);
		detailsVP.setAdapter(mPagerAdapter);
		detailsVP.setCurrentItem(0);
		this.mData = movie;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		detailsVP.setAdapter(null);
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong("movie_id_saved", movieId);
	}

	@Override
	public Movie getData() {
		return mData;
	}

	@Override
	public void closeAndShowMovieGrid() {
		finish();
	}

}
