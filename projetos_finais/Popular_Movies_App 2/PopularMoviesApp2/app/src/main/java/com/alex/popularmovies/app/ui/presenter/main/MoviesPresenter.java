package com.alex.popularmovies.app.ui.presenter.main;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.exception.DataException;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.MoviesType;
import com.alex.popularmovies.app.data.repository.movie.MovieRepository;
import com.alex.popularmovies.app.data.repository.movie.MovieRepositoryContract;
import com.alex.popularmovies.app.ui.presenter.BaseListPresenter;

import java.util.List;

/**
 * Created by Alex on 23/03/2017.
 */

public class MoviesPresenter extends BaseListPresenter<MoviesContract.View, Movie, MovieRepositoryContract> implements MoviesContract.Presenter {

	private static final String TAG = MoviesPresenter.class.getSimpleName();

	private ListMovieByKey movieByKey;
	private MoviesType mListType;

	public MoviesPresenter(MoviesContract.View mView, Context mContext, Bundle savedInstanceState, MovieRepositoryContract mRepository, MoviesType moviesType) {
		super(mView, mContext, savedInstanceState, mRepository);
		mListType = moviesType;
	}

	@Override
	protected void setEmptyView() {
		mView.setEmptyView(mContext.getString(R.string.none_movie_loaded));
	}

	@Override
	protected void loadDataFromSource() {
		Log.d(TAG, "Load from source. Offset: " + mOffset);
		movieByKey = new ListMovieByKey(mListType, this);
		movieByKey.execute();
	}

	@Override
	public void selectItemClicked(Movie item, int pos) {
		Log.i(TAG, "Filme selecionado: " + item.getIdFromApi());
		lastPositionInScrool = pos;

		mView.showDataView(item);
	}

	@Override
	protected void applyFilterFromAdapter() {
		movieByKey = new ListMovieByKey(mListType, this);
		movieByKey.execute();
	}

	private void setMovieGrid(List<Movie> movies) {
		hideProgressView();
		if (movies == null) {
			mView.showErrorMsg("Erro ao tentar buscar filmes.");
		} else if (!movies.isEmpty()) {
			if (isNewAdapter()) {
				mView.createListAdapter(movies);
				mView.setGridScroolPosition(mView.getAdapter().getCount());
			} else {
				mView.addAdapterData(movies);
			}

			mOffset = mView.getAdapter().getCount();
			setViewAccordingToDataLoaded();
			Log.v(TAG, "Total filmes carregados: " + mOffset);
		}
	}

	@Override
	public void setListType(MoviesType moviesType) {
		this.mListType = moviesType;
		reCreateAdapter();
	}

	protected void setViewAccordingToDataLoaded() {
		mView.updateMenuItems();
		mView.setActionBarTitle(mListType.name().replaceAll("_", " "));
		super.loadUntilLastPosition();
	}

	@Override
	public void setListScroolPosition(int position) {
		lastPositionInScrool = position;
	}

	@Override
	public MoviesType getCurrentListType() {
		return mListType;
	}

	private List<Movie> getMoviesFromRepository(MoviesType key) {
		try {
			Log.d(TAG, "Load background: key: " + key + " offset: " + mOffset);
			switch (key) {
				case MOST_POPULAR:
					return mRepository.moviesByPopularity(mLoadItemsLimit, mOffset);
				case TOP_VOTED:
					return mRepository.moviesByTopRate(mLoadItemsLimit, mOffset);
				case FAVORITE:
					return mRepository.favoriteMovieList(mLoadItemsLimit, mOffset, MovieRepository.MovieFilter.POPULAR);
			}

			return mRepository.moviesByPopularity(mLoadItemsLimit, mOffset);
		} catch (DataException e) {
			Log.e(TAG, "Erro ao tentar listar filmes", e);
		}

		return null;
	}

	@Override
	protected void initialize() {
		if (isNewAdapter()) {
			Log.d(TAG, "Init com new adapter");
			super.initialize();
		} else {
			Log.d(TAG, "Init com load more");
			loadMoreData(0, 0, mView.getAdapter().getCount());
		}
	}

	private static class ListMovieByKey extends AsyncTask<String, Integer, List<Movie>> {
		private MoviesType key;
		private MoviesPresenter presenter;

		ListMovieByKey(MoviesType key, MoviesPresenter presenter) {
			this.key = key;
			this.presenter = presenter;
		}

		@Override
		protected void onPreExecute() {
			presenter.showProgressView();
		}

		@Override
		protected List<Movie> doInBackground(String... params) {
			return presenter.getMoviesFromRepository(key);
		}

		@Override
		protected void onPostExecute(List<Movie> movies) {
			presenter.setMovieGrid(movies);
		}
	}
}
