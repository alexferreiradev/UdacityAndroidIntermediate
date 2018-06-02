package com.alex.popularmovies.app.data.source.remote.network;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * Created by alex on 02/06/18.
 */
public class NetworkResourceManagerTest {

	@Test
	public void getStringFromURL() throws Exception {
		NetworkResourceManager networkResourceManager = new NetworkResourceManager();
		String resourceFromURL = networkResourceManager.getStringResourceFromURL(new URL("http://www.google.com"));

		assertNotNull(resourceFromURL);
	}

	@Test
	public void getStringFromURL2() throws Exception {
		NetworkResourceManager networkResourceManager = new NetworkResourceManager();
		String resourceFromURL = networkResourceManager.getStringResourceFromURL(new URL("http://api.themoviedb.org/3/movie/popular?api_key=123&page=1"));

		assertNotNull(resourceFromURL);
	}

}