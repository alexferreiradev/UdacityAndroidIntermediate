package com.alex.popularmovies.app.data.source.queryspec.remote;

import android.net.Uri;
import com.alex.popularmovies.app.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alex on 02/04/2017.
 */

public class MoviesRemoteQuery extends RemoteQuery {

	private static final String API_MOVIE_PATH = "movie";
	private static final String API_PARAM_APIKEY = "api_key";
	private static final String REMOTE_API_V3 = "3";
	private static final String REMOTE_API_AUTHORITY = "api.themoviedb.org";
	private static final String REMOTE_API_SCHEME = "http";
	private static final String API_PARAM_PAGE = "page";
	private static final int REMOTE_API_PAGE_SIZE = 20; // testado
	private final String mKey;

	public MoviesRemoteQuery(int mLimit, int mOffset, String mKey) {
		super(mLimit, mOffset);
		this.mKey = mKey;
	}

	@Override
	public URL getQuery() {
		String api_key = BuildConfig.OPEN_MOVIE_API_KEY;
		Uri.Builder builder = new Uri.Builder();
		String page = String.valueOf(Double.valueOf(Math.ceil((mOffset + REMOTE_API_PAGE_SIZE) / (REMOTE_API_PAGE_SIZE * 1.0))).intValue());
		builder.scheme(REMOTE_API_SCHEME)
				.authority(REMOTE_API_AUTHORITY)
				.path(REMOTE_API_V3)
				.appendPath(API_MOVIE_PATH)
				.appendPath(mKey)
				.appendQueryParameter(API_PARAM_APIKEY, api_key)
				.appendQueryParameter(API_PARAM_PAGE, page);

		Uri uri = builder.build();
		try {
			return new URL(uri.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
