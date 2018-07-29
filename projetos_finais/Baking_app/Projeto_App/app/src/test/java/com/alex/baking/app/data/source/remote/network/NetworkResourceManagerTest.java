package com.alex.baking.app.data.source.remote.network;

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

}