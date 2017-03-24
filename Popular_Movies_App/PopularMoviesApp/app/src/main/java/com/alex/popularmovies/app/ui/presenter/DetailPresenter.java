package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.os.Bundle;

import com.alex.popularmovies.app.model.BaseModel;

/**
 * Created by Alex on 23/03/2017.
 */

public class DetailPresenter extends BasePresenter {

    protected DetailPresenter(View mView, Context mContext, Bundle savedInstanceState) {
        super(mView, mContext, savedInstanceState);
    }

    @Override
    protected void initialize() {

    }

    @Override
    public Object taskFromSource(BaseModel data, TaskType taskType) {
        return null;
    }

    @Override
    protected void analiseBackgroundThreadResultData(Object result, TaskType taskType) {

    }
}
