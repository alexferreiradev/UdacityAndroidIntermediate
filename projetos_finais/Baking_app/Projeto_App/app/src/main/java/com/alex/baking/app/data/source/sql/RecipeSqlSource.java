package com.alex.baking.app.data.source.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.alex.baking.app.data.model.Recipe;

public class RecipeSqlSource extends BaseSqlSource<Recipe> {

	public RecipeSqlSource(Context context) {
		super(context);
	}

	@Override
	protected ContentValues wrapperContent(Recipe data) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BakingContract.RecipeEntry._ID, data.getId());
		contentValues.put(BakingContract.RecipeEntry.COLUMN_ID_FROM_API, data.getIdFromAPI());
		contentValues.put(BakingContract.RecipeEntry.COLUMN_IMAGE, data.getImage());
		contentValues.put(BakingContract.RecipeEntry.COLUMN_NAME, data.getNome());
		contentValues.put(BakingContract.RecipeEntry.COLUMN_SERVING, data.getServing());

		return contentValues;
	}

	@Override
	protected Recipe wrapperModel(ContentValues values) {
		Recipe recipe = new Recipe();
		recipe.setId(values.getAsLong(BakingContract.RecipeEntry._ID));
		recipe.setIdFromAPI(values.getAsString(BakingContract.RecipeEntry.COLUMN_ID_FROM_API));
		recipe.setImage(values.getAsString(BakingContract.RecipeEntry.COLUMN_IMAGE));
		recipe.setNome(values.getAsString(BakingContract.RecipeEntry.COLUMN_NAME));
		recipe.setServing(values.getAsString(BakingContract.RecipeEntry.COLUMN_SERVING));

		return recipe;
	}

	@Override
	protected Recipe createModelFromCursor(Cursor cursor) {
		Recipe recipe = new Recipe();
		int columnIndex = cursor.getColumnIndex(BakingContract.RecipeEntry._ID);
		recipe.setId(cursor.getLong(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_ID_FROM_API);
		recipe.setIdFromAPI(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_IMAGE);
		recipe.setImage(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_NAME);
		recipe.setNome(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_SERVING);
		recipe.setServing(cursor.getString(columnIndex));

		return recipe;
	}

	@Override
	protected Uri getContentUri() {
		return BakingContract.RecipeEntry.CONTENT_URI;
	}

	@Override
	protected Uri getContentUriByID() {
		return BakingContract.RecipeEntry.CONTENT_URI_BY_ID;
	}
}
