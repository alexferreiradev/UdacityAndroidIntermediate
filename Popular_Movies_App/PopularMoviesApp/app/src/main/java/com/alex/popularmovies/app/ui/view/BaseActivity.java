package com.alex.popularmovies.app.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alex.popularmovies.app.model.BaseModel;
import com.alex.popularmovies.app.ui.presenter.BasePresenter;

/**
 * Created by Alex on 23/03/2017.
 */

public class BaseActivity extends AppCompatActivity implements BasePresenter.View{


    @Override
    public void toggleProgressBar() {

    }

    @Override
    public void initializeWidgets(Bundle savedInstanceState) {

    }

    @Override
    public void initializeArgumentsFromIntent() {

    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showSuccessMsg(String msg) {

    }

    @Override
    public void startBackgroundThread(BaseModel data, BasePresenter.TaskType taskType) {

    }
}
