package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.os.Bundle;

import com.alex.popularmovies.app.model.BaseModel;

import java.util.List;

/**
 * Created by Alex on 23/03/2017.
 */

public class MoviesPresenter extends BaseListPresenter {

    protected MoviesPresenter(BaseListContract.View mView, Context context, Bundle savedInstanceState) {
        super(mView, context, savedInstanceState);
    }

    @Override
    public void selectItemClicked(BaseModel item) {

    }

    @Override
    public void showAddOrEditView(BaseModel data) {

    }

    @Override
    protected void setEmptyView() {

    }

    @Override
    protected List loadDataFromSource(int offset, int loadItemsLimit) {
        return null;
    }

    @Override
    protected int updateDataFromSource(BaseModel data) {
        return 0;
    }

    @Override
    protected boolean removeDataFromSource(BaseModel data) {
        return false;
    }

    @Override
    protected Long saveDataFromSource(BaseModel data) {
        return null;
    }

    @Override
    protected List applyFilterFromAdapter() {
        return null;
    }

    @Override
    protected List applyFilterFromSource() {
        return null;
    }

    @Override
    protected void showDataNotEditedError() {

    }

    @Override
    protected void showDataEditedSuccess() {

    }

    @Override
    protected void showDataSavedSuccess() {

    }

    @Override
    protected void showDataRemovedError() {

    }

    @Override
    protected void showDataRemovedSuccess() {

    }

    @Override
    protected void showDataNotSavedError() {

    }
}
