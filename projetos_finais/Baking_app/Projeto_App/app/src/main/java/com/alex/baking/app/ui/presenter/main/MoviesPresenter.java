package com.alex.baking.app.ui.presenter.main;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.alex.baking.app.R;
import com.alex.baking.app.data.exception.DataException;
import com.alex.baking.app.data.model.Movie;
import com.alex.baking.app.data.model.MoviesType;
import com.alex.baking.app.data.repository.movie.MovieRepository;
import com.alex.baking.app.data.repository.movie.MovieRepositoryContract;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.ui.presenter.BaseListPresenter;

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

	private void setMovieGrid(List<Movie> movies, Throwable mThrowable) {
		hideProgressView();
		if (movies == null) {
			if (mThrowable != null) {
				if (mThrowable instanceof ConnectionException) {
					mView.showErrorMsg("Erro de conexão, dispositivo offline");
				}
			} else {
				mView.showErrorMsg("Erro interno ao tentar buscar filmes.");
			}
		} else if (!movies.isEmpty()) {
			if (isNewAdapter()) {
				mView.createListAdapter(movies);
			} else {
				mView.addAdapterData(movies);
			}

			mOffset = mView.getAdapter().getCount();
			setViewAccordingToDataLoaded();
			super.loadUntilLastPosition();

			Log.v(TAG, "Total filmes carregados: " + mOffset);
		}
	}

	@Override
	public void setListType(MoviesType moviesType) {
		this.mListType = moviesType;
		setViewAccordingToDataLoaded();
		reCreateAdapter();
	}

	private void setViewAccordingToDataLoaded() {
		mView.updateMenuItems();
		mView.setActionBarTitle(mListType.name().replaceAll("_", " "));
	}

	@Override
	public void setListScroolPosition(int position) {
		lastPositionInScrool = position;
	}

	@Override
	public MoviesType getCurrentListType() {
		return mListType;
	}

	private List<Movie> getMoviesFromRepository(MoviesType key) throws Throwable {
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
			if (e.getCause() instanceof ConnectionException) {
				throw e.getCause();
			}
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
		private Throwable mThrowable;

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
			try {
				return presenter.getMoviesFromRepository(key);
			} catch (Throwable throwable) {
				mThrowable = throwable;
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<Movie> movies) {
			presenter.setMovieGrid(movies, mThrowable);
		}
	}
}
