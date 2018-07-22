package com.alex.baking.app.data.source.queryspec.remote;

import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.data.repository.movie.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by alex on 02/06/18.
 */
@RunWith(AndroidJUnit4.class)
public class MoviesRemoteQueryTest {

	private MoviesRemoteQuery spec;

	@Before
	public void setUp() throws Exception {
		spec = new MoviesRemoteQuery(0, 0, MovieRepository.MovieFilter.POPULAR);
	}

	@Test
	public void getQuery() throws Exception {
		URL query = spec.getQuery();

		assertNotNull(query);
		assertEquals("http", query.getProtocol());
		assertEquals("api.themoviedb.org", query.getAuthority());
		assertEquals("api.themoviedb.org", query.getHost());
		assertEquals("/3/movie/popular", query.getPath());
		assertEquals(-1, query.getPort());
		assertEquals(80, query.getDefaultPort());
		String queryFromUrl = query.getQuery();
		Pattern compile = Pattern.compile("api_key=[a-z|0-9]+&page=1");
		Matcher matcher = compile.matcher(queryFromUrl);
		assertTrue(matcher.find());
	}

}