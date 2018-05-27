package com.alex.popularmovies.app.data.model;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 09/12/2016.
 */

public class Movie extends BaseModel<Movie> {

	/**
	 * filme
	 * id
	 * title
	 * posterPath
	 * thumbnailPath
	 * synopsis
	 * rating
	 * releaseDate
	 * videos
	 * reviews
	 */
	private String title; // "original_title": "Lock, Stock and Two Smoking Barrels"
	private String posterPath; // "poster_path": "/qV7QaSf7f7yC2lc985zfyOJIAIN.jpg"
	private String thumbnailPath; // "backdrop_path": "/kzeR7BA0htJ7BeI6QEUX3PVp39s.jpg"
	private String synopsis; //  "overview": "A card sharp and his ..."
	private double rating; // "vote_count": 1377
	private Date releaseDate; // "release_date": "1998-03-05",
	private boolean favorite;
	private Bitmap poster; // Imagem de poster
	private Bitmap thumbnail; // Imagem de thumnail

	private List<Video> videos;
	private List<Review> reviews;

	public Movie() {
	}

	public Movie(String title, String posterPath, String thumbnailPath, String synopsis, double rating, Date releaseDate, boolean favorite, Bitmap poster, Bitmap thumbnail) {
		this.title = title;
		this.posterPath = posterPath;
		this.thumbnailPath = thumbnailPath;
		this.synopsis = synopsis;
		this.rating = rating;
		this.releaseDate = releaseDate;
		this.favorite = favorite;
		this.poster = poster;
		this.thumbnail = thumbnail;
	}

	public Movie(String title, String posterPath, String thumbnailPath, String synopsis, double rating, Date releaseDate, boolean favorite, Bitmap poster, Bitmap thumbnail, List<Video> videos, List<Review> reviews) {
		this(title, posterPath, thumbnailPath, synopsis, rating, releaseDate, favorite, poster, thumbnail);
		this.videos = videos;
		this.reviews = reviews;
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public Bitmap getPoster() {
		return poster;
	}

	public void setPoster(Bitmap poster) {
		this.poster = poster;
	}

	public Bitmap getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Bitmap thumbnail) {
		this.thumbnail = thumbnail;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Movie movie = (Movie) o;

		if (Double.compare(movie.rating, rating) != 0) return false;
		if (favorite != movie.favorite) return false;
		if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
		if (posterPath != null ? !posterPath.equals(movie.posterPath) : movie.posterPath != null) return false;
		if (thumbnailPath != null ? !thumbnailPath.equals(movie.thumbnailPath) : movie.thumbnailPath != null) return false;
		if (synopsis != null ? !synopsis.equals(movie.synopsis) : movie.synopsis != null) return false;
		if (releaseDate != null ? !releaseDate.equals(movie.releaseDate) : movie.releaseDate != null) return false;
		if (poster != null ? !poster.equals(movie.poster) : movie.poster != null) return false;
		return thumbnail != null ? thumbnail.equals(movie.thumbnail) : movie.thumbnail == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = title != null ? title.hashCode() : 0;
		result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
		result = 31 * result + (thumbnailPath != null ? thumbnailPath.hashCode() : 0);
		result = 31 * result + (synopsis != null ? synopsis.hashCode() : 0);
		temp = Double.doubleToLongBits(rating);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
		result = 31 * result + (favorite ? 1 : 0);
		result = 31 * result + (poster != null ? poster.hashCode() : 0);
		result = 31 * result + (thumbnail != null ? thumbnail.hashCode() : 0);
		return result;
	}

	@Override

	public String toString() {
		return "Movie{" +
				"id=" + id +
				", title='" + title + '\'' +
				", posterPath='" + posterPath + '\'' +
				", thumbnailPath='" + thumbnailPath + '\'' +
				", synopsis='" + synopsis + '\'' +
				", rating=" + rating +
				", releaseDate=" + releaseDate +
				", favorite=" + favorite +
				", poster=" + poster +
				", thumbnail=" + thumbnail +
				'}';
	}
}
