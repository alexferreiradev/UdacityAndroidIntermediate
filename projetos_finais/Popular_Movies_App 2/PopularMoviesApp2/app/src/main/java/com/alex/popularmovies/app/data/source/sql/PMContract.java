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

package com.alex.popularmovies.app.data.source.sql;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the movies database.
 */
public class PMContract {

	public static final String CONTENT_AUTHORITY = "com.alex.popularmovies.app";

	protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	public static final class MovieEntry implements BaseColumns {

		public static final String TABLE_NAME = "movie";

		public static final String COLUMN_TITLE = "title";
		public static final String COLUMN_POSTER_PATH = "poster_path";
		public static final String COLUMN_THUMBNAIL_PATH = "thumbnail_path";
		public static final String COLUMN_SYNOPSYS = "synopsys";
		public static final String COLUMN_RATING = "rating";
		public static final String COLUMN_RELEASE_DATE = "release_date";
		public static final String COLUMN_IS_FAVORITE = "is_favorite";

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
		public static final Uri CONTENT_URI_BY_ID = CONTENT_URI.buildUpon().appendPath("#").build();
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;


		public static Uri buildMovieUri(long id) {
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}

		public static String createTableSql() {
			return "create table " + TABLE_NAME + "(" +
					_ID + " integer auto increment primary key, " +
					COLUMN_TITLE + " text, " +
					COLUMN_POSTER_PATH + " text, " +
					COLUMN_THUMBNAIL_PATH + " text, " +
					COLUMN_SYNOPSYS + " text, " +
					COLUMN_RATING + " real, " +
					COLUMN_RELEASE_DATE + " date, " + // TODO: 17/06/18 ver tipo na doc ofcial
					COLUMN_IS_FAVORITE + " bool" +
					")";
		}

		public static String dropTableSql() {
			return "drop table if exists " + TABLE_NAME;
		}
	}

}
