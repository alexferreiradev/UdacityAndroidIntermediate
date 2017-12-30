package com.alex.popularmovies.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alex on 11/12/2016.
 */

public class MoviesUtil {

    /**
     * Formata data para o padrão FULL
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null)
            return null;

        DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
        return format.format(date);
    }

    /**
     * Formata data para um padrão escolhido.
     * @param date
     * @param dateStyle
     * @return
     */
    public static String formatDate(Date date, int dateStyle) {
        if (date == null)
            return null;

        DateFormat format = SimpleDateFormat.getDateInstance(dateStyle, Locale.getDefault());
        return format.format(date);
    }

    public static Date convertToDate(String dateString){
        if (dateString == null || dateString.isEmpty())
            return null;

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
