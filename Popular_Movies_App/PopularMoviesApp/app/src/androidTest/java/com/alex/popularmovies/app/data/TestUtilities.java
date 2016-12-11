package com.alex.popularmovies.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.alex.popularmovies.app.util.MoviesUtil;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/*
    Students: These are functions and some test data to make it easier to test your database and
    Content Provider.  Note that you'll want your WeatherContractSunshine class to exactly match the one
    in our solution to use these as-given.
 */
public class TestUtilities extends AndroidTestCase {
    static final String TEST_LOCATION = "99705";
    static final long TEST_DATE = 1419033600L;  // December 20th, 2014

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" +  valueCursor.getString(idx) +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    public static long insertImageValues(Context mContext) {
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createFakeImageValues();

        long rowId;
        rowId = db.insert(MoviesContract.ImageEntry.TABLE_NAME, null, testValues);
        assertTrue("Error: Failure to insert Image Values", rowId != -1);
        return rowId;
    }

    public static long insertMovieValues(Context mContext, long imageId) {
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long rowId;
        ContentValues fakeMovieValues = createFakeMovieValues(imageId);
        rowId = db.insert(MoviesContract.MovieEntry.TABLE_NAME, null, fakeMovieValues);
        assertTrue("Error: Failure to insert Movie Values", rowId != -1);
        return rowId;
    }

    public static ContentValues createFakeMovieValues(long imageId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MovieEntry.COLUMN_IMG_KEY, imageId);
        contentValues.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH, "teste.png");
        contentValues.put(MoviesContract.MovieEntry.COLUMN_IMDB_ID, 123);
        contentValues.put(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE, "title original");
        contentValues.put(MoviesContract.MovieEntry.COLUMN_TITLE, "title");
        contentValues.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW, "over");
        contentValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, MoviesUtil.formatDate(new Date()));
        contentValues.put(MoviesContract.MovieEntry.COLUMN_POPULARITY, 2.5);
        contentValues.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, 2.4);
        contentValues.put(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT, 2);

        return contentValues;
    }

    public static ContentValues createFakeImageValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.ImageEntry.COLUMN_ASPECT_RATIO, 1.1);
        contentValues.put(MoviesContract.ImageEntry.COLUMN_FILE_PATH, "teste.png");
        contentValues.put(MoviesContract.ImageEntry.COLUMN_HEIGHT, 3.4);
        contentValues.put(MoviesContract.ImageEntry.COLUMN_WIDTH, 3.4);

        return contentValues;
    }

}
