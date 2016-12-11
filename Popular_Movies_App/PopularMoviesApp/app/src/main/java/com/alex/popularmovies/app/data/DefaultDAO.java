package com.alex.popularmovies.app.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alex.popularmovies.app.model.IModel;

/**
 * Created by Alex on 11/12/2016.
 */

public class DefaultDAO implements IDefaultDao<IModel> {
    private Context mContext;
    private MoviesDbHelper moviesDbHelper;
    private String tableName;

    public DefaultDAO(Context context, String tableName) {
        this.mContext = context;
        moviesDbHelper = new MoviesDbHelper(mContext);
        this.tableName = tableName;
    }

    @Override
    public IModel saveOrUpdate(IModel obj) {
        SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        int rowsUpdated = -1;
        if (obj.getId() > 0) {
            rowsUpdated = db.update(tableName, obj.buildValues(), null, null);
        } else {
            long rowId = db.insert(tableName, null, obj.buildValues());
            obj.setId(rowId);
//            if (rowId < 0)
//                return obj;// // TODO: 11/12/2016 retornar exception
        }

        db.close();
        return obj;
    }

    @Override
    public IModel delete(IModel obj) {
        SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        String[] whereArgs = new String[1];
        whereArgs[0] = String.valueOf(obj.getId());
        String where = MoviesContract.MovieEntry._ID+"=?";
        int rowsDeleted = db.delete(tableName, where, whereArgs );
        if (rowsDeleted < 0)
            return null; // TODO retornar exception

        db.close();
        return obj;
    }

    @Override
    public Cursor search(String key, String value, String orderBy, String limit) {
        SQLiteDatabase db = moviesDbHelper.getWritableDatabase();
        Cursor cursor = db.query(tableName, null, null, null, null, null, orderBy, limit);
        return cursor;
    }

    @Override
    public Cursor get(String key, String value) {
        return search(key, value, null, "1");
    }

    @Override
    public void closeDB() {
        moviesDbHelper.close();
    }
}
