package com.alex.baking.app.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alex on 11/12/2016.
 */

public final class Util {

	private static final String TAG = Util.class.getSimpleName();

	/**
	 * Formata data para o padrão FULL
	 *
	 * @param date data a ser convertida
	 * @return string da data formatada
	 */
	public static String formatDate(Date date) {
		return formatDate(date, SimpleDateFormat.FULL);
	}

	/**
	 * Formata data para um padrão escolhido.
	 *
	 * @param date obj a ser convertido em string
	 * @param dateStyle - formato da string a ser utilizado
	 * @return string da data formatada
	 */
	public static String formatDate(Date date, int dateStyle) {
		if (date == null)
			return null;

		DateFormat format = SimpleDateFormat.getDateInstance(dateStyle, Locale.getDefault());
		return format.format(date);
	}

	/**
	 * Usa FULL como estilo padrao.
	 *
	 * @param dateString string que representa um {@link Date}
	 * @return string da data formatada
	 */
	public static Date convertToDate(String dateString) {
		return convertToDate(dateString, SimpleDateFormat.FULL);
	}

	public static Date convertToDate(String dateString, int dateStyle) {
		if (dateString == null || dateString.isEmpty())
			return null;

		DateFormat dateFormat = DateFormat.getDateInstance(dateStyle, Locale.getDefault());
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String readStream(InputStream stream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
		StringBuilder stringBuilder = new StringBuilder();
		do {
			String line = bufferedReader.readLine();
			if (line == null || !line.isEmpty()) {
				stringBuilder.append(line);
			}
		} while (bufferedReader.ready());

		return stringBuilder.toString();
	}

	public static String readFileTextFromResources(String relativePathToFile) {
		String textFromFile = null;

		if (relativePathToFile == null) {
			return null;
		} else if (!relativePathToFile.startsWith("/")) {
			relativePathToFile = "/" + relativePathToFile;
		}

		InputStream resourceAsStream = null;
		try {
			resourceAsStream = Util.class.getResourceAsStream(relativePathToFile);
			textFromFile = Util.readStream(resourceAsStream);
		} catch (IOException e) {
			Log.e(TAG, "Erro de IO ao tentar ler arquivo.");
		} finally {
			if (resourceAsStream != null) {
				try {
					resourceAsStream.close();
				} catch (IOException ignored) {
				}
			}
		}

		return textFromFile;
	}
}
