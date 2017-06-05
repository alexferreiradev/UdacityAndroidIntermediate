package com.alex.popularmovies.app.data.repository;

import android.content.ContentValues;
import android.net.Uri;

import com.alex.popularmovies.app.data.model.BaseModel;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public interface DefaultRepository<ModelType extends BaseModel> {

    public abstract long insert(Uri uri, ContentValues values);
    public abstract int delete(Uri uri, String selection, String[] selectionArgs);
    public abstract int update(Uri uri, ContentValues values, String selection,
                               String[] selectionArgs);
    public abstract List<ModelType> query(Uri uri, String[] projection, String selection,
                                          String[] selectionArgs, String sortOrder);
    abstract ModelType get(Long dataId);
}
