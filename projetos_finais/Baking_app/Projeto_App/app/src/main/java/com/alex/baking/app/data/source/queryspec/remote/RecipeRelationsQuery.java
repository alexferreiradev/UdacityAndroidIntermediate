package com.alex.baking.app.data.source.queryspec.remote;

import java.net.MalformedURLException;
import java.net.URL;

public class RecipeRelationsQuery extends RemoteQuery {

	private Long recipeId;

	public RecipeRelationsQuery(int mLimit, int mOffset, Long recipeId) {
		super(mLimit, mOffset);
		this.recipeId = recipeId;
	}

	@Override
	public URL getQuery() {
		try {
			String baseUrl = "http://go.udacity.com/android-baking-app-json?id=";
			return new URL(baseUrl + String.valueOf(recipeId));
		} catch (MalformedURLException ignored) {
		}

		return null;
	}
}
