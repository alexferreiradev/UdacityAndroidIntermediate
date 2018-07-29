package com.alex.baking.app.data.source.sql;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import com.alex.baking.app.data.model.BaseModel;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.queryspec.sql.SqlQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

abstract class BaseSqlSource<ModelType extends BaseModel> implements DefaultSource<ModelType, SqlQuery> {

	protected ContentResolver mResolver;

	public BaseSqlSource(Context context) {
		mResolver = context.getContentResolver();
	}

	protected abstract ContentValues wrapperContent(ModelType data);

	protected abstract ModelType wrapperModel(ContentValues values);

	protected abstract ModelType createModelFromCursor(Cursor cursor);

	protected abstract Uri getContentUri();

	protected abstract Uri getContentUriByID();

	protected List<ModelType> createListModelFromCursor(Cursor cursor) {
		List<ModelType> list = new ArrayList<>();

		if (cursor == null || !cursor.moveToFirst()) {
			return list;
		}

		do {
			ModelType model = createModelFromCursor(cursor);
			list.add(model);
		} while (cursor.moveToNext());

		return list;
	}

	public void setmResolver(ContentResolver mResolver) {
		this.mResolver = mResolver;
	}

	@Override
	public ModelType create(ModelType model) {
		ContentValues contentValues = wrapperContent(model);
		contentValues.remove(BaseColumns._ID);
		Uri uriInsert = mResolver.insert(getContentUri(), contentValues);
		if (uriInsert != null) {
			Long id = ContentUris.parseId(uriInsert);
			model.setId(id);
		}

		return model;
	}

	@Override
	public ModelType recover(Long id) {
		ModelType modelFromCursor = null;

		String selection = BakingContract.IngredientEntry._ID + "=?";
		String[] selectionArgs = {String.valueOf(id)};
		Cursor cursor = mResolver.query(getContentUriByID(), null, selection, selectionArgs, null);
		if (cursor != null && cursor.moveToFirst()) {
			modelFromCursor = createModelFromCursor(cursor);
			cursor.close();
		}

		return modelFromCursor;
	}

	@Override
	public List<ModelType> recover(QuerySpecification<SqlQuery> specification) {
		List<ModelType> modelList = new ArrayList<>();

		SqlQuery sqlQuery = specification.getQuery();
		Cursor cursor = mResolver.query(sqlQuery.getUri(), sqlQuery.getProjection(), sqlQuery.getSelection(), sqlQuery.getSelectionArgs(), sqlQuery.getSort());
		if (cursor != null && cursor.moveToFirst()) {
			modelList = createListModelFromCursor(cursor);
			cursor.close();
		}

		return modelList;
	}

	@Override
	public ModelType update(ModelType model) {
		if (model == null || model.getId() == null) {
			return null;
		}

		String where = BakingContract.IngredientEntry._ID + "=?";
		String[] whereArgs = {String.valueOf(model.getId())};
		int rowsUpdated = mResolver.update(getContentUri(), wrapperContent(model), where, whereArgs);
		if (rowsUpdated <= 0) {
			throw new RuntimeException("Não foi atualizado nenhuma linha na tabela");
		}

		return model;
	}

	@Override
	public ModelType delete(ModelType model) {
		if (model == null || model.getId() == null) {
			return null;
		}

		String where = BakingContract.IngredientEntry._ID + "=?";
		String[] whereArgs = {String.valueOf(model.getId())};
		int rowsDeleted = mResolver.delete(getContentUri(), where, whereArgs);
		if (rowsDeleted <= 0) {
			throw new RuntimeException("Não foi possível localizar id: " + model.getId());
		}

		return model;
	}
}
