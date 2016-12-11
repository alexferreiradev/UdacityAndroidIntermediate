package com.alex.popularmovies.app.data;

import android.database.Cursor;

/**
 * Created by Alex on 11/12/2016.
 */

public interface IDefaultDao<T> {

    /**
     * Salva ou atualiza um objeto
     * @param obj
     * @return
     */
    public T saveOrUpdate(T obj);

    /**
     * Remove um objeto
     * @param obj
     * @return
     */
    public T delete(T obj);

    /**
     * Procura por objetos que tenham chave e valor de acordo com parâmetros da busca
     * @param key
     * @param value
     * @param orderBy
     * @param limit
     * @return
     */
    public Cursor search(String key, String value, String orderBy, String limit);

    /**
     * Retorna um objeto
     * @param key
     * @param value
     * @return
     */
    public Cursor get(String key, String value);

    public void closeDB();
}
