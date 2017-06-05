package com.alex.popularmovies.app.data.source;

import android.net.Uri;

/**
 * Created by Alex on 02/04/2017.
 */

public abstract class BaseQuerySpecification {

    protected Uri mUri;
    protected String mProjection;
    protected String mSelection;
    protected String[] mSelectionArgs;
    protected String mOrderBy;
    protected String mLimit;

    protected BaseQuerySpecification() {
        makeQuery();
    }

    protected abstract void makeQuery();

    public Uri getmUri() {
        return mUri;
    }

    public String getmProjection() {
        return mProjection;
    }

    public String getmSelection() {
        return mSelection;
    }

    public String[] getmSelectionArgs() {
        return mSelectionArgs;
    }

    public String getmOrderBy() {
        return mOrderBy;
    }

    public String getmLimit() {
        return mLimit;
    }
}
