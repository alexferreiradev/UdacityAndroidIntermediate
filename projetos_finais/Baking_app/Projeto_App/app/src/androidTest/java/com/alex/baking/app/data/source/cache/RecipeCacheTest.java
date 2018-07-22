package com.alex.baking.app.data.source.cache;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by alex on 31/05/18.
 */
public class RecipeCacheTest {

	private RecipeCache cache;

	@Before
	public void setUp() {
		cache = RecipeCache.getInstance();
	}

	@Test
	public void recover_id() {
		cache.recover(1L);
	}

	@Test
	public void delete() {
		cache.recover(1L);
	}

	@Test
	public void recover() {
		cache.recover(1L);
	}

	@Test
	public void update() {
		cache.update(null);
	}

	@Test
	public void create() {
		cache.recover(1L);
	}

	@Test
	public void addAllCache() {
		cache.recover(1L);
	}

	@Test
	public void removeAllCache() {
		cache.recover(1L);
	}

}