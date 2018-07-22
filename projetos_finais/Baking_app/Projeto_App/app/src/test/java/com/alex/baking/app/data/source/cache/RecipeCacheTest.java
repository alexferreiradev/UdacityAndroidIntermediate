package com.alex.baking.app.data.source.cache;

import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by alex on 31/05/18.
 */
public class RecipeCacheTest {

	private RecipeCache cache;
	@Mock
	private Recipe model;

	@Before
	public void setUp() {
		cache = RecipeCache.getInstance();
	}

	@Test
	public void recover_id() {
		Long validId = 2L;
		Recipe validRecipe = new Recipe();
		validRecipe.setId(validId);
		cache.getCache().add(validRecipe);

		Recipe recover = cache.recover(validId);

		assertEquals(validRecipe, recover);
	}

	@Test(expected = RuntimeException.class)
	public void delete() {
		cache.delete(null);
	}

	@SuppressWarnings("ConstantConditions")
	@Test(expected = RuntimeException.class)
	public void recover() {
		QuerySpecification querySpecification = null;
		cache.recover(querySpecification);
	}

	@Test(expected = RuntimeException.class)
	public void update() {
		cache.update(null);
	}

	@Test(expected = RuntimeException.class)
	public void create() {
		cache.create(model);
	}

	@Test
	public void addAllCache() {
		List<Recipe> recipeList = new ArrayList<>();
		cache.addAllCache(recipeList, 0);
		// TODO: 22/07/18 Completar com assert
	}

	@Test
	public void removeAllCache() {
		cache.removeAllCache(new ArrayList<>());
		// TODO: 22/07/18 Completar com assert
	}

}