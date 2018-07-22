package com.alex.baking.app.data.model;

/**
 * Created by alex on 27/05/18.
 */

public class Review extends BaseModel<Review> {

	private String idFromAPI;
	private String author;
	private String content;
	private String url;

	public String getIdFromAPI() {
		return idFromAPI;
	}

	public void setIdFromAPI(String idFromAPI) {
		this.idFromAPI = idFromAPI;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Review review = (Review) o;

		if (idFromAPI != null ? !idFromAPI.equals(review.idFromAPI) : review.idFromAPI != null) return false;
		if (author != null ? !author.equals(review.author) : review.author != null) return false;
		if (content != null ? !content.equals(review.content) : review.content != null) return false;
		return url != null ? url.equals(review.url) : review.url == null;
	}

	@Override
	public int hashCode() {
		int result = idFromAPI != null ? idFromAPI.hashCode() : 0;
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (content != null ? content.hashCode() : 0);
		result = 31 * result + (url != null ? url.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Review{" +
				"idFromAPI='" + idFromAPI + '\'' +
				", author='" + author + '\'' +
				", content='" + content + '\'' +
				", id=" + id +
				", url='" + url + '\'' +
				'}';
	}
}
