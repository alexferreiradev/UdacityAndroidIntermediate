package com.alex.popularmovies.app.data.repository;

import android.content.ContentValues;
import android.net.Uri;

import com.alex.popularmovies.app.data.model.BaseModel;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public interface DefaultRepository<ModelType extends BaseModel> {

    long insert(Uri uri, ContentValues values);

    int delete(Uri uri, String selection, String[] selectionArgs);

    int update(Uri uri, ContentValues values, String selection,
                               String[] selectionArgs);

    /**
     * Recupera lista de filmes. Poderá utilizar diferentes fontes de dados.
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return lista vazia caso ocorra erro ou lista com filmes carregados
     */
    List<ModelType> query(Uri uri, String[] projection, String selection,
                                          String[] selectionArgs, String sortOrder);

    ModelType get(Long dataId);
}
