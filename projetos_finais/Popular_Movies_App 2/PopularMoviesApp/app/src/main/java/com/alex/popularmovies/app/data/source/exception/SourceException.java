package com.alex.popularmovies.app.data.source.exception;

/**
 * Created by Alex on 05/11/2017.
 */

public class SourceException extends Exception {
	public SourceException() {
		super("Erro ao tentar recuperar/alterar dado em fonte de dados");
	}

	public SourceException(String message) {
		super(message);
	}

	public SourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public SourceException(Throwable cause) {
		super(cause);
	}
}
