package com.alex.popularmovies.app.ui.presenter.detail;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.Review;
import com.alex.popularmovies.app.data.model.Video;
import com.alex.popularmovies.app.ui.presenter.BasePresenter;
import com.alex.popularmovies.app.ui.view.fragment.SimpleFragment;
import com.alex.popularmovies.app.ui.view.moviedetail.InfoDetailsFragment;
import com.alex.popularmovies.app.ui.view.moviedetail.ReviewDetailsFragment;
import com.alex.popularmovies.app.ui.view.moviedetail.VideoDetailsFragment;

import java.util.List;

/**
 * Created by Alex on 17/12/2017.
 */

public interface DetailContract {

	interface Presenter extends
			InfoDetailsFragment.InfoFragmentCallbacks,
			VideoDetailsFragment.VideoFragmentCallBacks,
			ReviewDetailsFragment.ReviewFragmentCallbacks {

		void setInfoView(InfoView view);

		void setVideoView(VideoView view);

		void setReviewView(ReviewView view);
	}

	interface View extends BasePresenter.View<Movie> {
		Long getMovieId();

		void setDataAndStartFragments(Movie movie);

		Movie getData();

		void closeAndShowMovieGrid();
	}

	public interface InfoView extends SimpleFragment<Movie> {

		void markMovieFavorite();

		void unFavoriteMovie();

	}

	public interface VideoView extends SimpleFragment<List<Video>> {

		void openVideo(Video video);

	}

	public interface ReviewView extends SimpleFragment<List<Review>> {

		void openReview(Review review);

	}
}
