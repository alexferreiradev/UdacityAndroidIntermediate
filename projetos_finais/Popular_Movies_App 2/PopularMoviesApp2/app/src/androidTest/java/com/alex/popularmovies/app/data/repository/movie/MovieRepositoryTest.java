package com.alex.popularmovies.app.data.repository.movie;

import android.support.test.runner.AndroidJUnit4;
import com.alex.popularmovies.app.data.exception.DataException;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.Review;
import com.alex.popularmovies.app.data.model.Video;
import com.alex.popularmovies.app.data.source.cache.MovieCache;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;
import com.alex.popularmovies.app.data.source.queryspec.sql.MoviesLocalQuery;
import com.alex.popularmovies.app.data.source.remote.RemoteMovieSource;
import com.alex.popularmovies.app.data.source.remote.RemoteReviewSource;
import com.alex.popularmovies.app.data.source.remote.RemoteVideoSource;
import com.alex.popularmovies.app.data.source.remote.network.exception.NullConnectionException;
import com.alex.popularmovies.app.data.source.sql.MovieSql;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by alex on 31/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class MovieRepositoryTest {

	@Mock(name = "mRemoteSource")
	private RemoteMovieSource remoteSource;
	@Mock(name = "mLocalSource")
	private MovieSql localSource;
	@Mock(name = "mRemoteReviewSource")
	private RemoteReviewSource remoteReviewSource;
	@Mock(name = "mRemoteVideoSource")
	private RemoteVideoSource remoteVideoSource;
	@Mock(name = "mCacheSource")
	private MovieCache cacheSource = MovieCache.getInstance();

	@InjectMocks
	private MovieRepository movieRepository;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		movieRepository = new MovieRepository(cacheSource, localSource, remoteSource, remoteReviewSource, remoteVideoSource);
	}

	@Test
	public void test_recover() throws DataException, SourceException, NullConnectionException {
		Movie movieMock = mock(Movie.class);

		when(movieMock.getId()).thenReturn(2L);
		when(cacheSource.recover(anyLong())).thenReturn(null);
		when(cacheSource.isDirty()).thenReturn(true);
		when(localSource.recover(anyLong())).thenReturn(movieMock);

		Movie movie = movieRepository.recover(1L);

		assertEquals(movieMock, movie);
		assertEquals(new Long(2L), movie.getId());
	}

	@Test
	public void test_find_favorite_movies() throws Exception {
		List<Movie> validMovies = new ArrayList<>();
		validMovies.add(new Movie());
		when(remoteSource.recover(any(QuerySpecification.class))).thenReturn(validMovies);
		when(cacheSource.isNewCache()).thenReturn(true);
		List<Movie> movies = movieRepository.moviesByPopularity(0, 0);

		assertNotNull(movies);
		assertFalse(movies.isEmpty());
	}

	@Test
	public void test_find_top_movies() throws Exception {
		List<Movie> validMovies = new ArrayList<>();
		validMovies.add(new Movie());
		when(remoteSource.recover(any(QuerySpecification.class))).thenReturn(validMovies);
		when(cacheSource.isNewCache()).thenReturn(true);
		List<Movie> movies = movieRepository.moviesByTopRate(0, 0);

		assertNotNull(movies);
		assertFalse(movies.isEmpty());
	}

	@Test
	public void test_list_favorite() throws Exception {
		List<Movie> validMovies = new ArrayList<>();
		validMovies.add(new Movie());
		MoviesLocalQuery localQuery = new MoviesLocalQuery(0, 0, MovieRepository.MovieFilter.POPULAR);
		when(localSource.recover(localQuery)).thenReturn(validMovies);
		when(cacheSource.isNewCache()).thenReturn(true);
		doNothing().when(cacheSource).updateCacheTo(validMovies);

		List<Movie> movies = movieRepository.favoriteMovieList(0, 0, MovieRepository.MovieFilter.POPULAR);

		assertNotNull(movies);
		assertFalse(movies.isEmpty());
		assertEquals(1, movies.size());
	}

	@Test
	public void test_review_by_movie() throws Exception {
		List<Review> validReview = new ArrayList<>();
		validReview.add(new Review());
		when(remoteReviewSource.recover(any(QuerySpecification.class))).thenReturn(validReview);
		List<Review> reviews = movieRepository.reviewListByMovie(1L, 0, 0);

		assertNotNull(reviews);
		assertFalse(reviews.isEmpty());
	}

	@Test
	public void test_video_by_movie() throws Exception {
		List<Video> validVideoList = new ArrayList<>();
		validVideoList.add(new Video());
		when(remoteVideoSource.recover(any(QuerySpecification.class))).thenReturn(validVideoList);
		List<Video> reviews = movieRepository.videoListByMovie(1L, 0, 0);

		assertNotNull(reviews);
		assertFalse(reviews.isEmpty());
	}

	public void test_update() throws DataException {
		movieRepository.update(null);
	}

	@Test
	public void test_create() throws Exception {
		Movie movieMock = mock(Movie.class);
		Movie movieMockAnswer = mock(Movie.class);
		when(localSource.create(movieMock)).thenReturn(movieMockAnswer);
		when(movieMock.getId()).thenReturn(null);
		when(movieMockAnswer.getId()).thenReturn(2L);

		Movie movieResult = movieRepository.create(movieMock);

		assertNotNull(movieResult);
		assertEquals(new Long(2L), movieResult.getId());
	}

	@Test(expected = DataException.class)
	public void test_delete() throws DataException {
		movieRepository.delete(null);
	}

}