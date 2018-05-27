package com.alex.popularmovies.app.ui.presenter.detail;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.ui.presenter.BasePresenter;

/**
 * Created by Alex on 17/12/2017.
 */

public interface DetailContract {

	public interface Presenter {
		void setMovieView(Movie movie);

		void setFavoriteOnRepository(Movie movie);

		void markAsFavorite(Movie movie);
	}

	public interface View extends BasePresenter.View<Movie> {
		Long getMovieId();

		void bindMovieViewData(Movie movie);

		void setFavOn();

		void setFavOff();

		void closeAndShowMovieGrid();
	}
}
