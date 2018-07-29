package com.alex.baking.app.data.exception;

/**
 * Created by Alex on 17/12/2017.
 */

public class DataException extends Exception {

	public DataException(String especificMessage) {
		super("Erro ao tentar manipular um modelo: " + especificMessage);
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataException(Throwable cause) {
		super(cause);
	}
}
