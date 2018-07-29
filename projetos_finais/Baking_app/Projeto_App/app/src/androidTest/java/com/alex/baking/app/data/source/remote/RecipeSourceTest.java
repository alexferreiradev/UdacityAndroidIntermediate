package com.alex.baking.app.data.source.remote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Recipe;
import com.alex.baking.app.data.source.queryspec.QuerySpecification;
import com.alex.baking.app.data.source.remote.network.NetworkResource;
import com.alex.baking.app.data.source.remote.network.exception.ConnectionException;
import com.alex.baking.app.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class) // Necessita de JsonObject e Uri do Android
public class RecipeSourceTest {

	@Mock
	private NetworkResource networkResource;

	@Mock
	private Recipe model;

	@Mock
	private QuerySpecification query;

	@InjectMocks
	private RecipeSource source;

	private Context context;

	@Before
	public void setUp() {
		context = InstrumentationRegistry.getInstrumentation().getTargetContext();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void recover_query() throws ConnectionException, IOException {
		InputStream inputStream = context.getResources().openRawResource(R.raw.valid_recipe_file);
		String validJsonString = Util.readStream(inputStream);
		when(networkResource.getStringResourceFromURL(any())).thenReturn(validJsonString);

		List<Recipe> recipeList = source.recover(query);

		assertNotNull(recipeList);
		assertEquals(4, recipeList.size());
		assertNull(recipeList.get(0).getId());
		assertNull(recipeList.get(0).getIngredientList());
		assertNull(recipeList.get(0).getStepList());
		assertEquals("2", recipeList.get(1).getIdFromAPI());
		assertEquals("", recipeList.get(2).getImage());
		assertEquals("Cheesecake", recipeList.get(3).getNome());
		assertEquals("8", recipeList.get(2).getServing());
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