package com.alex.popularmovies.app.data.source.remote.network.exception;

/**
 * Created by alex on 28/05/18.
 */

public class ConnectionException extends Exception {
	public ConnectionException() {
		super();
	}

	public ConnectionException(String message) {
		super(message);
	}

	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
