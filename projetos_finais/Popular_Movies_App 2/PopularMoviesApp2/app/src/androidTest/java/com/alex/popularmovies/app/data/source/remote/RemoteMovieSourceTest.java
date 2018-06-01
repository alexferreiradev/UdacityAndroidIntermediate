package com.alex.popularmovies.app.data.source.remote;

import android.support.test.runner.AndroidJUnit4;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.remote.MoviesRemoteQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by alex on 31/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class RemoteMovieSourceTest {

	@InjectMocks
	RemoteMovieSource source;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = SourceException.class)
	public void create() throws Exception {
		source.create(null);
	}

	@Test
	public void recover_by_id() throws Exception {
		Movie movie = source.recover(1L);

		assertNotNull(movie);
	}

	@Test
	public void recover() throws Exception {
		List<Movie> movies = source.recover(new MoviesRemoteQuery(0, 0, MoviesRemoteQuery.MovieFilter.POPULAR));

		assertNotNull(movies);
		assertFalse(movies.isEmpty());
	}

	@Test(expected = SourceException.class)
	public void update() throws Exception {
		source.update(null);
	}

	@Test(expected = SourceException.class)
	public void delete() throws Exception {
		source.delete(null);
	}

}