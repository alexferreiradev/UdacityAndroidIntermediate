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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    void deleteTheDatabase() {
        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(MoviesContract.MovieEntry.TABLE_NAME);
        tableNameHashSet.add(MoviesContract.ImageEntry.TABLE_NAME);

        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new MoviesDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // Não tem nenhuma tabela
        assertTrue("Error: Your database was created without all tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + MoviesContract.MovieEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> movieColumnHashSet = new HashSet<String>();
        movieColumnHashSet.add(MoviesContract.MovieEntry._ID);
        movieColumnHashSet.add(MoviesContract.MovieEntry.COLUMN_IMDB_ID);
        movieColumnHashSet.add(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH);
        movieColumnHashSet.add(MoviesContract.MovieEntry.COLUMN_IMG_KEY);
        movieColumnHashSet.add(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        movieColumnHashSet.add(MoviesContract.MovieEntry.COLUMN_OVERVIEW);
        movieColumnHashSet.add(MoviesContract.MovieEntry.COLUMN_POPULARITY);
        movieColumnHashSet.add(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE);
        movieColumnHashSet.add(MoviesContract.MovieEntry.COLUMN_TITLE);
        movieColumnHashSet.add(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE);
        movieColumnHashSet.add(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT);

        final HashSet<String> imageColumnHashSet = new HashSet<String>();
        imageColumnHashSet.add(MoviesContract.ImageEntry._ID);
        imageColumnHashSet.add(MoviesContract.ImageEntry.COLUMN_ASPECT_RATIO);
        imageColumnHashSet.add(MoviesContract.ImageEntry.COLUMN_FILE_PATH);
        imageColumnHashSet.add(MoviesContract.ImageEntry.COLUMN_HEIGHT);
        imageColumnHashSet.add(MoviesContract.ImageEntry.COLUMN_WIDTH);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            movieColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        c = db.rawQuery("PRAGMA table_info(" + MoviesContract.ImageEntry.TABLE_NAME + ")",
                null);
        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            imageColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required movie entry columns",
                movieColumnHashSet.isEmpty());
        assertTrue("Error: The database doesn't contain all of the required image entry columns",
                imageColumnHashSet.isEmpty());
        db.close();
    }

    public void testImageTable() {
        SQLiteDatabase db = new MoviesDbHelper(this.mContext).getWritableDatabase();
        assertTrue(db.isOpen());
        long id = TestUtilities.insertImageValues(this.mContext);
        assertNotSame("Id = -1", id, -1);
        Cursor cursor = db.query(MoviesContract.ImageEntry.TABLE_NAME, null, null, null, null, null, null);
        assertNotNull("Cursor nulo", cursor);
        assertTrue("Cursor nao pode mover para first", cursor.moveToFirst());
        ContentValues fakeImageValues = TestUtilities.createFakeImageValues();
        TestUtilities.validateCursor("Os dados nao são os mesmo", cursor, fakeImageValues);
        assertFalse(cursor.moveToNext());
        cursor.close();
        assertTrue(cursor.isClosed());
        db.close();
        assertFalse(db.isOpen());
    }

    public void testMovieTable(){
        SQLiteDatabase db = new MoviesDbHelper(this.mContext).getWritableDatabase();
        assertTrue(db.isOpen());
        long imageId = TestUtilities.insertImageValues(this.mContext);
        long id = TestUtilities.insertMovieValues(this.mContext, imageId);
        assertNotSame("Id = -1", id, -1);
        Cursor cursor = db.query(MoviesContract.MovieEntry.TABLE_NAME, null, null, null, null, null, null);
        assertNotNull("Cursor nulo", cursor);
        assertTrue("Cursor nao pode mover para first", cursor.moveToFirst());
        ContentValues fakeexpectedValuesValues = TestUtilities.createFakeMovieValues(imageId);
        TestUtilities.validateCursor("Os dados nao são os mesmo", cursor, fakeexpectedValuesValues);
        assertFalse(cursor.moveToNext());
        cursor.close();
        assertTrue(cursor.isClosed());
        db.close();
        assertFalse(db.isOpen());
    }
}
