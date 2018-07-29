package com.alex.baking.app.data.source.queryspec.remote;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

public class RecipeRelationsQueryTest {

	@InjectMocks
	private RecipeRelationsQuery query;

	@Before
	public void setUp() {
		query = new RecipeRelationsQuery(0, 0, 2L);
	}

	@Test
	public void getQuery() {
		assertEquals("http://go.udacity.com/android-baking-app-json?id=2", query.getQuery().toString());
	}
}