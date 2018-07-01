package com.alex.popularmovies.app.data.source.remote.network;

import com.alex.popularmovies.app.data.source.remote.network.exception.ConnectionException;
import com.alex.popularmovies.app.data.source.remote.network.exception.NetworkResourceException;

import java.net.URL;

/**
 * Created by alex on 02/06/18.
 */

public interface NetworkResource {

	String getStringResourceFromURL(URL url) throws NetworkResourceException, ConnectionException;
}
