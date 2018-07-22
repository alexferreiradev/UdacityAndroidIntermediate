package com.alex.baking.app.data.source.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieSqlHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "movie.db";
	public static final int DB_VERSION = 3;
	static final String TAG = MovieSqlHelper.class.getSimpleName();

	public MovieSqlHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSql = PMContract.MovieEntry.createTableSql();
		db.execSQL(createSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String dropSql = PMContract.MovieEntry.dropTableSql();

		db.execSQL(dropSql);
		Log.i(TAG, "Atualizando banco da versão: " + oldVersion + " para: " + newVersion);

		onCreate(db);
	}
}
