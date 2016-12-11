package com.alex.popularmovies.app.model;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Alex on 09/12/2016.
 */

public interface IModel<T> {

    public long getId();
    public void setId(long id);

    /**
     * Cria um Content Value para o Objeto T
     * @return
     */
    public ContentValues buildValues();

    /**
     * Cria um Objeto de acordo com valores extraidos de um bd.
     * @param values
     * @return
     */
    public T setValues(Cursor values);

    /**
     * Cria um objeto de acordo com valores extraidos de um {@link ContentValues}.
     * @param values
     * @return
     */
    public T setValues(ContentValues values);

}
