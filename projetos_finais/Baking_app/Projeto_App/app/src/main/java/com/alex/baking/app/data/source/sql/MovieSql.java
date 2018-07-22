package com.alex.baking.app.data.source.sql;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.alex.baking.app.data.model.Movie;
import com.alex.baking.app.data.source.exception.SourceException;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.queryspec.sql.SqlQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.alex.baking.app.data.source.sql.PMContract.MovieEntry.*;

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
			ContentValues contentValues = wrapperContent(model);
			contentValues.remove(PMContract.MovieEntry._ID);
			Uri movieUri = mResolver.insert(PMContract.MovieEntry.CONTENT_URI, contentValues);
			assert movieUri != null;
			Long movieId = ContentUris.parseId(movieUri);
			Log.i(TAG, "Filme criado no banco com ID: " + movieId);
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
			Uri uri = PMContract.MovieEntry.buildMovieUri(id);
			String[] selectionArgs = {String.valueOf(id)};
			Cursor movieCursor = mResolver.query(uri, null, PMContract.MovieEntry.COLUMN_ID_FROM_API + "=?", selectionArgs, null);
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
			if (model == null || model.getId() == null) {
				throw new SourceException("filme ou id nulo. Filme invalido.");
			}

			Uri uri = PMContract.MovieEntry.buildMovieUri(model.getId());
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
		try {
			if (model == null || model.getId() == null) {
				throw new SourceException("filme ou id nulo. Filme invalido.");
			}

			Uri uri = PMContract.MovieEntry.buildMovieUri(model.getId());
			int rowsUpdated = mResolver.delete(uri, null, null);
			if (rowsUpdated < 0) {
				return null;
			}
			model.setId(null);

			return model;
		} catch (Exception e) {
			Log.e(TAG, "Erro desconhecido: " + e.getMessage());
			throw new SourceException("Erro desconhecido: " + e.getMessage(), e);
		}
	}

	@Override
	protected ContentValues wrapperContent(Movie data) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(PMContract.MovieEntry._ID, data.getId());
		contentValues.put(PMContract.MovieEntry.COLUMN_ID_FROM_API, data.getIdFromApi());
		contentValues.put(PMContract.MovieEntry.COLUMN_IS_FAVORITE, data.isFavorite());
		contentValues.put(PMContract.MovieEntry.COLUMN_POSTER_PATH, data.getPosterPath());
		contentValues.put(PMContract.MovieEntry.COLUMN_RATING, data.getRating());
		contentValues.put(PMContract.MovieEntry.COLUMN_SYNOPSYS, data.getSynopsis());
		contentValues.put(PMContract.MovieEntry.COLUMN_THUMBNAIL_PATH, data.getThumbnailPath());
		contentValues.put(PMContract.MovieEntry.COLUMN_TITLE, data.getTitle());
		Date releaseDate = data.getReleaseDate();
		contentValues.put(PMContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDate.getTime());

		return contentValues;
	}

	@Override
	protected Movie wrapperModel(ContentValues values) {
		Movie movie = new Movie();
		assert values != null;
		movie.setIdFromApi(values.getAsLong(COLUMN_ID_FROM_API));
		movie.setPosterPath(values.getAsString(COLUMN_POSTER_PATH));
		movie.setRating(values.getAsDouble(COLUMN_RATING));
		Long dateLong = values.getAsLong(COLUMN_RELEASE_DATE);
		Date releaseDate = new Date(dateLong);
		movie.setReleaseDate(releaseDate);
		movie.setSynopsis(values.getAsString(COLUMN_SYNOPSYS));
		movie.setThumbnailPath(values.getAsString(COLUMN_THUMBNAIL_PATH));
		movie.setTitle(values.getAsString(COLUMN_TITLE));
		movie.setId(values.getAsLong(_ID));

		return movie;
	}

	@Override
	protected Movie createModelFromCursor(Cursor cursor) {
		Movie movie = new Movie();
		assert cursor != null;

		int columnIndex = cursor.getColumnIndex(PMContract.MovieEntry.COLUMN_ID_FROM_API);
		movie.setIdFromApi(cursor.getLong(columnIndex));
		columnIndex = cursor.getColumnIndex(COLUMN_POSTER_PATH);
		movie.setPosterPath(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(COLUMN_RATING);
		movie.setRating(cursor.getDouble(columnIndex));
		columnIndex = cursor.getColumnIndex(COLUMN_RELEASE_DATE);
		Long dateLong = cursor.getLong(columnIndex);
		Date releaseDate = new Date(dateLong);
		movie.setReleaseDate(releaseDate);
		columnIndex = cursor.getColumnIndex(COLUMN_SYNOPSYS);
		movie.setSynopsis(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(COLUMN_THUMBNAIL_PATH);
		movie.setThumbnailPath(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(COLUMN_TITLE);
		movie.setTitle(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(_ID);
		movie.setId(cursor.getLong(columnIndex));

		return movie;
	}
}
