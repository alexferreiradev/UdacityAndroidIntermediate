package com.alex.baking.app.data.model;

/**
 * Created by alex on 27/05/18.
 */

public enum VideoType {

	TRAILER("Trailer"), TEASER("Teaser"), CLIP("Clip"), FEATURETTE("Featurette");

	private String key;

	VideoType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
