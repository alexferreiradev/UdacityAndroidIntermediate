package com.alex.popularmovies.app.ui.presenter.detail;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.alex.popularmovies.app.data.exception.DataException;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.Review;
import com.alex.popularmovies.app.data.model.Video;
import com.alex.popularmovies.app.data.repository.movie.MovieRepositoryContract;
import com.alex.popularmovies.app.ui.presenter.BasePresenter;

import java.util.List;

/**
 * Created by Alex on 23/03/2017.
 */

public class DetailPresenter extends BasePresenter<DetailContract.View, Movie, MovieRepositoryContract> implements DetailContract.Presenter {

	private static final String TAG = DetailPresenter.class.getSimpleName();

	private DetailContract.InfoView mInfoView;
	private DetailContract.VideoView mVideoView;
	private DetailContract.ReviewView mReviewView;

	public DetailPresenter(DetailContract.View mView, Context mContext, Bundle savedInstanceState, MovieRepositoryContract mRepository) {
		super(mView, mContext, savedInstanceState, mRepository);
	}

	@Override
	protected void initialize() {
		AsyncTask<Long, Integer, Movie> findMovieById = new FindMovieById(this);
		findMovieById.execute(mView.getMovieId());
	}

	@Override
	public void favoriteMovie(Movie movie) {

	}

	@Override
	public void unFavoriteMovie(Movie movie) {

	}

	@Override
	public void loadMovie() {
		mInfoView.showProgressBar(-1);
		Movie data = mView.getData();
		mInfoView.startView(data);
		mInfoView.hideProgressBar();
	}

	@Override
	public void openReview(Review review) {
		if (review == null || review.getUrl() == null) {
			mView.showErrorMsg("Comentário não é valido para ser aberto");
		}

		mReviewView.openReview(review);
	}

	@Override
	public void loadReviewsFromMovie(Movie movie) {
		FindReviewByMovie findReviewByMovie = new FindReviewByMovie(this);
		findReviewByMovie.execute(movie);
	}

	@Override
	public void playVideo(Video video) {
		if (video == null || video.getSite() == null || video.getSite().isEmpty()) {
			mView.showErrorMsg("Video não é válido para ser aberto");
		}

		mVideoView.openVideo(video);
	}

	@Override
	public void loadVideosFromMovie(Movie movie) {
		FindVideoByMovie findVideoByMovie = new FindVideoByMovie(this);
		findVideoByMovie.execute(movie);
	}

	@Override
	public void setInfoView(DetailContract.InfoView view) {
		mInfoView = view;
	}

	@Override
	public void setVideoView(DetailContract.VideoView view) {
		mVideoView = view;
	}

	@Override
	public void setReviewView(DetailContract.ReviewView view) {
		mReviewView = view;
	}

	@Nullable
	private Movie getMovieFromRepository(Long movieId) {
		try {
			return mRepository.recover(movieId);
		} catch (DataException e) {
			Log.e(TAG, "Erro durante recuperacao de filme por id: ", e);
		}

		return null;
	}

	private List<Video> getVideosFromRepository(Movie movie) {
		try {
			return mRepository.videoListByMovie(movie.getId(), 0, 0);
		} catch (DataException e) {
			Log.e(TAG, "Erro ao tentar carregar videos para o filme: " + movie.getId(), e);
		}

		return null;
	}

	private List<Review> loadReviewsFromRepo(Movie movie) {
		try {
			return mRepository.reviewListByMovie(movie.getId(), 0, 0);
		} catch (DataException e) {
			Log.e(TAG, "Erro ao tentar carregar comentarios para o filme: " + movie.getId(), e);
		}

		return null;
	}

	private static class FindMovieById extends AsyncTask<Long, Integer, Movie> {
		private DetailPresenter presenter;

		FindMovieById(DetailPresenter presenter) {
			this.presenter = presenter;
		}

		@Override
		protected void onPreExecute() {
			presenter.showProgressView();
		}

		@Override
		protected Movie doInBackground(Long... params) {
			return presenter.getMovieFromRepository(params[0]);
		}

		@Override
		protected void onPostExecute(Movie movie) {
			presenter.hideProgressView();
			if (movie == null) {
				presenter.mView.showErrorMsg("Erro ao buscar filme");
				presenter.mView.closeAndShowMovieGrid();
			} else {
				presenter.mView.setDataAndStartFragments(movie);
			}
		}
	}

	private static class FindVideoByMovie extends AsyncTask<Movie, Integer, List<Video>> {
		private DetailPresenter presenter;

		FindVideoByMovie(DetailPresenter presenter) {
			this.presenter = presenter;
		}

		@Override
		protected void onPreExecute() {
			presenter.mVideoView.showProgressBar(-1);
		}

		@Override
		protected List<Video> doInBackground(Movie... movies) {
			return presenter.getVideosFromRepository(movies[0]);
		}

		@Override
		protected void onPostExecute(List<Video> videos) {
			if (videos == null) {
				Log.e(TAG, "Ocorreu algum erro no repositorio, os videos não foram carregados");
				return;
			}

			presenter.mVideoView.hideProgressBar();
			presenter.mVideoView.startView(videos);
		}

	}

	private static class FindReviewByMovie extends AsyncTask<Movie, Integer, List<Review>> {
		private DetailPresenter presenter;

		FindReviewByMovie(DetailPresenter presenter) {
			this.presenter = presenter;
		}

		@Override
		protected void onPreExecute() {
			presenter.mReviewView.showProgressBar(-1);
		}

		@Override
		protected List<Review> doInBackground(Movie... movies) {
			return presenter.loadReviewsFromRepo(movies[0]);
		}

		@Override
		protected void onPostExecute(List<Review> reviewList) {
			if (reviewList == null) {
				Log.e(TAG, "Ocorreu algum erro no repositorio, os comentarios do video não foram carregados");
				return;
			}

			presenter.mReviewView.hideProgressBar();
			presenter.mReviewView.startView(reviewList);
		}

	}
}
