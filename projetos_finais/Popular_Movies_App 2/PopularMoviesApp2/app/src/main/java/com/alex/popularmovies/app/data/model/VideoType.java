package com.alex.popularmovies.app.data.model;

/**
 * Created by alex on 27/05/18.
 */

enum VideoType {

	TRAILLER("Trailer"), TEASER("Teaser"), CLIP("Clip"), FEATURETTE("Featurette");

	private String key;

	VideoType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
