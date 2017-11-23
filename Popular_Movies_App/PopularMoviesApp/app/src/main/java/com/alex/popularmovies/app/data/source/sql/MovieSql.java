package com.alex.popularmovies.app.data.source.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.BaseQuerySpecification;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.GetAllMovies;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class MovieSql extends BaseSqlSource<Movie> {


    public MovieSql(Context context) {
        super(context);
    }

    @Override
    protected ContentValues wrapperContent(Movie data) {
        return null;
    }

    @Override
    protected Movie wrapperModel(ContentValues values) {
        return null;
    }

    @Override
    protected Movie createModelFromCursor(Cursor cursor) {
        return null;
    }

    @Override
    public Movie insert(Movie data) {
        Uri insert = mResolver.insert(PMContract.MovieEntry.CONTENT_URI, wrapperContent(data));
        String id = insert.getLastPathSegment();
        data.setId(Long.parseLong(id));
        return data;
    }

    @Override
    public void update(Movie data) {
        String where = PMContract.MovieEntry._ID + "=?";
        String[] whereArgs = {String.valueOf(data.getId())};
        int update = mResolver.update(PMContract.MovieEntry.CONTENT_URI, wrapperContent(data), where, whereArgs);
    }

    @Override
    public void delete(Movie data) {
        String where = PMContract.MovieEntry._ID + "=?";
        String[] whereArgs = {String.valueOf(data.getId())};
        mResolver.delete(PMContract.MovieEntry.CONTENT_URI, where, whereArgs);
    }

    @Override
    public List<Movie> query(BaseQuerySpecification specification) {

        String[] proj = null;
        String sel = null;
        String[] selArg = null;
        String sort = null;
        Cursor cursor = null;
        if (specification instanceof GetAllMovies) {
            cursor = mResolver.query(PMContract.MovieEntry.CONTENT_URI, proj, sel, selArg, sort);
        }

        return createListModelFromCursor(cursor);
    }

    @Override
    public List<Movie> list(String sortOrderType) throws SourceException {
        return null;
    }

    @Override
    public Movie get(Long id) throws SourceException {
        throw new SourceException("Este método não está implementado");
    }
}
