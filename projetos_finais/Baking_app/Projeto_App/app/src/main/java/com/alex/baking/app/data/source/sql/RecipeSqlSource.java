package com.alex.baking.app.data.source.sql;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.queryspec.sql.SqlQuery;

import java.util.ArrayList;
import java.util.List;

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
	public Recipe create(Recipe model) {
		ContentValues contentValues = wrapperContent(model);
		contentValues.remove(BakingContract.RecipeEntry._ID);
		Uri uriInsert = mResolver.insert(BakingContract.RecipeEntry.CONTENT_URI, contentValues);
		if (uriInsert != null) {
			Long id = ContentUris.parseId(uriInsert);
			model.setId(id);
		}

		return model;
	}

	@Override
	public Recipe recover(Long id) {
		Recipe modelFromCursor = null;

		String selection = BakingContract.RecipeEntry._ID + "=?";
		String[] selectionArgs = {String.valueOf(id)};
		Cursor cursor = mResolver.query(BakingContract.RecipeEntry.CONTENT_URI_BY_ID, null, selection, selectionArgs, null); // TODO: 26/07/18 - Não comitar, dont push this
		if (cursor != null && cursor.moveToFirst()) {
			modelFromCursor = createModelFromCursor(cursor);
			cursor.close();
		}

		return modelFromCursor;
	}

	@Override
	public List<Recipe> recover(QuerySpecification<SqlQuery> specification) {
		List<Recipe> recipeList = new ArrayList<>();

		SqlQuery sqlQuery = specification.getQuery();
		Cursor cursor = mResolver.query(sqlQuery.getUri(), sqlQuery.getProjection(), sqlQuery.getSelection(), sqlQuery.getSelectionArgs(), sqlQuery.getSort());
		if (cursor != null && cursor.moveToFirst()) {
			recipeList = createListModelFromCursor(cursor);
			cursor.close();
		}

		return recipeList;
	}

	@Override
	public Recipe update(Recipe model) {
		if (model == null || model.getId() == null) {
			return null;
		}

		String where = BakingContract.RecipeEntry._ID + "=?";
		String[] whereArgs = {String.valueOf(model.getId())};
		int rowsUpdated = mResolver.update(BakingContract.RecipeEntry.CONTENT_URI, wrapperContent(model), where, whereArgs);
		if (rowsUpdated <= 0) {
			throw new RuntimeException("Não foi atualizado nenhuma linha na tabela de recipe");
		}

		return model;
	}

	@Override
	public Recipe delete(Recipe model) {
		if (model == null || model.getId() == null) {
			return null;
		}

		String where = BakingContract.RecipeEntry._ID + "=?";
		String[] whereArgs = {String.valueOf(model.getId())};
		int rowsDeleted = mResolver.delete(BakingContract.RecipeEntry.CONTENT_URI, where, whereArgs);
		if (rowsDeleted <= 0) {
			throw new RuntimeException("Não foi possível localizar id do recipe: " + model.getId());
		}

		return model;
	}
}
