package com.alex.baking.app.data.source.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.MeasureType;

public class IngredientSqlSource extends BaseSqlSource<Ingredient> {

	public IngredientSqlSource(Context context) {
		super(context);
	}

	@Override
	protected ContentValues wrapperContent(Ingredient data) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BakingContract.IngredientEntry._ID, data.getId());
		contentValues.put(BakingContract.IngredientEntry.COLUMN_INGREDIENT, data.getIngredient());
		contentValues.put(BakingContract.IngredientEntry.COLUMN_MEASURE, data.getMeasure().name());
		contentValues.put(BakingContract.IngredientEntry.COLUMN_QUANTITY, data.getQuantity());

		return contentValues;
	}

	@Override
	protected Ingredient wrapperModel(ContentValues values) {
		Ingredient recipe = new Ingredient();
		recipe.setId(values.getAsLong(BakingContract.IngredientEntry._ID));
		recipe.setIngredient(values.getAsString(BakingContract.IngredientEntry.COLUMN_INGREDIENT));
		String measureString = values.getAsString(BakingContract.IngredientEntry.COLUMN_MEASURE).toUpperCase();
		recipe.setMeasure(MeasureType.valueOf(measureString));
		recipe.setQuantity(values.getAsDouble(BakingContract.IngredientEntry.COLUMN_QUANTITY));

		return recipe;
	}

	@Override
	protected Ingredient createModelFromCursor(Cursor cursor) {
		Ingredient recipe = new Ingredient();
		int columnIndex = cursor.getColumnIndex(BakingContract.IngredientEntry._ID);
		recipe.setId(cursor.getLong(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.IngredientEntry.COLUMN_INGREDIENT);
		recipe.setIngredient(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.IngredientEntry.COLUMN_MEASURE);
		String measureString = cursor.getString(columnIndex).toUpperCase();
		recipe.setMeasure(MeasureType.valueOf(measureString));
		columnIndex = cursor.getColumnIndex(BakingContract.IngredientEntry.COLUMN_QUANTITY);
		recipe.setQuantity(cursor.getDouble(columnIndex));

		return recipe;
	}

	@Override
	protected Uri getContentUri() {
		return BakingContract.IngredientEntry.CONTENT_URI;
	}

	@Override
	protected Uri getContentUriByID() {
		return BakingContract.IngredientEntry.CONTENT_URI_BY_ID;
	}
}
