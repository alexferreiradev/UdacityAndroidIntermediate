package com.alex.popularmovies.app.model;

import android.content.ContentValues;

import java.util.HashMap;

/**
 * Created by Alex on 09/12/2016.
 */

public interface IModel<T> {

    /**
     * Cria um Content Value para o Objeto T
     * @return
     */
    public ContentValues buildValue(T obj);

    /**
     * Cria um Objeto de acordo com valores extraidos de um bd.
     * @param values
     * @return
     */
    public T valueOf(HashMap<String, String> values);
}
