package com.alex.popularmovies.app.data.source.exception;

/**
 * Created by alex on 28/05/18.
 */

public class NullConnectionException extends Exception {
	public NullConnectionException() {
		super();
	}

	public NullConnectionException(String message) {
		super(message);
	}

	public NullConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
