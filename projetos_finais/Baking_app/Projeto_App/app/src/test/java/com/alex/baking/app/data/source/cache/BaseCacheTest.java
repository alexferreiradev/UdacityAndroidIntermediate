package com.alex.baking.app.data.source.cache;

import com.alex.baking.app.data.model.BaseModel;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class BaseCacheTest {

	private List<BaseModel> cacheList;
	@Mock
	private BaseModel model;

	@InjectMocks
	private BaseCache<BaseModel> cache;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		cacheList = cache.mCache;
		cacheList.add(model);
		when(model.getId()).thenReturn(1L);
	}

	@Test
	public void updateCacheTo() {
		List<BaseModel> newValues = new ArrayList<>();
		cache.updateCacheTo(newValues);
		cacheList = cache.mCache;

		assertEquals(newValues, cacheList);
		assertEquals(newValues.size(), cacheList.size());
	}

	@Test
	public void getCache() {
		assertEquals(cacheList, cache.getCache());
	}

	@Test(expected = RuntimeException.class)
	public void create() {
		cache.create(model);
	}

	@Test
	public void recover() {
		BaseModel recover = cache.recover(1L);

		assertNotNull(recover);
		assertEquals(Long.valueOf(1L), recover.getId());
	}

	@Test(expected = RuntimeException.class)
	public void recover1() {
		cache.recover(mock(QuerySpecification.class));
	}

	@Test(expected = RuntimeException.class)
	public void update() {
		cache.update(model);
	}

	@Test
	public void delete() {
		cache.delete(model);

		assertEquals(0, cache.mCache.size());
	}

	@Test
	public void addAllCache() {
	}

	@Test
	public void removeAllCache() {
	}
}