package com.alex.popularmovies.app.data.source.sql;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.alex.popularmovies.app.data.source.exception.SourceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by alex on 31/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class MovieSqlTest {

	private MovieSql source;

	@Before
	public void setUp() throws Exception {
		Context context = InstrumentationRegistry.getContext();

		source = new MovieSql(context);
	}

	@Test(expected = SourceException.class)
	public void create() throws Exception {
	}

	@Test(expected = SourceException.class)
	public void recover_id() throws Exception {
	}

	@Test(expected = SourceException.class)
	public void recover() throws Exception {
	}

	@Test(expected = SourceException.class)
	public void update() throws Exception {
	}

	@Test(expected = SourceException.class)
	public void delete() throws Exception {
	}

}