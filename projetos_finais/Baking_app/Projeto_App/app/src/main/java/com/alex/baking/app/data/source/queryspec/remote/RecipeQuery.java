package com.alex.baking.app.data.source.queryspec.remote;

import java.net.URL;

public class RecipeQuery extends RemoteQuery {

	public RecipeQuery(int mLimit, int mOffset) {
		super(mLimit, mOffset);
	}

	@Override
	public URL getQuery() {
		return null;
	}
}
