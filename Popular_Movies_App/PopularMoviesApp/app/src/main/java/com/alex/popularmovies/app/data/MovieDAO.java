package com.alex.popularmovies.app.data;

import android.content.Context;
import android.database.Cursor;

import com.alex.popularmovies.app.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 09/12/2016.
 */

public class MovieDAO implements IDao<Movie> {

    private final DefaultDAO defaultDAO;

    public MovieDAO(Context context) {
        defaultDAO = new DefaultDAO(context, MoviesContract.MovieEntry.TABLE_NAME);
    }

    @Override
    public Movie saveOrUpdate(Movie obj) {
        return (Movie) defaultDAO.saveOrUpdate(obj);
    }

    @Override
    public Movie delete(Movie obj) {
        return (Movie) defaultDAO.delete(obj);
    }

    @Override
    public List<Movie> search(String key, String value, String orderBy, String limit) {
        Cursor cursor = defaultDAO.search(key, value, orderBy, limit);
        if (cursor == null || !cursor.moveToFirst())
            return null;
        List<Movie> movies = new ArrayList<Movie>();
        do {
            Movie movie = new Movie(cursor);
            movies.add(movie);
        }while (cursor.moveToNext());
        cursor.close();
        return movies;
    }

    @Override
    public Movie get(String key, String value) {
        Cursor cursor = defaultDAO.get(key, value);
        if (cursor == null || !cursor.moveToFirst())
            return null;
        Movie movie = new Movie(cursor);
        cursor.close();
        return movie;
    }
}
