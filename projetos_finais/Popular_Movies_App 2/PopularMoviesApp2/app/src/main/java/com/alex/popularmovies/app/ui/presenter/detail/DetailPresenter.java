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
	public void loadMovie() {
		mInfoView.showProgressBar(-1);
		mInfoView.startView(null);
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

	@Override
	public void updateMovieFavoriteStatus(Movie movie, boolean newStatus) {
		movie.setFavorite(newStatus);
		UpdateFavoriteMovieStatus updateFavoriteMovieStatus = new UpdateFavoriteMovieStatus(this);
		updateFavoriteMovieStatus.execute(movie);
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
			return mRepository.videoListByMovie(movie.getIdFromApi(), 0, 0);
		} catch (DataException e) {
			Log.e(TAG, "Erro ao tentar carregar videos para o filme: " + movie.getIdFromApi(), e);
		}

		return null;
	}

	private List<Review> loadReviewsFromRepo(Movie movie) {
		try {
			return mRepository.reviewListByMovie(movie.getIdFromApi(), 0, 0);
		} catch (DataException e) {
			Log.e(TAG, "Erro ao tentar carregar comentarios para o filme: " + movie.getIdFromApi(), e);
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

	private Movie updateMovieFavoriteStatusInRepo(Movie movie) {
		try {
			return mRepository.updateMovieFavoriteStatus(movie);
		} catch (DataException e) {
			Log.e(TAG, "Erro ao tentar alterar status do filme: " + movie.getTitle(), e);
		}

		return null;
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
				presenter.mView.showErrorMsg("Videos de filme não poderam ser carregado");
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
				presenter.mView.showErrorMsg("Comentario de filme não pode ser carregado");
				return;
			}

			presenter.mReviewView.hideProgressBar();
			presenter.mReviewView.startView(reviewList);
		}

	}

	private static class UpdateFavoriteMovieStatus extends AsyncTask<Movie, Integer, Movie> {
		private DetailPresenter presenter;

		UpdateFavoriteMovieStatus(DetailPresenter presenter) {
			this.presenter = presenter;
		}

		@Override
		protected void onPreExecute() {
			presenter.mInfoView.showProgressBar(-1);
		}

		@Override
		protected Movie doInBackground(Movie... movies) {
			return presenter.updateMovieFavoriteStatusInRepo(movies[0]);
		}

		@Override
		protected void onPostExecute(Movie movie) {
			if (movie == null) {
				Log.e(TAG, "Ocorreu algum erro no repositorio ao tentar atualizar o estado de favorito do filme");
				presenter.mView.showErrorMsg("Filme não pode ser atualizado");
				return;
			}

			if (movie.isFavorite()) {
				presenter.mView.showSuccessMsg("Filme favoritado");
			} else {
				presenter.mView.showSuccessMsg("Filme removido dos favoritos");
			}

			presenter.mInfoView.hideProgressBar();
			presenter.mInfoView.updateFavoriteMovieStatus(movie);
		}

	}
}
