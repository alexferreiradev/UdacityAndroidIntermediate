package com.alex.popularmovies.app.data.repository.movie;

import android.support.test.runner.AndroidJUnit4;
import com.alex.popularmovies.app.data.exception.DataException;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.cache.MovieCache;
import com.alex.popularmovies.app.data.source.exception.NullConnectionException;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;
import com.alex.popularmovies.app.data.source.remote.RemoteMovieSource;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 31/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class MovieRepositoryTest {

	@Mock(name = "mRemoteSource")
	private RemoteMovieSource remoteSource;
	@Mock(name = "mLocalSource")
	private MovieSql localSource;
	@Mock(name = "mCacheSource")
	private MovieCache cacheSource = MovieCache.getInstance();

	@InjectMocks
	private MovieRepository movieRepository;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		movieRepository = new MovieRepository(cacheSource, localSource, remoteSource);
	}

	@Test
	public void test_recover() throws DataException, SourceException, NullConnectionException {
		Movie movieMock = mock(Movie.class);
		when(movieMock.getId()).thenReturn(2L);
		when(cacheSource.recover(anyLong())).thenReturn(null);
		when(cacheSource.isDirty()).thenReturn(true);
		when(remoteSource.recover(anyLong())).thenReturn(movieMock);

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

	@Test(expected = DataException.class)
	public void test_update() throws DataException {
		movieRepository.update(null);
	}

	@Test(expected = DataException.class)
	public void test_create() throws DataException {
		movieRepository.create(null);
	}

	@Test(expected = DataException.class)
	public void test_delete() throws DataException {
		movieRepository.delete(null);
	}

}