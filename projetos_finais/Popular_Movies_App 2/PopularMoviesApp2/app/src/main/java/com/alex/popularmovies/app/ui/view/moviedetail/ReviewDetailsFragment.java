package com.alex.popularmovies.app.ui.view.moviedetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.Review;
import com.alex.popularmovies.app.ui.adapter.ReviewListAdapter;
import com.alex.popularmovies.app.ui.presenter.IPresenter;
import com.alex.popularmovies.app.ui.presenter.detail.DetailContract;
import com.alex.popularmovies.app.ui.view.fragment.BaseListFragment;

import java.util.List;

@SuppressLint("ValidFragment")
public class ReviewDetailsFragment extends BaseListFragment<Review, ReviewDetailsFragment.ReviewFragmentCallbacks> implements DetailContract.ReviewView {
	public static final String MOVIE_ARG_KEY = "movie_key";
	private static final String TAG = ReviewDetailsFragment.class.getSimpleName();
	private TextView emptyTV;
	private ListView reviewsLV;
	private Movie mMovie;
	private ReviewListAdapter mAdapter;

	public ReviewDetailsFragment(ReviewFragmentCallbacks presenter) {
		super(presenter);
	}

	@Override
	protected void startWithArguments(Bundle arguments) {
		mMovie = (Movie) arguments.get(MOVIE_ARG_KEY);
		if (mMovie == null) {
			throw new IllegalArgumentException("Filme passado para fragment esta nulo");
		}

		presenter.loadReviewsFromMovie(mMovie);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.review_fragment_movie_details, container, false);
		mProgressBar = fragmentView.findViewById(R.id.progressBar);
		emptyTV = fragmentView.findViewById(R.id.tvEmpty);
		reviewsLV = fragmentView.findViewById(R.id.lvReview);

		emptyTV.setText("Não há comentários");
		reviewsLV.setEmptyView(emptyTV);

		return fragmentView;
	}

	@Override
	protected void createAdapter(List<Review> model) {
		mAdapter = new ReviewListAdapter(getActivity(), model, presenter);
		reviewsLV.setAdapter(mAdapter);
	}

	@Override
	protected List<Review> destroyAdapter() {
		mAdapter = null;
		reviewsLV.setAdapter(null);
		return null;
	}

	@Override
	public void openReview(Review review) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri parse;
		try {
			parse = Uri.parse(review.getUrl());
		} catch (Exception e) {
			Log.e(TAG, "Erro converter url de comentario para URI", e);
			Toast.makeText(getContext(), "Url de comentário é inválida", Toast.LENGTH_SHORT).show();
			return;
		}

		intent.setData(parse);

		startActivity(intent);
	}

	public interface ReviewFragmentCallbacks extends IPresenter {

		void openReview(Review review);

		void loadReviewsFromMovie(Movie movie);

	}
}
