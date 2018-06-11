package com.alex.popularmovies.app.ui.view.moviedetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		Log.d(TAG, "Fazendo bind de filme: " + model.getId());
		tvName.setText(model.getTitle());
		String popularityFormated = new DecimalFormat("#.#").format(model.getRating());
		String ratingFormatted = popularityFormated + "/" + getString(R.string.max_rating);
		tvRating.setText(ratingFormatted);
		MovieImageUtil.setImageViewWithPicasso(ivMovieImage, getContext(), model, MovieImageUtil.IMAGE_LENGTH_W_185);
		tvSynopsis.setText(model.getSynopsis());
		Calendar instance = Calendar.getInstance();
		instance.setTime(model.getReleaseDate());
		tvYear.setText(String.valueOf(instance.get(Calendar.YEAR)));
	}

	@Override
	public Movie destroyView(Movie model) {
		return mMovie;
	}


	@Override
	public void markMovieFavorite() {

	}

	@Override
	public void unFavoriteMovie() {

	}

	public interface InfoFragmentCallbacks extends IPresenter {

		void favoriteMovie(Movie movie);

		void unFavoriteMovie(Movie movie);

		void loadMovie();

	}
}
