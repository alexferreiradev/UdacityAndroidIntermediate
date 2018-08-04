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

package com.alex.baking.app.data.source.sql;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the movies database.
 */
public class BakingContract {

	public static final String CONTENT_AUTHORITY = "com.alex.baking.app";

	@SuppressWarnings("WeakerAccess")
	protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	public static final class RecipeEntry implements BaseColumns {

		public static final String TABLE_NAME = "recipe";

		public static final String COLUMN_ID_FROM_API = "id_from_api";
		public static final String COLUMN_NAME = "nome";
		public static final String COLUMN_SERVING = "serving";
		public static final String COLUMN_IMAGE = "image";

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
		public static final Uri CONTENT_URI_BY_ID = CONTENT_URI.buildUpon().appendPath("#").build();
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;


		public static Uri buildRecipeUri(long id) {
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}

		public static String createTableSql() {
			return "create table " + TABLE_NAME + "(" +
					_ID + " integer primary key autoincrement, " +
					COLUMN_ID_FROM_API + " integer unique, " +
					COLUMN_NAME + " text, " +
					COLUMN_SERVING + " text, " +
					COLUMN_IMAGE + " text" +
					")";
		}

		public static String dropTableSql() {
			return "drop table if exists " + TABLE_NAME;
		}
	}

	public static final class IngredientEntry implements BaseColumns {

		public static final String TABLE_NAME = "ingredient";

		public static final String COLUMN_QUANTITY = "quantity";
		public static final String COLUMN_MEASURE = "measure";
		public static final String COLUMN_INGREDIENT = "ingredient";
		public static final String COLUMN_FK_RECIPE = "recipe_id";

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
		public static final Uri CONTENT_URI_BY_ID = CONTENT_URI.buildUpon().appendPath("#").build();
		public static final Uri CONTENT_URI_BY_RECIPE_ID = RecipeEntry.CONTENT_URI.buildUpon().appendPath(RecipeEntry.TABLE_NAME).appendPath("#").appendPath(TABLE_NAME).build();
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;


		public static Uri buildIngredientUri(long id) {
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}

		public static String createTableSql() {
			return "create table " + TABLE_NAME + "(" +
					_ID + " integer primary key autoincrement, " +
					COLUMN_FK_RECIPE + " integer, " +
					COLUMN_QUANTITY + " real, " +
					COLUMN_MEASURE + " text, " +
					COLUMN_INGREDIENT + " text" +
					")";
		}

		public static String dropTableSql() {
			return "drop table if exists " + TABLE_NAME;
		}
	}

	public static final class StepEntry implements BaseColumns {

		public static final String TABLE_NAME = "step";

		public static final String COLUMN_ID_FROM_API = "id_from_api";
		public static final String COLUMN_SHORT_DESCRIPTION = "short_description";
		public static final String COLUMN_DESCRIPTION = "description";
		public static final String COLUMN_VIDEO_URL = "video_url";
		public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
		public static final String COLUMN_FK_RECIPE = "recipe_id";

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
		public static final Uri CONTENT_URI_BY_ID = CONTENT_URI.buildUpon().appendPath("#").build();
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;


		public static Uri buildMovieUri(long id) {
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}

		public static String createTableSql() {
			return "create table " + TABLE_NAME + "(" +
					_ID + " integer primary key autoincrement, " +
					COLUMN_ID_FROM_API + " integer unique, " +
					COLUMN_FK_RECIPE + " integer, " +
					COLUMN_SHORT_DESCRIPTION + " real, " +
					COLUMN_DESCRIPTION + " text, " +
					COLUMN_VIDEO_URL + " text, " +
					COLUMN_THUMBNAIL_URL + " text" +
					")";
		}

		public static String dropTableSql() {
			return "drop table if exists " + TABLE_NAME;
		}
	}

}
