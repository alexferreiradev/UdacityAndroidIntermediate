package com.alex.popularmovies.app.data.repository.movie;

import android.util.Log;
import com.alex.popularmovies.app.data.exception.DataException;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.Review;
import com.alex.popularmovies.app.data.model.Video;
import com.alex.popularmovies.app.data.repository.BaseRepository;
import com.alex.popularmovies.app.data.source.DefaultSource;
import com.alex.popularmovies.app.data.source.cache.BaseCache;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;
import com.alex.popularmovies.app.data.source.queryspec.remote.MoviesRemoteQuery;
import com.alex.popularmovies.app.data.source.queryspec.remote.RemoteQuery;
import com.alex.popularmovies.app.data.source.queryspec.remote.ReviewRemoteQuery;
import com.alex.popularmovies.app.data.source.queryspec.remote.VideoRemoteQuery;
import com.alex.popularmovies.app.data.source.queryspec.sql.MoviesLocalQuery;
import com.alex.popularmovies.app.data.source.remote.network.exception.ConnectionException;
import com.alex.popularmovies.app.data.source.sql.PMContract;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class MovieRepository extends BaseRepository<Movie> implements MovieRepositoryContract {
	private static final String TAG = MovieRepository.class.getSimpleName();
	private DefaultSource<Review> mRemoteReviewSource;
	private DefaultSource<Video> mRemoteVideoSource;

	public MovieRepository(BaseCache<Movie> cacheSource, DefaultSource<Movie> localSource, DefaultSource<Movie> remoteSource, DefaultSource<Review> remoteReviewSource, DefaultSource<Video> remoteVideoSource) {
		super(cacheSource, localSource, remoteSource);
		mRemoteReviewSource = remoteReviewSource;
		mRemoteVideoSource = remoteVideoSource;
	}

	@Override
	protected void createCache(List<Movie> data) {
		updateCache(data, 0);
	}

	@Override
	protected void destroyCache(List<Movie> data) {
		mCacheSource.setDirty(true);
	}

	@Override
	protected void updateCache(List<Movie> data, int offset) {
		if (mCacheSource.isDirty() || offset == 0) {
			mCacheSource.updateCacheTo(data);
		} else {
			mCacheSource.addAllCache(data, offset);
		}
	}

	@Override
	public Movie create(Movie model) throws DataException {
		try {
			if (model == null) {
				return null;
			}

			Long id = model.getId();
			if (id == null) {
				return mLocalSource.create(model);
			} else {
				throw new SourceException("Filme já existe na base local.");
			}
		} catch (SourceException e) {
			Log.e(TAG, "Erro de source ao tentar criar ou atualizar um filme");
			throw new DataException("Erro em source: ", e);
		}
	}

	@Override
	public Movie recover(Long id) throws DataException {
		if (id == null || id < 0) {
			return null;
		}

		try {
			Movie movie = mCacheSource.recover(id);
			if (movie == null) {
				Log.d(TAG, "Não foi encontrado o filme no cache, marcando o cache como desatualizado");
				mCacheSource.setDirty(true);
			} else {
				setFavoriteState(movie);
				return movie;
			}

			if (mCacheSource.isDirty()) {
				Log.d(TAG, "Buscando filme em favoritos.");
				movie = mLocalSource.recover(id);
				if (movie == null) {
					Log.w(TAG, "Filme com id: " + id + " não foi encontrado no banco.");
				}
			}

			return movie;
		} catch (ConnectionException e) {
			Log.e(TAG, "Erro ao tentar buscar abrir conexão com API: " + e.getMessage(), e);
			// todo Avisar usuario que sem conexao pode utilizar favoritos
			throw new DataException("Erro de conexao: ", e);
		} catch (SourceException e) {
			Log.e(TAG, "Erro ao tentar buscar no cache o id.", e);
			throw new DataException("Erro em source: ", e);
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: ", e);
			throw new DataException("Erro desconhecido", e);
		}
	}

	private void setFavoriteState(Movie movie) throws DataException {
		try {
			List<String> selectionArgs = new ArrayList<>();
			selectionArgs.add(String.valueOf(movie.getIdFromApi()));
			MoviesLocalQuery moviesLocalQuery = new MoviesLocalQuery(1, 0, PMContract.MovieEntry.COLUMN_ID_FROM_API + "=?", selectionArgs, MovieFilter.POPULAR);
			List<Movie> movieList = mLocalSource.recover(moviesLocalQuery);
			if (movieList != null && movieList.size() == 1) {
				Movie movieFound = movieList.get(0);
				if (movieFound.getIdFromApi().equals(movie.getIdFromApi())) {
					movie.setFavorite(true);
				}
			}
		} catch (SourceException e) {
			Log.e(TAG, "Erro ao tentar buscar no cache o id.", e);
			throw new DataException("Erro em source: ", e);
		} catch (ConnectionException e) {
			Log.e(TAG, "Erro ao tentar buscar no cache o id.", e);
			throw new DataException("Erro em source: ", e);
		}
	}

	@Override
	public Movie update(Movie model) throws DataException {
		try {
			if (model == null) {
				return null;
			}

			Long id = model.getId();
			if (id == null) {
				throw new DataException("Objeto não existe para ser atualizado. Model sem ID");
			} else {
				return mLocalSource.update(model);
			}
		} catch (SourceException e) {
			Log.e(TAG, "Erro de source ao tentar criar ou atualizar um model");
			throw new DataException("Erro em source: ", e);
		}
	}

	@Override
	public Movie delete(Movie model) throws DataException {
		throw new DataException("Metodo não permitido para esta versao");
	}

	@Override
	public List<Movie> moviesByPopularity(int limit, int offset) throws DataException {
		RemoteQuery querySpec = new MoviesRemoteQuery(limit, offset, MovieFilter.POPULAR);
		return getMoviesByQuery(querySpec);
	}

	@Override
	public List<Movie> moviesByTopRate(int limit, int offset) throws DataException {
		RemoteQuery querySpec = new MoviesRemoteQuery(limit, offset, MovieFilter.TOP_RATED);
		return getMoviesByQuery(querySpec);
	}

	@Override
	public Movie updateMovieFavoriteStatus(Movie movie) throws DataException {
		try {
			if (movie == null) {
				throw new DataException("Filme para ser atualizado estado de favorito nulo");
			} else {
				if (movie.isFavorite()) {
					movie = mLocalSource.create(movie);
				} else {
					if (movie.getId() == null) {
						throw new DataException("Filme para ser removido dos favoritos com id null");
					}

					movie = mLocalSource.delete(movie);
				}
			}
		} catch (DataException e) {
			throw e;
		} catch (SourceException e) {
			Log.e(TAG, e.getMessage());
			throw new DataException("Erro em source: ", e);
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: ", e);
			throw new DataException("Erro desconhecido: ", e);
		}

		return movie;
	}

	@Override
	public List<Movie> favoriteMovieList(int limit, int offset, MovieFilter filter) throws DataException {
		List<Movie> movies;

		try {
			if (filter == null) {
				filter = MovieFilter.POPULAR;
			}

			MoviesLocalQuery querySpecification = new MoviesLocalQuery(limit, offset, filter);

			movies = mLocalSource.recover(querySpecification);
			updateCache(movies, querySpecification.getOffset());
		} catch (SourceException e) {
			Log.e(TAG, e.getMessage());
			throw new DataException("Erro em source: ", e);
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: ", e);
			throw new DataException("Erro desconhecido: ", e);
		}

		return movies;
	}

	@Override
	public List<Review> reviewListByMovie(Long movieId, int limit, int offset) throws DataException {
		List<Review> reviewList;

		try {
			QuerySpecification<URL> querySpec = new ReviewRemoteQuery(limit, offset, movieId);
			reviewList = mRemoteReviewSource.recover(querySpec);
//			updateCache(reviewList, querySpec.getOffset()); // TODO: 02/06/18 Adicionar metodo update item
		} catch (SourceException e) {
			Log.e(TAG, e.getMessage());
			throw new DataException("Erro em source: ", e);
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: ", e);
			throw new DataException("Erro desconhecido: ", e);
		}

		return reviewList;
	}

	@Override
	public List<Video> videoListByMovie(Long movieId, int limit, int offset) throws DataException {
		List<Video> videoList;

		try {
			QuerySpecification<URL> querySpec = new VideoRemoteQuery(limit, offset, movieId);
			videoList = mRemoteVideoSource.recover(querySpec);
//			updateCache(reviewList, querySpec.getOffset()); // TODO: 02/06/18 Adicionar metodo update item
		} catch (SourceException e) {
			Log.e(TAG, e.getMessage());
			throw new DataException("Erro em source: ", e);
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: ", e);
			throw new DataException("Erro desconhecido: ", e);
		}

		return videoList;
	}

	@Override
	public boolean hasCache() {
		return !mCacheSource.isDirty();
	}

	@Override
	public List<Movie> getCurrentCache() {
		return mCacheSource.isDirty() ? new ArrayList<>() : mCacheSource.getCache();
	}

	private List<Movie> getMoviesByQuery(QuerySpecification querySpecification) throws DataException {
		List<Movie> movies;

		try {
			movies = mRemoteSource.recover(querySpecification);
			updateCache(movies, querySpecification.getOffset());
		} catch (SourceException e) {
			Log.e(TAG, e.getMessage());
			throw new DataException("Erro em source: ", e);
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: ", e);
			throw new DataException("Erro desconhecido: ", e);
		}

		return movies;
	}

	@Override
	public void setCacheToDirty() {
		mCacheSource.setDirty(true);
	}

	public enum MovieFilter {
		POPULAR,
		TOP_RATED,
		FAVORITE,
	}
}
