package com.alex.popularmovies.app.data.source.queryspec.remote;

import com.alex.popularmovies.app.data.source.queryspec.BaseQuerySpec;

import java.net.URL;

/**
 * Created by Alex on 17/12/2017.
 */

public abstract class RemoteQuery extends BaseQuerySpec<URL> {

    public RemoteQuery(int mLimit, int mOffset) {
        super(mLimit, mOffset);
    }
}
