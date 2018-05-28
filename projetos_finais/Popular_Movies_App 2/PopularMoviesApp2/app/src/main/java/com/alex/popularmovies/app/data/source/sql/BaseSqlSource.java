package com.alex.popularmovies.app.data.source.sql;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.source.DefaultSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 02/04/2017.
 */

abstract class BaseSqlSource<ModelType extends BaseModel> implements DefaultSource<ModelType> {

	protected ContentResolver mResolver;

	public BaseSqlSource(Context context) {
		mResolver = context.getContentResolver();
	}

	protected abstract ContentValues wrapperContent(ModelType data);

	protected abstract ModelType wrapperModel(ContentValues values);

	protected abstract ModelType createModelFromCursor(Cursor cursor);

	protected List<ModelType> createListModelFromCursor(Cursor cursor) {
		List<ModelType> list = new ArrayList<ModelType>();

		if (cursor == null || !cursor.moveToFirst())
			return list;

		do {
			ModelType model = createModelFromCursor(cursor);
			list.add(model);
		} while (cursor.moveToNext());

		return list;
	}
}
