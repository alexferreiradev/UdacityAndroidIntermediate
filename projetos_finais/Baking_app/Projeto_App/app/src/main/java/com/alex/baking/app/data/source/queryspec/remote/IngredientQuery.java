package com.alex.baking.app.data.source.queryspec.remote;

import java.net.URL;

public class IngredientQuery extends RemoteQuery {

	private Long recipeId;

	public IngredientQuery(int mLimit, int mOffset, Long recipeId) {
		super(mLimit, mOffset);
		this.recipeId = recipeId;
	}

	@Override
	public URL getQuery() {
		// TODO: 24/07/18 Adicionar criacao de URL
		return null;
	}
}
