package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.repository.DefaultRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alex on 08/04/2017.
 */
public class MoviesPresenterTest {
    @Mock
    Context mContext;
    @Mock
    MoviesPresenter.View mView;
    @Mock
    DefaultRepository<Movie> repository;
    @InjectMocks
    MoviesPresenter moviesPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetEmptyView() throws Exception {
        moviesPresenter.setEmptyView();
        verify(mView).setEmptyView(anyString());
    }

    @Test
    public void testLoadDataFromSource() throws Exception {
        moviesPresenter.loadDataFromSource();
        BaseAdapter mockAdapter = Mockito.mock(BaseAdapter.class);
        when(mockAdapter.getCount()).thenReturn(2);
        when(mView.getAdapter()).thenReturn(mockAdapter);
        verify(mView).addAdapterData(anyList());
    }

    @Test
    public void testApplyFilterFromAdapter() throws Exception {
        moviesPresenter.applyFilterFromAdapter();
        verify(mView).createListAdapter(anyList());
    }

    @Test
    public void testSelectItemClicked() throws Exception {
        moviesPresenter.selectItemClicked(new Movie());
        verify(mView).showAddOrEditDataView(Matchers.<Movie>any());
    }

    @Test
    public void testShowAddOrEditView() throws Exception {
        moviesPresenter.showAddOrEditView(new Movie());
        verify(mView).showAddOrEditDataView(Matchers.<Movie>any());
    }

    @Test
    public void testApplyFilter() throws Exception {
        moviesPresenter.applyFilter("filterKey", "filterValue");
        verify(mView).createListAdapter(anyList());
    }

    @Test
    public void testPopulateAdapter() throws Exception {
        List<Movie> list = new Mockito().mock(List.class);
        when(list.size()).thenReturn(10);
        when(list.isEmpty()).thenReturn(false);
        MoviesPresenter spyPresenter = spy(moviesPresenter);

        doReturn(true).when(spyPresenter).isNewAdapter(); // criacao de adapter
        spyPresenter.populateAdapter(list);
        verify(mView).createListAdapter(anyList());

        reset(spyPresenter);
        doReturn(false).when(spyPresenter).isNewAdapter(); // Adicao de dados
        spyPresenter.populateAdapter(list);
        verify(mView, times(1)).addAdapterData(anyList());
    }

    @Test
    public void testStartPresenterView() throws Exception {
        MoviesPresenter spy = spy(moviesPresenter);
        doNothing().when(spy).initialize();
        spy.startPresenterView();
        verify(mView).initializeArgumentsFromIntent();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme