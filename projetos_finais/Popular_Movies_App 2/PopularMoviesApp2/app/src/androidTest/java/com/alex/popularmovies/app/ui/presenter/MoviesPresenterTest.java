package com.alex.popularmovies.app.ui.presenter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.model.MoviesType;
import com.alex.popularmovies.app.data.repository.movie.MovieRepository;
import com.alex.popularmovies.app.ui.presenter.main.MoviesContract;
import com.alex.popularmovies.app.ui.presenter.main.MoviesPresenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by Alex on 08/04/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MoviesPresenterTest {
	@Mock
	private MoviesContract.View view;
	@Mock
	private MovieRepository repository;

	private MoviesPresenter moviesPresenter;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Context context = InstrumentationRegistry.getTargetContext();
		doNothing().when(view).initializeWidgets(any());
		doNothing().when(view).setEmptyView(anyString());

		moviesPresenter = new MoviesPresenter(view, context, null, repository);
	}

	@Test
	public void loadMoreData() throws Exception {
		List<Movie> validMovies = new ArrayList<>();
		when(repository.moviesByPopularity(anyInt(), anyInt())).thenReturn(validMovies);
		moviesPresenter.loadMoreData(0, 10, 10);
		// TODO: 02/06/18 testar test multi thread, fazer pesquisa e verificar como testar chamdas de objs em threads diferentes do test
	}

	@Test
	public void setMovieType() throws Exception {
		MoviesType moviesType = MoviesType.MOST_POPULAR;
		String validTitle = moviesType.name().replaceAll("_", " ");
		List<Movie> validMovies = new ArrayList<>();
		when(repository.moviesByPopularity(anyInt(), anyInt())).thenReturn(validMovies);
		moviesPresenter.setListType(moviesType);

		verify(view).destroyListAdapter();
		verify(repository).setCacheToDirty();
		verify(view).updateMenuItems();
		verify(view).setActionBarTitle(validTitle);
	}

	@Test
	public void selectItem() throws Exception {
		Movie item = mock(Movie.class);
		moviesPresenter.selectItemClicked(item, 10);

		verify(item).getIdFromApi();
		verify(view).showDataView(item);
	}
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme