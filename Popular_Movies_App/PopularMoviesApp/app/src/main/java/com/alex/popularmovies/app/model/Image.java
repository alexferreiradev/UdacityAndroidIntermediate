package com.alex.popularmovies.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.alex.popularmovies.app.data.MoviesContract;

/**
 * Created by Alex on 09/12/2016.
 */

public class Image implements IModel<Image> {

    private long id;
    private String filePath;
    private Double aspectRatio;
    private Double width;
    private Double height;

    public Image() {
    }

    public Image(Cursor cursor) {
        setValues(cursor);
    }

    public Image(ContentValues contentValues) {
        setValues(contentValues);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (id != image.id) return false;
        if (!filePath.equals(image.filePath)) return false;
        if (aspectRatio != null ? !aspectRatio.equals(image.aspectRatio) : image.aspectRatio != null)
            return false;
        if (width != null ? !width.equals(image.width) : image.width != null) return false;
        return height != null ? height.equals(image.height) : image.height == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + filePath.hashCode();
        result = 31 * result + (aspectRatio != null ? aspectRatio.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", aspectRatio=" + aspectRatio +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    public ContentValues buildValues() {

        /*
            Dados a serem add

            getId(), getAspectRatio(), getFilePath(), getHeight(), getWidth()
        */
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.ImageEntry._ID, id);
        contentValues.put(MoviesContract.ImageEntry.COLUMN_ASPECT_RATIO, aspectRatio);
        contentValues.put(MoviesContract.ImageEntry.COLUMN_FILE_PATH, filePath);
        contentValues.put(MoviesContract.ImageEntry.COLUMN_HEIGHT, height);
        contentValues.put(MoviesContract.ImageEntry.COLUMN_WIDTH, width);
        return contentValues;
    }

    @Override
    public Image setValues(Cursor cursor) {
        if (cursor == null)
            return null;

        int columnIndex = cursor.getColumnIndex(MoviesContract.ImageEntry._ID);
        if (columnIndex >= 0)
            id = cursor.getInt(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.ImageEntry.COLUMN_FILE_PATH);
        if (columnIndex >= 0)
            filePath = cursor.getString(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.ImageEntry.COLUMN_HEIGHT);
        if (columnIndex >= 0)
            height = cursor.getDouble(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.ImageEntry.COLUMN_WIDTH);
        if (columnIndex >= 0)
            width = cursor.getDouble(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.ImageEntry.COLUMN_ASPECT_RATIO);
        if (columnIndex >= 0)
            aspectRatio = cursor.getDouble(columnIndex);

        return this;
    }

    @Override
    public Image setValues(ContentValues values) {
        if (values.containsKey(MoviesContract.ImageEntry._ID))
            id = values.getAsLong(MoviesContract.ImageEntry._ID);
        if (values.containsKey(MoviesContract.ImageEntry.COLUMN_FILE_PATH))
            filePath = values.getAsString(MoviesContract.ImageEntry.COLUMN_FILE_PATH);
        if (values.containsKey(MoviesContract.ImageEntry.COLUMN_WIDTH))
            width = values.getAsDouble(MoviesContract.ImageEntry.COLUMN_WIDTH);
        if (values.containsKey(MoviesContract.ImageEntry.COLUMN_HEIGHT))
            height = values.getAsDouble(MoviesContract.ImageEntry.COLUMN_HEIGHT);
        if (values.containsKey(MoviesContract.ImageEntry.COLUMN_ASPECT_RATIO))
            aspectRatio = values.getAsDouble(MoviesContract.ImageEntry.COLUMN_ASPECT_RATIO);
        return this;
    }
}
