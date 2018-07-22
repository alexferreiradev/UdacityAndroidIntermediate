package com.alex.baking.app.data.source.remote;

import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Movie;
import com.alex.baking.app.data.source.exception.SourceException;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.remote.network.NetworkResourceManager;
import com.alex.baking.app.util.MoviesUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by alex on 31/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class RemoteMovieSourceTest {

	@Mock(name = "mNetworkResource")
	private NetworkResourceManager networkResource;

	@InjectMocks
	private RemoteMovieSource source;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void recover() throws Exception {
		InputStream fakeStream = getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.movie_json_example);
		String movieString = MoviesUtil.readStream(fakeStream);
		when(networkResource.getStringResourceFromURL(any())).thenReturn(movieString);
		QuerySpecification querryMocked = mock(QuerySpecification.class);
		List<Movie> movies = source.recover(querryMocked);

		assertNotNull(movies);
		assertFalse(movies.isEmpty());
		assertEquals(20, movies.size());
	}

	@Test(expected = SourceException.class)
	public void create() throws Exception {
		source.create(null);
	}

	@Test(expected = SourceException.class)
	public void recover_by_id() throws Exception {
		source.recover(1L);
	}

	@Test(expected = SourceException.class)
	public void update() throws Exception {
		source.update(null);
	}

	@Test(expected = SourceException.class)
	public void delete() throws Exception {
		source.delete(null);
	}
}