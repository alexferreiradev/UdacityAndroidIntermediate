package com.alex.baking.app.data.source.remote.network;

import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;

import java.net.URL;

/**
 * Created by alex on 02/06/18.
 */

public interface NetworkResource {

	String getStringResourceFromURL(URL url) throws ConnectionException;
}
