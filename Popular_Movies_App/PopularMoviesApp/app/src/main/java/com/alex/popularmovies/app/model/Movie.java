package com.alex.popularmovies.app.model;

import android.content.ContentValues;

import java.util.Date;

/**
 * Created by Alex on 09/12/2016.
 */

public class Movie extends BaseModel<Movie> {

    /**
     * filme
     id
     title
     imageId
     synopsis
     totalVotes
     popularity
     releaseDate
     */
    private String title; // "original_title": "Lock, Stock and Two Smoking Barrels"
    private String posterPath; // "poster_path": "/qV7QaSf7f7yC2lc985zfyOJIAIN.jpg"
    private String thumbnailPath; // "backdrop_path": "/kzeR7BA0htJ7BeI6QEUX3PVp39s.jpg"
    private String synopsis; //  "overview": "A card sharp and his ..."
    private int totalVotes; // "vote_count": 1377
    private Date releaseDate; // "release_date": "1998-03-05",
    private double popularity; // "popularity": 0.811565,

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (totalVotes != movie.totalVotes) return false;
        if (Double.compare(movie.popularity, popularity) != 0) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (posterPath != null ? !posterPath.equals(movie.posterPath) : movie.posterPath != null)
            return false;
        if (thumbnailPath != null ? !thumbnailPath.equals(movie.thumbnailPath) : movie.thumbnailPath != null)
            return false;
        if (synopsis != null ? !synopsis.equals(movie.synopsis) : movie.synopsis != null)
            return false;
        return releaseDate != null ? releaseDate.equals(movie.releaseDate) : movie.releaseDate == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = title != null ? title.hashCode() : 0;
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + (thumbnailPath != null ? thumbnailPath.hashCode() : 0);
        result = 31 * result + (synopsis != null ? synopsis.hashCode() : 0);
        result = 31 * result + totalVotes;
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        temp = Double.doubleToLongBits(popularity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", thumbnailPath='" + thumbnailPath + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", totalVotes='" + totalVotes + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", popularity=" + popularity +
                '}';
    }

}
