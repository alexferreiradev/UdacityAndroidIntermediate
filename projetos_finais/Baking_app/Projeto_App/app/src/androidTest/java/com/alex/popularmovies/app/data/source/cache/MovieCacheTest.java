package com.alex.popularmovies.app.data.source.cache;

import android.support.test.runner.AndroidJUnit4;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.BaseQuerySpec;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alex on 31/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class MovieCacheTest {

	private MovieCache cache;

	@Before
	public void setUp() throws Exception {
		cache = MovieCache.getInstance();
	}

	@Test
	public void recover_id() throws Exception {
		List<Movie> movieCache = this.cache.getCache();
		Movie movie = new Movie();
		movie.setIdFromApi(1L);
		movieCache.add(movie);

		Movie movieRecovered = cache.recover(1L);

		assertNotNull(movieRecovered);
		assertEquals(movie, movieRecovered);
		assertEquals(new Long(1L), movieRecovered.getIdFromApi());
	}

	@Test
	public void delete() throws Exception {
		List<Movie> movieCache = this.cache.getCache();
		Movie movie = new Movie();
		movieCache.add(movie);
		assertTrue(movieCache.size() == 1);

		Movie delete = cache.delete(movie);

		assertNotNull(delete);
		assertTrue(movieCache.isEmpty());
		assertFalse(movieCache.contains(movie));
	}

	@Test(expected = SourceException.class)
	public void recover() throws Exception {
		List<Movie> movies = cache.recover(new BaseQuerySpec(0, 0) {
			@Override
			public Object getQuery() {
				return null;
			}
		});
	}

	@Test(expected = SourceException.class)
	public void update() throws Exception {
		cache.update(null);
	}

	@Test(expected = SourceException.class)
	public void create() throws Exception {
		cache.create(null);
	}

	@Test
	public void addAllCache() throws Exception {
	}

	@Test
	public void removeAllCache() throws Exception {
	}

}