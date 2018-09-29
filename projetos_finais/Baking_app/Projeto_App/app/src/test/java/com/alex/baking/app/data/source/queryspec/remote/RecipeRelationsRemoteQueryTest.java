package com.alex.baking.app.data.source.queryspec.remote;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

public class RecipeRelationsRemoteQueryTest {

	@InjectMocks
	private RecipeRelationsRemoteQuery query;

	@Before
	public void setUp() {
		query = new RecipeRelationsRemoteQuery(0, 0, 2L);
	}

	@Test
	public void getQuery() {
		assertEquals("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json?id=2", query.getQuery().toString());
	}
}