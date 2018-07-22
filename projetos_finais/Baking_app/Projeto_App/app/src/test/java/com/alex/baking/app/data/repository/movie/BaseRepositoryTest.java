package com.alex.baking.app.data.repository.movie;

import com.alex.baking.app.data.model.BaseModel;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.repository.BaseRepository;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.cache.MemoryCache;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by alex on 31/05/18.
 */
@SuppressWarnings("unchecked")
public class BaseRepositoryTest {

	@Mock
	private Recipe model;
	@Mock(name = "mRemoteSource")
	private DefaultSource<Recipe> remoteSource;
	@Mock(name = "mLocalSource")
	private DefaultSource<Recipe> localSource;
	@Mock(name = "mCacheSource")
	private MemoryCache<Recipe> cacheSource;

	@InjectMocks
	private BaseRepository<BaseModel> recipeRepository;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		recipeRepository = new BaseRepository(cacheSource, localSource, remoteSource);
	}

	@Test
	public void test_recover() throws ConnectionException {
		Long validId = 1L;
		when(localSource.recover(validId)).thenReturn(model);
		when(cacheSource.isDirty()).thenReturn(true);

		BaseModel recipe = recipeRepository.recover(validId);

		assertEquals(model, recipe);
		verify(localSource).recover(validId);
	}

	@Test
	public void test_update() {
		Long validId = 2L;
		when(model.getId()).thenReturn(validId);
		when(localSource.update(model)).thenReturn(model);

		BaseModel recipe = recipeRepository.update(model);

		assertEquals(model, recipe);
		verify(localSource).update(model);
	}

	@Test
	public void test_create() {
		when(model.getId()).thenReturn(null);
		Recipe modelWithId = mock(Recipe.class);
		Long validId = 2L;
		when(modelWithId.getId()).thenReturn(validId);
		when(localSource.create(model)).thenReturn(modelWithId);

		BaseModel recipe = recipeRepository.create(model);

		assertNotNull(recipe);
		assertEquals(validId, recipe.getId());
		verify(localSource).create(model);
	}

	@Test
	public void test_delete() {
		when(model.getId()).thenReturn(null);
		when(localSource.delete(model)).thenReturn(model);

		BaseModel recipe = recipeRepository.delete(model);

		assertEquals(model, recipe);
		verify(localSource).delete(model);
	}

	@Test
	public void test_recover_com_cache() throws ConnectionException {
		Long validId = 1L;
		when(cacheSource.recover(validId)).thenReturn(model);

		BaseModel recipe = recipeRepository.recover(validId);

		assertEquals(model, recipe);
		verify(localSource, never()).recover(validId);
		verify(cacheSource).recover(validId);
	}

	//	--------------------------------------               Testes Negativos


	@Test
	public void test_argumentos_nulos() throws ConnectionException {
		assertNull(recipeRepository.create(null));
		assertNull(recipeRepository.update(null));
		assertNull(recipeRepository.delete(null));
		assertNull(recipeRepository.recover(null));
	}

	@Test(expected = RuntimeException.class)
	public void test_update_model_nao_existente() {
		when(model.getId()).thenReturn(null);

		recipeRepository.update(model);

		verify(localSource, Mockito.never()).update(model);
	}

	@Test(expected = RuntimeException.class)
	public void test_create_model_existente() {
		when(model.getId()).thenReturn(1L);

		recipeRepository.create(model);

		verify(localSource, Mockito.never()).create(model);
	}
}