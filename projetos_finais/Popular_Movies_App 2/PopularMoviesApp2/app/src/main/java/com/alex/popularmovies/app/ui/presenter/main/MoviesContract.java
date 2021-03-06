package com.alex.popularmovies.app.ui.presenter.main;

import android.view.Menu;
import android.view.MenuItem;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.MoviesType;
import com.alex.popularmovies.app.ui.presenter.BaseListContract;
import com.alex.popularmovies.app.ui.presenter.IPresenter;

/**
 * Created by Alex on 17/12/2017.
 */

public interface MoviesContract {

	interface Presenter extends IPresenter {

		void setListType(MoviesType moviesType);

		void setListScroolPosition(int position);

		MoviesType getCurrentListType();

	}

	interface View extends BaseListContract.View<Movie> {

		boolean onOptionsItemSelected(MenuItem item);

		boolean onCreateOptionsMenu(Menu menu);

		void updateMenuItems();
	}
}
