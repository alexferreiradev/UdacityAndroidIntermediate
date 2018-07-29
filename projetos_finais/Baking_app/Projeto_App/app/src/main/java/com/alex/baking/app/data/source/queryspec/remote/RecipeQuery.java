package com.alex.baking.app.data.source.queryspec.remote;

import java.net.MalformedURLException;
import java.net.URL;

public class RecipeQuery extends RemoteQuery {

	public RecipeQuery(int mLimit, int mOffset) {
		super(mLimit, mOffset);
	}

	@Override
	public URL getQuery() {
		try {
			return new URL("http://go.udacity.com/android-baking-app-json");
		} catch (MalformedURLException ignored) {
		}

		return null;
	}
}
