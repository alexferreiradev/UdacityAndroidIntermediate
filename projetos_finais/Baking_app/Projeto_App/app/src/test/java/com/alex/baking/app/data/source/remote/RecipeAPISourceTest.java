package com.alex.baking.app.data.source.remote;

import com.alex.baking.app.data.model.Recipe;
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

public class RecipeAPISourceTest {

	@Mock
	private NetworkResource networkResource;

	@Mock
	private Recipe model;

	@Mock
	private QuerySpecification query;

	@InjectMocks
	private RecipeAPISource source;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void recover_query() throws ConnectionException {
		String validJsonString = Util.readFileTextFromResources("/recipe_source/valid_recipe_file.json");
		when(query.getQuery()).thenReturn(null);
		when(networkResource.getStringResourceFromURL(any())).thenReturn(validJsonString);

		List<Recipe> recipeList = source.recover(query); // TODO: 25/07/18 verificar se com teste de instrumentação funciona

		assertNotNull(recipeList);
		assertEquals(4, recipeList.size());
	}

	@Test(expected = RuntimeException.class)
	public void create() {
		source.create(model);
	}

	@Test(expected = RuntimeException.class)
	public void recover() {
		source.recover(new Long(null));
	}

	@Test(expected = RuntimeException.class)
	public void update() {
		source.update(model);
	}

	@Test(expected = RuntimeException.class)
	public void delete() {
		source.delete(model);
	}
}