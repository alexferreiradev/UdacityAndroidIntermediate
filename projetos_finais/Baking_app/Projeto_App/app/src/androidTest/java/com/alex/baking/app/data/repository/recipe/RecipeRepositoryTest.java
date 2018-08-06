package com.alex.baking.app.data.repository.recipe;

import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.source.DefaultSource;
import com.alex.baking.app.data.source.cache.MemoryCache;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.queryspec.remote.RecipeQuery;
import com.alex.baking.app.data.source.queryspec.remote.RecipeRelationsRemoteQuery;
import com.alex.baking.app.data.source.queryspec.sql.BaseSqlSpecification;
import com.alex.baking.app.data.source.queryspec.sql.SqlQuery;
import com.alex.baking.app.data.source.remote.IngredientSource;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.data.source.sql.IngredientSqlSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class) // Necessita de Uri do Android
public class RecipeRepositoryTest {

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
	public void get_recipe_list() throws ConnectionException {
		when(model.getIdFromAPI()).thenReturn("1");
		when(model.getId()).thenReturn(2L);
		doReturn(model).when(mLocalSource).create(any(Recipe.class));

		List<Recipe> recipeList = repo.getRecipeList(0, 0);

		assertNotNull(recipeList);
		assertEquals(validList.size(), recipeList.size());
		verify(mRemoteSource).recover(any(RecipeQuery.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getIngredientListByRecipe() throws ConnectionException {
		DefaultSource<Ingredient, URL> sourceMock = mock(IngredientSource.class);
		List<Ingredient> validIngredientList = new ArrayList<>();
		Ingredient validIngredient = new Ingredient();
		validIngredientList.add(validIngredient);
		when(sourceMock.recover(any(RecipeRelationsRemoteQuery.class))).thenReturn(validIngredientList);
		repo.setRemoteIngredientSource(sourceMock);
		Ingredient ingredientMock = mock(Ingredient.class);
		when(ingredientMock.getId()).thenReturn(2L);
		IngredientSqlSource ingredientSqlSource = mock(IngredientSqlSource.class);
		doReturn(ingredientMock).when(ingredientSqlSource).delete(null);
		QuerySpecification<SqlQuery> querySpecification = new BaseSqlSpecification(null, 0, 0);
		List<Ingredient> ingredientListWithId = new ArrayList<>();
		ingredientListWithId.add(ingredientMock);
		doReturn(ingredientListWithId).when(ingredientSqlSource).recover(any(querySpecification.getClass()));
		repo.setIngredientLocalSource(ingredientSqlSource);

		List<Ingredient> ingredientList = repo.getIngredientListByRecipe(1L, 0, 0);

		assertNotNull(ingredientList);
		assertEquals(validIngredientList.size(), ingredientList.size());
		assertEquals(Long.valueOf(2L), ingredientList.get(0).getId());
		verify(sourceMock).recover(any(RecipeRelationsRemoteQuery.class));
	}
}