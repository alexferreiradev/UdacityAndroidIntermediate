package com.alex.popularmovies.app.data.source.sql;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import com.alex.popularmovies.app.data.model.Movie;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import com.alex.popularmovies.app.data.source.queryspec.QuerySpecification;
import com.alex.popularmovies.app.data.source.queryspec.sql.SqlQuery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by alex on 31/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class MovieSqlTest extends ProviderTestCase2<PMProvider> {

	@Mock
	private ContentResolver mResolver;

	private MovieSql source;

	public MovieSqlTest() {
		super(PMProvider.class, PMContract.CONTENT_AUTHORITY);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		Context context = InstrumentationRegistry.getTargetContext();
		setContext(context);
		MockitoAnnotations.initMocks(this);

		source = new MovieSql(context);
		source.mResolver = getMockContentResolver();
	}

	@Test(expected = SourceException.class)
	public void create() throws Exception {
		Movie movieMock = mock(Movie.class);
		Movie movieAnswer = mock(Movie.class);

		when(movieMock.getId()).thenReturn(null);
		when(movieAnswer.getId()).thenReturn(2L);

		Movie movieResult = source.create(movieMock);

		assertNotNull(movieResult);
		assertEquals(new Long(2L), movieResult.getId());
	}

	@Test(expected = SourceException.class)
	public void recover_id() throws Exception {
		source.recover(1L);
	}

	@Ignore // TODO: 17/06/18 Erro de mock para mResolver. Não consegui mock - método final.
	@Test
	public void recover() throws Exception {
		Cursor cursorMock = mock(Cursor.class);
		QuerySpecification<SqlQuery> queryMock = mock(QuerySpecification.class);
		SqlQuery sqlQueryMock = mock(SqlQuery.class);

		when(queryMock.getQuery()).thenReturn(sqlQueryMock);
		when(sqlQueryMock.getUri()).thenReturn(PMContract.MovieEntry.CONTENT_URI);
		doReturn(cursorMock).when(mResolver).query(PMContract.MovieEntry.CONTENT_URI, null, null, null, null);
		when(mResolver.query(sqlQueryMock.getUri(), null, null, null, null)).thenReturn(cursorMock);

		List<Movie> movieList = source.recover(queryMock);

		assertNotNull(movieList);
		assertFalse(movieList.isEmpty());
		assertEquals(1, movieList.size());
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