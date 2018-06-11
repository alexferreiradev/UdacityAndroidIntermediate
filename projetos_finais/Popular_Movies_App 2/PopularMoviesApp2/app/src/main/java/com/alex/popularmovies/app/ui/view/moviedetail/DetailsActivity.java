package com.alex.popularmovies.app.ui.view.moviedetail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
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
import com.alex.popularmovies.app.ui.presenter.detail.DetailContract;
import com.alex.popularmovies.app.ui.presenter.detail.DetailPresenter;
import com.alex.popularmovies.app.ui.view.BaseActivity;

public class DetailsActivity extends BaseActivity<Movie, DetailPresenter.View, DetailPresenter> implements DetailContract.View {
	public static final String EXTRA_PARAM_MOVIE_ID = "Movie id";
	private static final String TAG = DetailsActivity.class.getSimpleName();


	private long movieId = -1;
	private View infoFragmentView;
	private View videoFragmentView;
	private View reviewFragmentView;

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
		infoFragmentView = findViewById(R.id.infoContainer);
		videoFragmentView = findViewById(R.id.videoContainer);
		reviewFragmentView = findViewById(R.id.reviewContainer);
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
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = supportFragmentManager.beginTransaction();
		Bundle fragmentArgument = new Bundle();

		if (infoFragmentView != null) {
			InfoDetailsFragment infoDetailsFragment = new InfoDetailsFragment(mPresenter);
			fragmentArgument.putSerializable(InfoDetailsFragment.MOVIE_ARG_KEY, movie);
			infoDetailsFragment.setArguments(fragmentArgument);
			mPresenter.setInfoView(infoDetailsFragment);
			transaction.replace(R.id.infoContainer, infoDetailsFragment);
		}
		if (videoFragmentView != null) {
			VideoDetailsFragment videoDetailsFragment = new VideoDetailsFragment(mPresenter);
			fragmentArgument.putSerializable(VideoDetailsFragment.MOVIE_ARG_KEY, movie);
			videoDetailsFragment.setArguments(fragmentArgument);
			mPresenter.setVideoView(videoDetailsFragment);
			transaction.replace(R.id.videoContainer, videoDetailsFragment);
		}
		if (reviewFragmentView != null) {
			ReviewDetailsFragment reviewDetailsFragment = new ReviewDetailsFragment(mPresenter);
			fragmentArgument.putSerializable(ReviewDetailsFragment.MOVIE_ARG_KEY, movie);
			reviewDetailsFragment.setArguments(fragmentArgument);
			mPresenter.setReviewView(reviewDetailsFragment);
			transaction.replace(R.id.reviewContainer, reviewDetailsFragment);
		}

		transaction.commit();
		this.mData = movie;
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
