package com.alex.popularmovies.app.model;

import android.content.ContentValues;

import java.util.Date;
import java.util.HashMap;

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
    public ContentValues buildValue(Movie obj) {

        return null;
    }

    @Override
    public Movie valueOf(HashMap<String, String> values) {
        return null;
    }
}
