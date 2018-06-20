package com.alex.popularmovies.app.data.source.sql;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

public class PMProvider extends ContentProvider {

	protected static final int ALL_MOVIES = 100;
	protected static final int MOVIE_BY_ID = 101;

	protected static final UriMatcher sUriMacher = buildMacher();
	private SQLiteOpenHelper mSqlHelper;

	private static UriMatcher buildMacher() {
		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		uriMatcher.addURI(PMContract.CONTENT_AUTHORITY, PMContract.MovieEntry.TABLE_NAME, ALL_MOVIES);
		uriMatcher.addURI(PMContract.CONTENT_AUTHORITY, PMContract.MovieEntry.TABLE_NAME + "/#", MOVIE_BY_ID);

		return uriMatcher;
	}

	@Override
	public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
		// Implement this to handle requests to delete one or more rows.
		SQLiteDatabase readDb = mSqlHelper.getWritableDatabase();

		switch (sUriMacher.match(uri)) {
			case MOVIE_BY_ID:
				long movieId = ContentUris.parseId(uri);
				selectionArgs = new String[]{String.valueOf(movieId)};
				int rowsUpdated = readDb.delete(PMContract.MovieEntry.TABLE_NAME, "_id = ?", selectionArgs);
				readDb.close();

//                getContext().getContentResolver().notifyChange(mUri, );
				return rowsUpdated;
			default:
				throw new UnknownError("URI not known + " + uri);
		}
	}

	@Override
	public String getType(@NonNull Uri uri) {
		return PMContract.MovieEntry.CONTENT_TYPE;
	}

	@Override
	public Uri insert(@NonNull Uri uri, ContentValues values) {
		SQLiteDatabase readDb = mSqlHelper.getWritableDatabase();

		switch (sUriMacher.match(uri)) {
			case ALL_MOVIES:
				long idMovie = readDb.insert(PMContract.MovieEntry.TABLE_NAME, null, values);
				if (idMovie > 0) {
					uri = ContentUris.withAppendedId(uri, idMovie);
				} else {
					uri = null;
				}

				readDb.close();
//				getContext().getContentResolver().notifyChange(uri, null); // TODO: 17/06/18 ver a necessidade disso
				break;
		}

		return uri;
	}

	@Override
	public boolean onCreate() {
		mSqlHelper = new MovieSqlHelper(getContext());

		return true;
	}

	@Override
	public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		SQLiteDatabase readDb = mSqlHelper.getReadableDatabase();

		switch (sUriMacher.match(uri)) {
			case ALL_MOVIES:
			case MOVIE_BY_ID:
				cursor = readDb.query(PMContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
//				getContext().getContentResolver().notifyChange(uri, null); // TODO: 17/06/18 ver a necessidade disso
				break;
		}

		return cursor;
	}

	@Override
	public int update(@NonNull Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		// Implement this to handle requests to delete one or more rows.
		SQLiteDatabase writerDb = mSqlHelper.getWritableDatabase();

		switch (sUriMacher.match(uri)) {
			case MOVIE_BY_ID:
				long movieId = ContentUris.parseId(uri);
				selectionArgs = new String[]{String.valueOf(movieId)};
				int rowsUpdated = writerDb.update(PMContract.MovieEntry.TABLE_NAME, values, "_id = ?", selectionArgs);
				writerDb.close();

//                getContext().getContentResolver().notifyChange(mUri, );
				return rowsUpdated;
			default:
				throw new UnknownError("URI not known + " + uri);
		}
	}

	public void setmSqlHelper(SQLiteOpenHelper mSqlHelper) {
		this.mSqlHelper = mSqlHelper;
	}
}
