package com.alex.popularmovies.app.ui.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alex.popularmovies.app.R;
import com.alex.popularmovies.app.data.model.BaseModel;
import com.alex.popularmovies.app.ui.presenter.BasePresenter;

/**
 * Created by Alex on 16/03/2017.
 */

public abstract class BaseActivity<ModelType extends BaseModel,
        ViewType extends BasePresenter.View,
        PresenterType extends BasePresenter>
        extends AppCompatActivity
        implements BasePresenter.View<ModelType> {

    protected ModelType mData;
    protected PresenterType mPresenter;
    protected ProgressBar mProgressBar;


    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.startPresenterView();
        }
    }

    @Override
    public void initializeWidgets(Bundle savedInstanceState) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.myToolBar);
        if (mToolbar == null)
            throw new NullPointerException("A Activity não tem myToolBar no layout.");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(24, 24, 24, 24);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (mProgressBar == null)
            throw new NullPointerException("A Activity não tem progressBar no layout.");
    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadProgressBarVisibility(boolean toVisible) {
        if (toVisible) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
