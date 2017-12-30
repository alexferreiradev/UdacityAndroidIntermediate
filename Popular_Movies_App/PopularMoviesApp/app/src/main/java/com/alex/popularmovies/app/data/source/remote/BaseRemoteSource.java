package com.alex.popularmovies.app.data.source.remote;

import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.data.source.DefaultSource;

import java.net.HttpURLConnection;

/**
 * Created by Alex on 02/04/2017.
 */

public abstract class BaseRemoteSource<ModelType extends BaseModel> implements DefaultSource<ModelType> {

    protected HttpURLConnection mHttpURLConnection;

    public BaseRemoteSource(HttpURLConnection mHttpURLConnection) {
        this.mHttpURLConnection = mHttpURLConnection;
    }
}
