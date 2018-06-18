package com.alex.popularmovies.app.data.source.sql;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

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
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Implement this to handle requests to delete one or more rows.
		switch (sUriMacher.match(uri)) {
			case ALL_MOVIES:
//                getContext().getContentResolver().notifyChange(mUri, );
				break;
			default:
				throw new UnknownError("URI not known + " + uri);
		}

		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public String getType(Uri uri) {
		// TODO: Implement this to handle requests for the MIME type of the data
		// at the given URI.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO: Implement this to handle requests to insert a new row.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public boolean onCreate() {
		// TODO: Implement this to initialize your content provider on startup.
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		SQLiteDatabase readDb = mSqlHelper.getReadableDatabase();

		switch (sUriMacher.match(uri)) {
			case ALL_MOVIES:
				cursor = readDb.query(PMContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				readDb.close();
//				getContext().getContentResolver().notifyChange(uri, null); // TODO: 17/06/18 ver a necessidade disso
				break;
		}

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		// TODO: Implement this to handle requests to update one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public void setmSqlHelper(SQLiteOpenHelper mSqlHelper) {
		this.mSqlHelper = mSqlHelper;
	}
}
