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
    private final String mKey;

    public MoviesRemoteQuery(int mLimit, int mOffset, String mKey) {
        super(mLimit, mOffset);
        this.mKey = mKey;
    }

    @Override
    public URL getQuery() {
        String api_key = BuildConfig.OPEN_MOVIE_API_KEY;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(REMOTE_API_SCHEME)
                .authority(REMOTE_API_AUTHORITY)
                .path(REMOTE_API_V3)
                .appendPath(API_MOVIE_PATH)
                .appendPath(mKey)
                .appendQueryParameter(API_PARAM_APIKEY, api_key);

        Uri uri = builder.build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
