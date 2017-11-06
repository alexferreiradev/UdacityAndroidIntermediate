package com.alex.popularmovies.app.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.cache.MovieCache;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.remote.RemoteMovie;
import com.alex.popularmovies.app.data.source.sql.MovieSql;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class MovieRepository extends BaseRepository<Movie> {


    public MovieRepository(Context context) {
        mCacheSource = new MovieCache(null);
        mLocalSource = new MovieSql(context);
        HttpURLConnection http = null;
        mRemoteSource = new RemoteMovie(http);
    }

    @Override
    public long insert(Uri uri, ContentValues values) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public List<Movie> query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        List<Movie> movies = new ArrayList<>();

        try {
            if (mCacheSource.isDirty()) {
                movies = mRemoteSource.list(null);
                mCacheSource.updateCache(movies);
                mCacheSource.setDirty(false);
            } else {
                movies = mCacheSource.list(null);
                // todo disparar testar se ocorreu atualizacao de dados no remoto
            }
        } catch (SourceException e) {
            Log.e("repo", e.getMessage());
        }

        return movies;
    }

    @Override
    public Movie get(Long dataId) {
        return null;
    }

    @Override
    protected void createCache(List<Movie> data) {
        mCacheSource = new MovieCache(data);
    }

    @Override
    protected void destroyCache(List<Movie> data) {
        mCacheSource = null;
    }

    @Override
    protected void updateCache(List<Movie> data) {
        mCacheSource.setDirty(true);
    }
}