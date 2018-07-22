package com.alex.baking.app.data.source.queryspec.remote;

import android.net.Uri;
import com.alex.baking.app.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alex on 02/04/2017.
 */

public class VideoRemoteQuery extends RemoteQuery {

	private Long mMovieId;

	public VideoRemoteQuery(int mLimit, int mOffset, Long movieId) {
		super(mLimit, mOffset);
		mMovieId = movieId;
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
				.appendPath(mMovieId.toString())
				.appendPath(API_MOVIE_VIDEO_PATH)
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
