package com.alex.baking.app.data.source.remote;

import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.remote.network.NetworkResource;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class IngredientSourceTest {

	@Mock
	private NetworkResource networkResource;

	@Mock
	private Ingredient model;

	@Mock
	private QuerySpecification query;

	@InjectMocks
	private IngredientSource source;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void recover_query() throws ConnectionException {
		String validJsonString = Util.readFileTextFromResources("/recipe_source/valid_recipe_file.json");
		when(query.getQuery()).thenReturn(null);
		when(networkResource.getStringResourceFromURL(any())).thenReturn(validJsonString);

		List<Ingredient> ingredientList = source.recover(query);

		assertNotNull(ingredientList);
		assertEquals(9, ingredientList.size());
	}
}