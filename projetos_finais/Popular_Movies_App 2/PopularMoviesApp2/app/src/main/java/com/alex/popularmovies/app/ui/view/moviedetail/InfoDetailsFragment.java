package com.alex.popularmovies.app.ui.view.moviedetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.ui.presenter.IPresenter;
import com.alex.popularmovies.app.ui.presenter.detail.DetailContract;
import com.alex.popularmovies.app.ui.view.fragment.BaseFragment;
import com.alex.popularmovies.app.util.MovieImageUtil;

import java.text.DecimalFormat;
import java.util.Calendar;

@SuppressLint("ValidFragment")
public class InfoDetailsFragment extends BaseFragment<Movie, InfoDetailsFragment.InfoFragmentCallbacks> implements DetailContract.InfoView {
	public static final String MOVIE_ARG_KEY = "movie_key";
	private static final String TAG = InfoDetailsFragment.class.getSimpleName();
	private TextView tvName;
	private TextView tvYear;
	private TextView tvRating;
	private TextView tvSynopsis;
	private ImageView ivMovieImage;
	private Movie mMovie;

	public InfoDetailsFragment(InfoFragmentCallbacks presenter) {
		super(presenter);
	}

	@Override
	protected void startWithArguments(Bundle arguments) {
		Movie movie = (Movie) arguments.getSerializable(MOVIE_ARG_KEY);
		if (movie == null) {
			throw new IllegalArgumentException("Model passado para iniciar fragment esta nulo");
		}

		this.mMovie = movie;
		presenter.loadMovie();
		setHasOptionsMenu(true);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.info_fragment_movie_details, container, false);
		mProgressBar = fragmentView.findViewById(R.id.progressBar);
		tvName = fragmentView.findViewById(R.id.tvMovieName);
		tvRating = fragmentView.findViewById(R.id.tvMovieRating);
		ivMovieImage = fragmentView.findViewById(R.id.ivMovieImage);
		tvYear = fragmentView.findViewById(R.id.tvMovieYear);
		tvSynopsis = fragmentView.findViewById(R.id.tvMovieSynopsis);

		return fragmentView;
	}

	@Override
	public void startView(Movie model) throws IllegalArgumentException {
		Log.d(TAG, "Fazendo bind de filme: " + mMovie.getIdFromApi());
		tvName.setText(mMovie.getTitle());
		String popularityFormated = new DecimalFormat("#.#").format(mMovie.getRating());
		String ratingFormatted = popularityFormated + "/" + getString(R.string.max_rating);
		tvRating.setText(ratingFormatted);
		MovieImageUtil.setImageViewWithPicasso(ivMovieImage, getContext(), mMovie, MovieImageUtil.IMAGE_LENGTH_W_185);
		tvSynopsis.setText(mMovie.getSynopsis());
		Calendar instance = Calendar.getInstance();
		instance.setTime(mMovie.getReleaseDate());
		tvYear.setText(String.valueOf(instance.get(Calendar.YEAR)));
	}

	@Override
	public Movie destroyView(Movie model) {
		return mMovie;
	}

	@Override
	public void updateFavoriteMovieStatus(Movie movie) {
		mMovie = movie;
		assert getActivity() != null;
		getActivity().invalidateOptionsMenu();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_info_details_fragment, menu);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		MenuItem item = menu.findItem(R.id.actionFavoriteMovie);
		if (item != null) {
			if (mMovie != null && mMovie.isFavorite()) {
				item.setIcon(getResources().getDrawable(R.drawable.ic_action_unfavorite));
				item.setTitle(R.string.detail_mark_fav_off);
			} else {
				item.setIcon(getResources().getDrawable(R.drawable.ic_action_favorite));
				item.setTitle(R.string.detail_mark_fav_on);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
			case R.id.actionFavoriteMovie:
				if (mMovie != null) {
					if (mMovie.isFavorite()) {
						Log.i(TAG, "Selecionando filme para não favorito." + mMovie.getIdFromApi());
						presenter.updateMovieFavoriteStatus(mMovie, false);
					} else {
						Log.i(TAG, "Selecionando filme para favorito: " + mMovie.getIdFromApi());
						presenter.updateMovieFavoriteStatus(mMovie, true);
					}
				}
				return true;
		}

		return false;
	}

	public interface InfoFragmentCallbacks extends IPresenter {

		void updateMovieFavoriteStatus(Movie movie, boolean newStatus);

		void loadMovie();

	}
}
