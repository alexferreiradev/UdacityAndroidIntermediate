package com.alex.popularmovies.app.data.model;

/**
 * Created by alex on 27/05/18.
 */

public class Video extends BaseModel<Video> {

	private String iso639_1;
	private String iso3166_1;
	private String key;
	private String name;
	private String site;
	private String size;
	private VideoType type;

	public String getIso639_1() {
		return iso639_1;
	}

	public void setIso639_1(String iso639_1) {
		this.iso639_1 = iso639_1;
	}

	public String getIso3166_1() {
		return iso3166_1;
	}

	public void setIso3166_1(String iso3166_1) {
		this.iso3166_1 = iso3166_1;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public VideoType getType() {
		return type;
	}

	public void setType(VideoType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Video video = (Video) o;

		if (iso639_1 != null ? !iso639_1.equals(video.iso639_1) : video.iso639_1 != null) return false;
		if (iso3166_1 != null ? !iso3166_1.equals(video.iso3166_1) : video.iso3166_1 != null) return false;
		if (key != null ? !key.equals(video.key) : video.key != null) return false;
		if (name != null ? !name.equals(video.name) : video.name != null) return false;
		if (site != null ? !site.equals(video.site) : video.site != null) return false;
		if (size != null ? !size.equals(video.size) : video.size != null) return false;
		return type == video.type;
	}

	@Override
	public int hashCode() {
		int result = iso639_1 != null ? iso639_1.hashCode() : 0;
		result = 31 * result + (iso3166_1 != null ? iso3166_1.hashCode() : 0);
		result = 31 * result + (key != null ? key.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (site != null ? site.hashCode() : 0);
		result = 31 * result + (size != null ? size.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Video{" +
				"iso639_1='" + iso639_1 + '\'' +
				", iso3166_1='" + iso3166_1 + '\'' +
				", key='" + key + '\'' +
				", id=" + id +
				", name='" + name + '\'' +
				", site='" + site + '\'' +
				", size='" + size + '\'' +
				", type=" + type +
				'}';
	}
}
