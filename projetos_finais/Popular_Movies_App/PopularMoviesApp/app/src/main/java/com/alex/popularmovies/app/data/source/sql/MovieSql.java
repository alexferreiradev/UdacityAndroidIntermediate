package com.alex.popularmovies.app.data.source.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;

import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class MovieSql extends BaseSqlSource<Movie> {

    public MovieSql(Context context) {
        super(context);
    }

    @Override
    public Movie create(Movie model) throws SourceException {
        throw new RuntimeException("Metodo nao disponivel para esta versao");
    }

    @Override
    public Movie recover(Long id) throws SourceException {
        throw new RuntimeException("Metodo nao disponivel para esta versao");
    }

    @Override
    public List<Movie> recover(QuerySpecification specification) throws SourceException {
        throw new RuntimeException("Metodo nao disponivel para esta versao");
    }

    @Override
    public Movie update(Movie model) throws SourceException {
        throw new RuntimeException("Metodo nao disponivel para esta versao");
    }

    @Override
    public Movie delete(Movie model) throws SourceException {
        throw new RuntimeException("Metodo nao disponivel para esta versao");
    }

    @Override
    protected ContentValues wrapperContent(Movie data) {
        throw new RuntimeException("Metodo nao disponivel para esta versao");
    }

    @Override
    protected Movie wrapperModel(ContentValues values) {
        throw new RuntimeException("Metodo nao disponivel para esta versao");
    }

    @Override
    protected Movie createModelFromCursor(Cursor cursor) {
        throw new RuntimeException("Metodo nao disponivel para esta versao");
    }
}
