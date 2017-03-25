package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.os.Bundle;

import com.alex.popularmovies.app.model.Movie;

import java.util.List;

/**
 * Created by Alex on 23/03/2017.
 */

public class MoviesPresenter extends BaseListPresenter<MoviesPresenter.View, Movie> {


    public MoviesPresenter(View mView, Context context, Bundle savedInstanceState) {
        super(mView, context, savedInstanceState);
    }

    @Override
    public void selectItemClicked(Movie item) {

    }

    @Override
    public void showAddOrEditView(Movie data) {

    }

    @Override
    protected void setEmptyView() {

    }

    @Override
    protected List<Movie> loadDataFromSource(int offset, int loadItemsLimit) {
        return null;
    }

    @Override
    protected int updateDataFromSource(Movie data) {
        return 0;
    }

    @Override
    protected boolean removeDataFromSource(Movie data) {
        return false;
    }

    @Override
    protected Long saveDataFromSource(Movie data) {
        return null;
    }

    @Override
    protected List<Movie> applyFilterFromAdapter() {
        return null;
    }

    @Override
    protected List<Movie> applyFilterFromSource() {
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

    public interface View extends BaseListContract.View<Movie> {

    }
}
