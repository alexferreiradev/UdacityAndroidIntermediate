package com.alex.baking.app.data.repository.recipe;

import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.model.Step;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.cache.MemoryCache;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.queryspec.remote.RecipeQuery;
import com.alex.baking.app.data.source.queryspec.remote.RecipeRelationsRemoteQuery;
import com.alex.baking.app.data.source.queryspec.sql.SqlQuery;
import com.alex.baking.app.data.source.remote.StepSource;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeRepositoryJUnitTest {
	/***
	 * Possue mais testes na pasta de testes instrumentados devido a Uri.
	 */

	@Mock
	private QuerySpecification<URL> query;
	@Mock
	private Recipe model;

	@Mock(name = "cache")
	private MemoryCache<Recipe> mCacheSource;
	@Mock(name = "local")
	private DefaultSource<Recipe, SqlQuery> mLocalSource;
	@Mock(name = "remoto")
	private DefaultSource<Recipe, URL> mRemoteSource;

	@InjectMocks
	private RecipeRepository repo;
	private List<Recipe> validList;

	@Before
	public void setUp() throws ConnectionException {
		MockitoAnnotations.initMocks(this);
		repo = new RecipeRepository(mCacheSource, mLocalSource, mRemoteSource); // Workaround to params with same type

		validList = new ArrayList<>();
		validList.add(model);

		doReturn(validList).when(mRemoteSource).recover(any(RecipeQuery.class));
	}

	@Test
	public void getStepListByRecipe() throws ConnectionException {
		DefaultSource<Step, URL> sourceMock = mock(StepSource.class);
		List<Step> validStepList = new ArrayList<>();
		Step validStep = new Step();
		validStepList.add(validStep);
		when(sourceMock.recover(any(RecipeRelationsRemoteQuery.class))).thenReturn(validStepList);
		repo.setRemoteStepSource(sourceMock);
		List<Step> stepList = repo.getStepListByRecipe(1L, 0, 0);

		assertNotNull(stepList);
		assertEquals(validStepList.size(), stepList.size());
		verify(sourceMock).recover(any(RecipeRelationsRemoteQuery.class));
	}
}