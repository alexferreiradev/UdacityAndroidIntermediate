package com.alex.baking.app.data.source.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressWarnings("WeakerAccess")
public class BakingSqlHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "baking.db";
	public static final int DB_VERSION = 3;

	static final String TAG = BakingSqlHelper.class.getSimpleName();

	public BakingSqlHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(BakingContract.RecipeEntry.createTableSql());
		db.execSQL(BakingContract.IngredientEntry.createTableSql());
		db.execSQL(BakingContract.StepEntry.createTableSql());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "Atualizando banco da versão: " + oldVersion + " para: " + newVersion);

		db.execSQL(BakingContract.RecipeEntry.dropTableSql());
		db.execSQL(BakingContract.IngredientEntry.dropTableSql());
		db.execSQL(BakingContract.StepEntry.dropTableSql());

		onCreate(db);
	}
}
