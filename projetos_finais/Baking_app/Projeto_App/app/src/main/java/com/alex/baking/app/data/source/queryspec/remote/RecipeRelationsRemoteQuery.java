package com.alex.baking.app.data.source.queryspec.remote;

import java.net.MalformedURLException;
import java.net.URL;

public class RecipeRelationsRemoteQuery extends RemoteQuery {

	private Long recipeId;

	public RecipeRelationsRemoteQuery(int mLimit, int mOffset, Long recipeId) {
		super(mLimit, mOffset);
		this.recipeId = recipeId;
	}

	@Override
	public URL getQuery() {
		try {
			String baseUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json?id=";
			return new URL(baseUrl + String.valueOf(recipeId));
		} catch (MalformedURLException ignored) {
		}

		return null;
	}
}
