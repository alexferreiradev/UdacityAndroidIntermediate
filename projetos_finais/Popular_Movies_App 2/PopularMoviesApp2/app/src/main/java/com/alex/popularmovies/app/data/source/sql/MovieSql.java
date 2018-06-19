package com.alex.popularmovies.app.data.source.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;
import com.alex.popularmovies.app.data.source.queryspec.sql.SqlQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

public class MovieSql extends BaseSqlSource<Movie> {
	private static final String TAG = MovieSql.class.getSimpleName();

	public MovieSql(Context context) {
		super(context);
	}

	@Override
	public Movie create(Movie model) throws SourceException {
		try {
			Uri movieUri = mResolver.insert(PMContract.MovieEntry.CONTENT_URI, wrapperContent(model));
			String movieIdString = movieUri.getPath();
			Long movieId = Long.valueOf(movieIdString);
			model.setId(movieId);

			return model;
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: " + e.getMessage());
			throw new SourceException("Erro desconhecido: " + e.getMessage(), e);
		}
	}

	@Override
	public Movie recover(Long id) throws SourceException {
		try {
			Uri contentUri = PMContract.MovieEntry.CONTENT_URI;
			Uri uri = contentUri.buildUpon().appendPath(String.valueOf(id)).build();
			Cursor movieCursor = mResolver.query(uri, null, null, null, null);
			Movie movie = createModelFromCursor(movieCursor);

			return movie;
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: " + e.getMessage());
			throw new SourceException("Erro desconhecido: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Movie> recover(QuerySpecification specification) throws SourceException {
		List<Movie> movieList = new ArrayList<>();

		try {
			SqlQuery query = (SqlQuery) specification.getQuery();
			Cursor cursor = mResolver.query(query.getUri(), query.getProjection(), query.getSelection(), query.getSelectionArgs(), query.getSort());

			assert cursor != null;
			movieList = createListModelFromCursor(cursor);
			cursor.close();
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: " + e.getMessage());
			throw new SourceException("Erro desconhecido: " + e.getMessage(), e);
		}

		return movieList;
	}

	@Override
	public Movie update(Movie model) throws SourceException {
		try {
			Uri contentUri = PMContract.MovieEntry.CONTENT_URI;
			Uri uri = contentUri.buildUpon().appendPath(String.valueOf(model.getId())).build();
			int rowsUpdated = mResolver.update(uri, wrapperContent(model), null, null);

			if (rowsUpdated < 0) {
				return null;
			}

			return model;
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: " + e.getMessage());
			throw new SourceException("Erro desconhecido: " + e.getMessage(), e);
		}
	}

	@Override
	public Movie delete(Movie model) throws SourceException {
		throw new SourceException("Metodo nao disponivel para esta versao");
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
