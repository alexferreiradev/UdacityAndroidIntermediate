/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alex.popularmovies.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "movies.db";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MoviesContract.MovieEntry.TABLE_NAME + " (" +

                MoviesContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                MoviesContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_IMDB_ID + " INTEGER NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +

                " FOREIGN KEY (" + MoviesContract.MovieEntry.COLUMN_IMG_KEY + ") REFERENCES " +
                MoviesContract.ImageEntry.TABLE_NAME + " (" + MoviesContract.ImageEntry._ID + ") );";

        final String SQL_CREATE_IMAGE_TABLE = "CREATE TABLE " + MoviesContract.ImageEntry.TABLE_NAME + " (" +

                MoviesContract.ImageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                MoviesContract.ImageEntry.COLUMN_FILE_PATH + " TEXT NOT NULL, " +
                MoviesContract.ImageEntry.COLUMN_ASPECT_RATIO + " REAL NOT NULL, " +
                MoviesContract.ImageEntry.COLUMN_WIDTH + " REAL NOT NULL, " +
                MoviesContract.ImageEntry.COLUMN_HEIGHT + " REAL NOT NULL, " + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_IMAGE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.ImageEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
