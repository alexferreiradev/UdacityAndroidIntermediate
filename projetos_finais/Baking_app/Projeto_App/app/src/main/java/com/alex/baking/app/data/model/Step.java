package com.alex.baking.app.data.model;

public class Step extends BaseModel {

	private String idFromAPI;
	private String shortDescription;
	private String description;
	private String videoURL;
	private String thumbnailURL;

	public String getIdFromAPI() {
		return idFromAPI;
	}

	public void setIdFromAPI(String idFromAPI) {
		this.idFromAPI = idFromAPI;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public String getThumbnailURL() {
		return thumbnailURL;
	}

	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Step)) return false;

		Step step = (Step) o;

		if (idFromAPI != null ? !idFromAPI.equals(step.idFromAPI) : step.idFromAPI != null) return false;
		if (shortDescription != null ? !shortDescription.equals(step.shortDescription) : step.shortDescription != null) return false;
		if (description != null ? !description.equals(step.description) : step.description != null) return false;
		if (videoURL != null ? !videoURL.equals(step.videoURL) : step.videoURL != null) return false;
		return thumbnailURL != null ? thumbnailURL.equals(step.thumbnailURL) : step.thumbnailURL == null;
	}

	@Override
	public int hashCode() {
		int result = idFromAPI != null ? idFromAPI.hashCode() : 0;
		result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (videoURL != null ? videoURL.hashCode() : 0);
		result = 31 * result + (thumbnailURL != null ? thumbnailURL.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Step{" +
				"idFromAPI='" + idFromAPI + '\'' +
				", shortDescription='" + shortDescription + '\'' +
				", description='" + description + '\'' +
				", videoURL='" + videoURL + '\'' +
				", thumbnailURL='" + thumbnailURL + '\'' +
				", id=" + id +
				'}';
	}
}
