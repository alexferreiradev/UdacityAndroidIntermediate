package com.alex.popularmovies.app.data.source.queryspec.remote;

import android.support.test.runner.AndroidJUnit4;
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
public class ReviewRemoteQueryTest {

	private ReviewRemoteQuery spec;

	@Before
	public void setUp() throws Exception {
		spec = new ReviewRemoteQuery(0, 0, 1L);
	}

	@Test
	public void getQuery() throws Exception {
		URL query = spec.getQuery();

		assertNotNull(query);
		assertEquals("http", query.getProtocol());
		assertEquals("api.themoviedb.org", query.getAuthority());
		assertEquals("api.themoviedb.org", query.getHost());
		assertEquals("/3/movie/1/reviews", query.getPath());
		assertEquals(-1, query.getPort());
		assertEquals(80, query.getDefaultPort());
		String queryFromUrl = query.getQuery();
		Pattern compile = Pattern.compile("api_key=[a-z|0-9]+&page=1");
		Matcher matcher = compile.matcher(queryFromUrl);
		assertTrue(matcher.find());
	}

}