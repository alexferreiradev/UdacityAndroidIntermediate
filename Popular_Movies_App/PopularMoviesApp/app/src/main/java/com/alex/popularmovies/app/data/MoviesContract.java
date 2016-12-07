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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names for the movies database.
 */
public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.alex.popularmovies.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = MovieEntry.TABLE_NAME;
    public static final String PATH_IMAGE = ImageEntry.TABLE_NAME;

    public static final class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "movie";
//        public static final String COLUMN_ID = TABLE_NAME.concat(_ID);

        public static final String COLUMN_IMG_KEY = ImageEntry._ID;
        public static final String COLUMN_IMDB_ID = "imdb_id";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POPULARITY = "popularity";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
    public static final class ImageEntry implements BaseColumns {

        public static final String TABLE_NAME = "image";
//        public static final String COLUMN_ID = TABLE_NAME.concat(_ID);

        public static final String COLUMN_ASPECT_RATIO = "aspect_ratio";
        public static final String COLUMN_FILE_PATH = "file_path";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_WIDTH = "width";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_IMAGE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IMAGE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IMAGE;
        public static Uri buildImageUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }
}
