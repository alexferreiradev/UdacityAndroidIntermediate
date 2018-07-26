package com.alex.baking.app.data.repository.movie;

import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.cache.MemoryCache;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeRepositoryTest {

	@Mock
	private QuerySpecification query;
	@Mock
	private Recipe model;

	@Mock(name = "cache")
	private MemoryCache<Recipe> mCacheSource;
	@Mock(name = "local")
	private DefaultSource<Recipe> mLocalSource;
	@Mock(name = "remoto")
	private DefaultSource<Recipe> mRemoteSource;

	@InjectMocks
	private RecipeRepository repo;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repo = new RecipeRepository(mCacheSource, mLocalSource, mRemoteSource); // Workaround to params with same type
	}

	@Test
	public void getRecipeList() throws ConnectionException {
		List<Recipe> validList = new ArrayList<>();
		validList.add(model);
		when(mRemoteSource.recover(any(QuerySpecification.class))).thenReturn(validList);

		List<Recipe> recipeList = repo.getRecipeList(0, 0);

		assertNotNull(recipeList);
		assertEquals(validList.size(), recipeList.size());
		verify(mRemoteSource).recover(any(QuerySpecification.class));
	}

	@Test
	public void getIngredientListByRecipe() throws ConnectionException {
		assertNotNull(repo.getIngredientListByRecipe(1L, 0, 0));
	}

	@Test
	public void getStepListByRecipe() throws ConnectionException {
		assertNotNull(repo.getStepListByRecipe(1L, 0, 0));
	}
}