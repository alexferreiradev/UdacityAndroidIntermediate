package com.alex.baking.app.data.source.sql;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

public class BakingProvider extends ContentProvider {

	protected static final int ALL_RECIPES = 100;
	protected static final int RECIPE_BY_ID = 101;
	protected static final int ALL_INGREDIENTS = 200;
	protected static final int INGREDIENT_BY_ID = 201;
	protected static final int INGREDIENT_BY_RECIPE_ID = 202;
	protected static final int ALL_STEPS = 300;
	protected static final int STEP_BY_ID = 301;

	protected static final UriMatcher sUriMacher = buildMacher();
	private SQLiteOpenHelper mSqlHelper;

	private static UriMatcher buildMacher() {
		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		uriMatcher.addURI(BakingContract.CONTENT_AUTHORITY, BakingContract.RecipeEntry.CONTENT_URI.getPath(), ALL_RECIPES);
		uriMatcher.addURI(BakingContract.CONTENT_AUTHORITY, BakingContract.IngredientEntry.CONTENT_URI.getPath(), ALL_INGREDIENTS);
		uriMatcher.addURI(BakingContract.CONTENT_AUTHORITY, BakingContract.StepEntry.CONTENT_URI.getPath(), ALL_STEPS);
		uriMatcher.addURI(BakingContract.CONTENT_AUTHORITY, BakingContract.RecipeEntry.CONTENT_URI_BY_ID.getPath(), RECIPE_BY_ID);
		uriMatcher.addURI(BakingContract.CONTENT_AUTHORITY, BakingContract.IngredientEntry.CONTENT_URI_BY_ID.getPath(), INGREDIENT_BY_ID);
		uriMatcher.addURI(BakingContract.CONTENT_AUTHORITY, BakingContract.IngredientEntry.CONTENT_URI_BY_RECIPE_ID.getPath(), INGREDIENT_BY_RECIPE_ID);
		uriMatcher.addURI(BakingContract.CONTENT_AUTHORITY, BakingContract.StepEntry.CONTENT_URI_BY_ID.getPath(), STEP_BY_ID);

		return uriMatcher;
	}

	@Override
	public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
		// Implement this to handle requests to delete one or more rows.
//		SQLiteDatabase readDb = mSqlHelper.getWritableDatabase();
//
//		switch (sUriMacher.match(uri)) {
//			case RECIPE_BY_ID:
//				long movieId = ContentUris.parseId(uri);
//				selectionArgs = new String[]{String.valueOf(movieId)};
//				int rowsUpdated = readDb.delete(BakingContract.RecipeEntry.TABLE_NAME, "_id = ?", selectionArgs);
//				readDb.close();
//
////                getContext().getContentResolver().notifyChange(mUri, );
//				return rowsUpdated;
//			default:
//				throw new UnknownError("URI not known + " + uri);
//		}
		throw new RuntimeException("Não implementado");
	}

	@Override
	public String getType(@NonNull Uri uri) {
		switch (sUriMacher.match(uri)) {
			case ALL_RECIPES:
				return BakingContract.RecipeEntry.CONTENT_TYPE;
			case ALL_INGREDIENTS:
				return BakingContract.IngredientEntry.CONTENT_TYPE;
			case ALL_STEPS:
				return BakingContract.StepEntry.CONTENT_TYPE;
		}

		return BakingContract.RecipeEntry.CONTENT_TYPE;
	}

	@Override
	public Uri insert(@NonNull Uri uri, ContentValues values) {
		SQLiteDatabase readDb = mSqlHelper.getWritableDatabase();
		String tableName = "";

		switch (sUriMacher.match(uri)) {
			case ALL_INGREDIENTS:
				tableName = BakingContract.IngredientEntry.TABLE_NAME;
				break;
			case ALL_STEPS:
				tableName = BakingContract.StepEntry.TABLE_NAME;
				break;
			case ALL_RECIPES:
				tableName = BakingContract.RecipeEntry.TABLE_NAME;
				break;
		}

		long id = readDb.insert(tableName, null, values);
		if (id > 0) {
			uri = ContentUris.withAppendedId(uri, id);
		} else {
			uri = null;
		}
		readDb.close();

		return uri;
	}

	@Override
	public boolean onCreate() {
		mSqlHelper = new BakingSqlHelper(getContext());

		return true;
	}

	@Override
	public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Cursor cursor;
		SQLiteDatabase readDb = mSqlHelper.getReadableDatabase();
		String tableName = "";
		String limitParam = uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT);

		switch (sUriMacher.match(uri)) {
			case RECIPE_BY_ID:
			case ALL_RECIPES:
				tableName = BakingContract.RecipeEntry.TABLE_NAME;
				break;
			case INGREDIENT_BY_RECIPE_ID:
			case INGREDIENT_BY_ID:
			case ALL_INGREDIENTS:
				tableName = BakingContract.IngredientEntry.TABLE_NAME;
				break;
		}

		cursor = readDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, limitParam);

		return cursor;
	}

	@Override
	public int update(@NonNull Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		throw new RuntimeException("Não implementado");
	}

	public void setmSqlHelper(SQLiteOpenHelper mSqlHelper) {
		this.mSqlHelper = mSqlHelper;
	}
}
