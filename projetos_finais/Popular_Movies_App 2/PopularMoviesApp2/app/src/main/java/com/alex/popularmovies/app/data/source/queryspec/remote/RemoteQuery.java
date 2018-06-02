package com.alex.popularmovies.app.data.source.queryspec.remote;

import com.alex.popularmovies.app.data.source.queryspec.BaseQuerySpec;

import java.net.URL;

/**
 * Created by Alex on 17/12/2017.
 */

public abstract class RemoteQuery extends BaseQuerySpec<URL> {

	protected static final String API_MOVIE_PATH = "movie";
	protected static final String API_MOVIE_REVIEW_PATH = "reviews";
	protected static final String API_PARAM_APIKEY = "api_key";

	protected static final String REMOTE_API_V3 = "3";
	protected static final String REMOTE_API_AUTHORITY = "api.themoviedb.org";
	protected static final String REMOTE_API_SCHEME = "http";
	protected static final String API_PARAM_PAGE = "page";
	protected static final int REMOTE_API_PAGE_SIZE = 20; // testado

	public RemoteQuery(int mLimit, int mOffset) {
		super(mLimit, mOffset);
	}
}
