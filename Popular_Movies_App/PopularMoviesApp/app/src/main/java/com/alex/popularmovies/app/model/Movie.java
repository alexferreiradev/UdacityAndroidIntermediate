package com.alex.popularmovies.app.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.alex.popularmovies.app.data.MoviesContract;
import com.alex.popularmovies.app.util.MoviesUtil;

import java.util.Date;

/**
 * Created by Alex on 09/12/2016.
 */

public class Movie implements IModel<Movie> {

    private long id;
    private String title;
    private String originalTitle;
    private String overview;
    private String backdropPath;
    private long imbdId;
    private double voteCount;
    private double voteAverage;
    private double popularity;
    private Date releaseDate;
    private Image image;

    public Movie() {
        id = -1;
        imbdId = -1;
    }

    public Movie(Cursor cursor) {
        setValues(cursor);
    }

    public Movie(ContentValues values) {
        setValues(values);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public long getImbdId() {
        return imbdId;
    }

    public void setImbdId(long imbdId) {
        this.imbdId = imbdId;
    }

    public double getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(double voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (imbdId != movie.imbdId) return false;
        if (Double.compare(movie.voteCount, voteCount) != 0) return false;
        if (Double.compare(movie.voteAverage, voteAverage) != 0) return false;
        if (Double.compare(movie.popularity, popularity) != 0) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (!originalTitle.equals(movie.originalTitle)) return false;
        if (!overview.equals(movie.overview)) return false;
        if (backdropPath != null ? !backdropPath.equals(movie.backdropPath) : movie.backdropPath != null)
            return false;
        if (releaseDate != null ? !releaseDate.equals(movie.releaseDate) : movie.releaseDate != null)
            return false;
        return image != null ? image.equals(movie.image) : movie.image == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + originalTitle.hashCode();
        result = 31 * result + overview.hashCode();
        result = 31 * result + (backdropPath != null ? backdropPath.hashCode() : 0);
        result = 31 * result + (int) (imbdId ^ (imbdId >>> 32));
        temp = Double.doubleToLongBits(voteCount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(voteAverage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(popularity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", imbdId=" + imbdId +
                ", voteCount=" + voteCount +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
                ", releaseDate=" + releaseDate +
                ", image=" + image +
                '}';
    }

    @Override
    public ContentValues buildValues() {
        /*
            Dados a serem empacotados

            getTitle(), getImage(), getBackdropPath(), getImbdId(), getOverview()
            , getPopularity(), getReleaseDate()
            getVoteAverage(), getVoteCount()
        */

        ContentValues values = new ContentValues();
        values.put(MoviesContract.MovieEntry._ID, id);
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE, title);
        values.put(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE, originalTitle);
        if (image != null && image.getId() != -1)
            values.put(MoviesContract.MovieEntry.COLUMN_IMG_KEY, image.getId());
        values.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH, backdropPath);
        values.put(MoviesContract.MovieEntry.COLUMN_IMDB_ID, imbdId);
        values.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW, overview);
        values.put(MoviesContract.MovieEntry.COLUMN_POPULARITY, popularity);
        values.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, MoviesUtil.formatDate(releaseDate));
        values.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, voteAverage);
        values.put(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT, voteCount);

        return values;
    }

    @Override
    public Movie setValues(Cursor cursor) {
        if (cursor == null)
            return null;

        int columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry._ID);
        if (columnIndex >= 0)
            id = cursor.getLong(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE);
        if (columnIndex >= 0)
            title = cursor.getString(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        if (columnIndex >= 0)
            originalTitle = cursor.getString(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH);
        if (columnIndex >= 0)
            backdropPath = cursor.getString(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_IMDB_ID);
        if (columnIndex >= 0)
            imbdId = cursor.getLong(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_OVERVIEW);
        if (columnIndex >= 0)
            overview = cursor.getString(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POPULARITY);
        if (columnIndex >= 0)
            popularity = cursor.getDouble(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE);
        if (columnIndex >= 0)
            releaseDate = MoviesUtil.convertToDate(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE);
        if (columnIndex >= 0)
            voteAverage = cursor.getDouble(columnIndex);
        columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT);
        if (columnIndex >= 0)
            voteCount = cursor.getDouble(columnIndex);

        return this;
    }

    @Override
    public Movie setValues(ContentValues values) {
        if (values.containsKey(MoviesContract.MovieEntry._ID))
            id = values.getAsLong(MoviesContract.MovieEntry._ID);
        if (values.containsKey(MoviesContract.MovieEntry.COLUMN_TITLE))
            title = values.getAsString(MoviesContract.MovieEntry.COLUMN_TITLE);
        if (values.containsKey(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE))
            originalTitle = values.getAsString(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        if (values.containsKey(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH))
            backdropPath = values.getAsString(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH);
        if (values.containsKey(MoviesContract.MovieEntry.COLUMN_IMDB_ID))
            imbdId = values.getAsLong(MoviesContract.MovieEntry.COLUMN_IMDB_ID);
        if (values.containsKey(MoviesContract.MovieEntry.COLUMN_OVERVIEW))
            overview = values.getAsString(MoviesContract.MovieEntry.COLUMN_OVERVIEW);
        if (values.containsKey(MoviesContract.MovieEntry.COLUMN_POPULARITY))
            popularity = values.getAsDouble(MoviesContract.MovieEntry.COLUMN_POPULARITY);
        if (values.containsKey(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE))
            releaseDate = MoviesUtil.convertToDate(values.getAsString(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE));
        if (values.containsKey(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE))
            voteAverage = values.getAsDouble(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE);
        if (values.containsKey(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT))
            voteCount = values.getAsDouble(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT);

        return this;
    }
}
