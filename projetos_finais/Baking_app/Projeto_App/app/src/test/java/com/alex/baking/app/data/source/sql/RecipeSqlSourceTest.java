package com.alex.baking.app.data.source.sql;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.queryspec.sql.SqlQuery;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecipeSqlSourceTest {

	@Mock
	private QuerySpecification<SqlQuery> query;
	@Mock
	private Recipe model;
	@Mock(name = "resolver")
	private ContentResolver mContenResolver;
	@Mock
	private Context context;
	@Mock
	private Uri uriMock;
	@Mock
	private SqlQuery sqlQuery;

	@InjectMocks
	private RecipeSqlSource source;
	private RecipeSqlSource sourceSpy;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(context.getContentResolver()).thenReturn(mContenResolver);
		source = new RecipeSqlSource(context); // Workaround para mock de contentResolver

		when(uriMock.getQueryParameter("id")).thenReturn("2");
		when(context.getContentResolver()).thenReturn(mContenResolver);
		when(model.getId()).thenReturn(2L);
		when(query.getQuery()).thenReturn(sqlQuery);
		sourceSpy = spy(source);
		doReturn(uriMock).when(sourceSpy).getContentUri();
		doReturn(uriMock).when(sourceSpy).getContentUriByID();
		doCallRealMethod().when(sourceSpy).create(any());
		doCallRealMethod().when(sourceSpy).update(any());
		doCallRealMethod().when(sourceSpy).recover(anyLong());
		doCallRealMethod().when(sourceSpy).recover(query);
		doCallRealMethod().when(sourceSpy).delete(any());
	}

	@Test
	public void create() {
		when(mContenResolver.insert(any(), any())).thenReturn(uriMock);

		Recipe recipe = sourceSpy.create(model);

		assertNotNull(recipe);
		assertEquals(new Long(2L), recipe.getId());
	}

	@Test
	public void recover() {
		Cursor cursorMock = mock(Cursor.class);
		Recipe validRecipe = createValidModel();
		createCursoMock(cursorMock, validRecipe);
		when(mContenResolver.query(any(), isNull(), any(), any(), isNull())).thenReturn(cursorMock);

		Recipe recipe = sourceSpy.recover(2L);

		assertNotNull(recipe);
		assertEquals(new Long(2L), recipe.getId());
	}

	@Test
	public void recover_query() {
		Cursor cursorMock = mock(Cursor.class);
		Recipe validRecipe = createValidModel();
		createCursoMock(cursorMock, validRecipe);

		List<Recipe> validRecipeList = new ArrayList<>();
		validRecipeList.add(validRecipe);
		when(mContenResolver.query(any(), isNull(), any(), any(), isNull())).thenReturn(cursorMock);

		List recipeListTest = sourceSpy.recover(query);

		assertNotNull(recipeListTest);
		assertEquals(validRecipeList.size(), recipeListTest.size());
		assertEquals(validRecipeList.get(0), recipeListTest.get(0));
	}

	@Test
	public void update() {
		when(mContenResolver.update(any(), any(), any(), any())).thenReturn(1);

		Recipe recipe = sourceSpy.update(model);

		assertNotNull(recipe);
		assertEquals(model, recipe);
		verify(sourceSpy).wrapperContent(model);
	}

	@Test
	public void delete() {
		when(mContenResolver.delete(any(), any(), any())).thenReturn(1);

		Recipe recipe = sourceSpy.delete(model);

		assertNotNull(recipe);
		assertEquals(model, recipe);
		verify(model, times(2)).getId();
	}

	@Test
	public void updateThrowError() {
		when(mContenResolver.update(any(), any(), any(), any())).thenReturn(0);

		try {
			sourceSpy.update(model);
		} catch (Exception e) {
			assertEquals("Não foi atualizado nenhuma linha na tabela", e.getMessage());
			assertEquals(RuntimeException.class.getSimpleName(), e.getClass().getSimpleName());
		}
	}

	@Test
	public void deleteThrowError() {
		when(mContenResolver.delete(any(), any(), any())).thenReturn(0);

		try {
			sourceSpy.delete(model);
		} catch (Exception e) {
			assertEquals("Não foi possível localizar id: 2", e.getMessage());
			assertEquals(RuntimeException.class.getSimpleName(), e.getClass().getSimpleName());
		}
	}

	@NonNull
	private Recipe createValidModel() {
		Recipe validRecipe = new Recipe();
		validRecipe.setId(2L);
		validRecipe.setIdFromAPI("123");
		validRecipe.setImage("image");
		validRecipe.setNome("Nome");
		validRecipe.setServing("Serving");
		validRecipe.setIngredientList(null);
		validRecipe.setStepList(null);
		return validRecipe;
	}

	private void createCursoMock(Cursor cursorMock, Recipe validRecipe) {
		when(cursorMock.moveToFirst()).thenReturn(true);
		when(cursorMock.moveToNext()).thenReturn(false);

		when(cursorMock.getColumnIndex(BakingContract.RecipeEntry._ID)).thenReturn(0);
		when(cursorMock.getColumnIndex(BakingContract.RecipeEntry.COLUMN_ID_FROM_API)).thenReturn(1);
		when(cursorMock.getColumnIndex(BakingContract.RecipeEntry.COLUMN_IMAGE)).thenReturn(2);
		when(cursorMock.getColumnIndex(BakingContract.RecipeEntry.COLUMN_NAME)).thenReturn(3);
		when(cursorMock.getColumnIndex(BakingContract.RecipeEntry.COLUMN_SERVING)).thenReturn(4);

		when(cursorMock.getLong(0)).thenReturn(validRecipe.getId());
		when(cursorMock.getString(1)).thenReturn(validRecipe.getIdFromAPI());
		when(cursorMock.getString(2)).thenReturn(validRecipe.getImage());
		when(cursorMock.getString(3)).thenReturn(validRecipe.getNome());
		when(cursorMock.getString(4)).thenReturn(validRecipe.getServing());
	}
}