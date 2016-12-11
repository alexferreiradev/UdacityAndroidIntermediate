package com.alex.popularmovies.app.data;

import android.content.Context;
import android.database.Cursor;

import com.alex.popularmovies.app.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11/12/2016.
 */

public class ImageDAO implements IDao<Image> {

    private final DefaultDAO defaultDAO;

    public ImageDAO(Context context) {
        defaultDAO = new DefaultDAO(context, MoviesContract.ImageEntry.TABLE_NAME);
    }

    @Override
    public Image saveOrUpdate(Image obj) {
        return (Image) defaultDAO.saveOrUpdate(obj);
    }

    @Override
    public Image delete(Image obj) {
        return (Image) defaultDAO.delete(obj);
    }

    @Override
    public List<Image> search(String key, String value, String orderBy, String limit) {
        Cursor cursor = defaultDAO.search(key, value, orderBy, limit);
        if (cursor == null || !cursor.moveToFirst())
            return null;
        List<Image> images = new ArrayList<Image>();
        do {
            Image image = new Image(cursor);
            images.add(image);
        }while (cursor.moveToNext());
        cursor.close();
        defaultDAO.closeDB();
        return images;
    }

    @Override
    public Image get(String key, String value) {
        Cursor cursor = defaultDAO.get(key, value);
        if (cursor == null || !cursor.moveToFirst())
            return null;
        Image image = new Image(cursor);
        cursor.close();
        defaultDAO.closeDB();
        return image;
    }
}
