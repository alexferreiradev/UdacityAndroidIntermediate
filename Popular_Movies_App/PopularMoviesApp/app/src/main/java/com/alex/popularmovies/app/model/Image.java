package com.alex.popularmovies.app.model;

import android.content.ContentValues;

import java.util.HashMap;

/**
 * Created by Alex on 09/12/2016.
 */

public class Image implements IModel<Image> {

    private long id;
    private String filePath;
    private Double aspectRatio;
    private Double width;
    private Double height;

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
    public ContentValues buildValue(Image obj) {
        return null;
    }

    @Override
    public Image valueOf(HashMap<String, String> values) {
        return null;
    }
}
