package com.alex.baking.app.ui.presenter.detail;

import com.alex.baking.app.data.model.Movie;
import com.alex.baking.app.data.model.Review;
import com.alex.baking.app.data.model.Video;
import com.alex.baking.app.ui.presenter.BasePresenter;
import com.alex.baking.app.ui.view.fragment.SimpleFragment;
import com.alex.baking.app.ui.view.moviedetail.InfoDetailsFragment;
import com.alex.baking.app.ui.view.moviedetail.ReviewDetailsFragment;
import com.alex.baking.app.ui.view.moviedetail.VideoDetailsFragment;

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

	interface InfoView extends SimpleFragment<Movie> {

		void updateFavoriteMovieStatus(Movie movie);
	}

	interface VideoView extends SimpleFragment<List<Video>> {

		void openVideo(Video video);

	}

	interface ReviewView extends SimpleFragment<List<Review>> {

		void openReview(Review review);

	}
}
