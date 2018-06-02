package com.alex.popularmovies.app.data.source.remote.network.exception;

/**
 * Created by alex on 02/06/18.
 */

public class NetworkResourceException extends Exception {
	public NetworkResourceException(String message) {
		super(message);
	}

	public NetworkResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NetworkResourceException(Throwable cause) {
		super(cause);
	}

	protected NetworkResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
