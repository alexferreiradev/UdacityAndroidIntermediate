package com.alex.baking.app.data.source.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.alex.baking.app.data.model.Step;

public class StepSqlSource extends BaseSqlSource<Step> {

	public StepSqlSource(Context context) {
		super(context);
	}

	@Override
	protected ContentValues wrapperContent(Step data) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BakingContract.StepEntry._ID, data.getId());
		contentValues.put(BakingContract.StepEntry.COLUMN_ID_FROM_API, data.getIdFromAPI());
		contentValues.put(BakingContract.StepEntry.COLUMN_DESCRIPTION, data.getDescription());
		contentValues.put(BakingContract.StepEntry.COLUMN_SHORT_DESCRIPTION, data.getShortDescription());
		contentValues.put(BakingContract.StepEntry.COLUMN_THUMBNAIL_URL, data.getThumbnailURL());
		contentValues.put(BakingContract.StepEntry.COLUMN_VIDEO_URL, data.getVideoURL());

		return contentValues;
	}

	@Override
	protected Step wrapperModel(ContentValues values) {
		Step recipe = new Step();
		recipe.setId(values.getAsLong(BakingContract.StepEntry._ID));
		recipe.setIdFromAPI(values.getAsString(BakingContract.StepEntry.COLUMN_ID_FROM_API));
		recipe.setDescription(values.getAsString(BakingContract.StepEntry.COLUMN_DESCRIPTION));
		recipe.setShortDescription(values.getAsString(BakingContract.StepEntry.COLUMN_SHORT_DESCRIPTION));
		recipe.setThumbnailURL(values.getAsString(BakingContract.StepEntry.COLUMN_THUMBNAIL_URL));
		recipe.setVideoURL(values.getAsString(BakingContract.StepEntry.COLUMN_VIDEO_URL));

		return recipe;
	}

	@Override
	protected Step createModelFromCursor(Cursor cursor) {
		Step recipe = new Step();
		int columnIndex = cursor.getColumnIndex(BakingContract.StepEntry._ID);
		recipe.setId(cursor.getLong(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.StepEntry.COLUMN_ID_FROM_API);
		recipe.setIdFromAPI(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.StepEntry.COLUMN_DESCRIPTION);
		recipe.setDescription(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.StepEntry.COLUMN_SHORT_DESCRIPTION);
		recipe.setShortDescription(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.StepEntry.COLUMN_THUMBNAIL_URL);
		recipe.setThumbnailURL(cursor.getString(columnIndex));
		columnIndex = cursor.getColumnIndex(BakingContract.StepEntry.COLUMN_VIDEO_URL);
		recipe.setVideoURL(cursor.getString(columnIndex));

		return recipe;
	}

	@Override
	protected Uri getContentUri() {
		return BakingContract.StepEntry.CONTENT_URI;
	}

	@Override
	protected Uri getContentUriByID() {
		return BakingContract.StepEntry.CONTENT_URI_BY_ID;
	}
}
