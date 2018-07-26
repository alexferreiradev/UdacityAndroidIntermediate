package com.alex.baking.app.data.source.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BakingSqlHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "baking.db";
	public static final int DB_VERSION = 1;

	static final String TAG = BakingSqlHelper.class.getSimpleName();

	public BakingSqlHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSql = BakingContract.RecipeEntry.createTableSql();
		db.execSQL(createSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String dropSql = BakingContract.RecipeEntry.dropTableSql();

		db.execSQL(dropSql);
		Log.i(TAG, "Atualizando banco da versão: " + oldVersion + " para: " + newVersion);

		onCreate(db);
	}
}
