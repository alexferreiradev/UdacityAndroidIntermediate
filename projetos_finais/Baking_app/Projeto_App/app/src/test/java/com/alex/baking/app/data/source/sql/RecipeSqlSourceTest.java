package com.alex.baking.app.data.source.sql;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Ignore // TODO: 26/07/18 - Não comitar, dont push this
public class RecipeSqlSourceTest {

	@Mock
	private QuerySpecification query;
	@Mock
	private Recipe model;

	@Mock(name = "resolver")
	private ContentResolver mContenResolver;

	@Mock
	private Context context;

	@InjectMocks
	private RecipeSqlSource source;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(context.getContentResolver()).thenReturn(mContenResolver);
		source = new RecipeSqlSource(context); // Workaround para mock de contentResolver
	}

	@Test
	public void create() {
//		Uri uri = new Uri.Builder().build();
		Uri uriMock = mock(Uri.class);
		when(uriMock.getQueryParameter("id")).thenReturn("2");
		when(context.getContentResolver()).thenReturn(mContenResolver);
		when(mContenResolver.insert(any(), any())).thenReturn(uriMock);
		when(model.getId()).thenReturn(2L);
		Recipe recipe = source.create(model);

		assertNotNull(recipe);
		assertEquals(new Long(2L), recipe.getId());
	}

	@Test
	public void recover() {
	}

	@Test
	public void recover_query() {
	}

	@Test
	public void update() {
	}

	@Test
	public void delete() {
	}
}