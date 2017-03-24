package com.alex.popularmovies.app.data;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.alex.popularmovies.app.dao.MoviesContract;
import com.alex.popularmovies.app.model.Movie;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 11/12/2016.
 */

public class TestMovieDAO extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();
    private MovieDAO movieDAO;

    void deleteTheDatabase() {
        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
        movieDAO = new MovieDAO(mContext);
    }

    public void testInsert() throws Throwable {
        long idImage = TestUtilities.insertImageValues(mContext);
        ContentValues movieValues = TestUtilities.createFakeMovieValues(idImage);
        Movie movie = new Movie(movieValues);

        Date releaseDate = new Date();
        movie.setReleaseDate(releaseDate);
        movie.setTitle("Teste de date e insert");
        Movie movie2 = movieDAO.saveOrUpdate(movie);
        assertNotSame("Não foi feito add de id para novo obj", movie2.getId(), -1);
        movie2 = movieDAO.get(MoviesContract.MovieEntry._ID, String.valueOf(movie2.getId()));
        assertNotSame("Não foi encontrado recem obj", movie2.getId(), -1);
    }

    public void testSearch() throws Exception {
        int times = 10;
        do{
            long idImage = TestUtilities.insertImageValues(mContext);
            TestUtilities.insertMovieValues(mContext, idImage);
            times --;
        }while (times > 0);

        List<Movie> movies = movieDAO.search(MoviesContract.MovieEntry.COLUMN_TITLE, "title", null, null);
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        assertEquals(movies.size(), 10);
    }

    public void testUpdate() throws Throwable {
        long idImage = TestUtilities.insertImageValues(mContext);
        long idMovie = TestUtilities.insertMovieValues(mContext, idImage);
        Movie movie = movieDAO.get(MoviesContract.MovieEntry._ID, String.valueOf(idMovie));
        movie.setTitle("Novo titulo");
        movieDAO.saveOrUpdate(movie);
        Movie movie2 = movieDAO.get(MoviesContract.MovieEntry._ID, String.valueOf(idMovie));
        assertNotSame("Titulos sao os mesmos", movie.getTitle(), movie2.getTitle());
    }

    public void testDelete() throws Exception {
        int times = 10;
        do{
            long idImage = TestUtilities.insertImageValues(mContext);
            TestUtilities.insertMovieValues(mContext, idImage);
            times --;
        }while (times > 0);

        List<Movie> movies = movieDAO.search(MoviesContract.MovieEntry.COLUMN_TITLE, "title", null, null);
        assertNotNull(movies);
        assertEquals(movies.size(), 10);
        Movie movie = movies.get(0);
        movieDAO.delete(movie);
        movies = movieDAO.search(MoviesContract.MovieEntry.COLUMN_TITLE, "title", null, null);
        assertNotNull(movies);
        assertNotSame(movies.size(), 10);
        assertEquals(movies.size(), 9);
    }
}
