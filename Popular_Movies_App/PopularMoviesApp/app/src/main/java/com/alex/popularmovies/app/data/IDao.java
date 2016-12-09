package com.alex.popularmovies.app.data;

import java.util.List;

/**
 * Created by Alex on 09/12/2016.
 */

public interface IDao<T> {

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
    public List<T> search(String key, String value, String orderBy, int limit);

    /**
     * Retorna um objeto
     * @param key
     * @param value
     * @return
     */
    public T get(String key, String value);
}
