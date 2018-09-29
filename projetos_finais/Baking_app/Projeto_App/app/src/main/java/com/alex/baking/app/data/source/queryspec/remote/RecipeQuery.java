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
			return new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
		} catch (MalformedURLException ignored) {
		}

		return null;
	}
}
