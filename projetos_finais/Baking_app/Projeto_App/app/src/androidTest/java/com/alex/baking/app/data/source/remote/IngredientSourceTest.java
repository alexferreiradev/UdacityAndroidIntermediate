package com.alex.baking.app.data.source.remote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Ingredient;
import com.alex.baking.app.data.model.MeasureType;
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
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class) // Necessita de JsonObject e Uri do Android
public class IngredientSourceTest {

	@Mock
	private NetworkResource networkResource;

	@Mock
	private Ingredient model;

	@Mock
	private QuerySpecification query;

	@InjectMocks
	private IngredientSource source;
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
		URL urlMock = new URL("http://testehost.com/path/2/?id=1");
		when(query.getQuery()).thenReturn(urlMock);
		when(networkResource.getStringResourceFromURL(any())).thenReturn(validJsonString);

		List<Ingredient> ingredientList = source.recover(query);

		assertNotNull(ingredientList);
		assertEquals(9, ingredientList.size());
		assertEquals(null, ingredientList.get(0).getId());
		assertEquals(MeasureType.CUP, ingredientList.get(0).getMeasure());
		assertEquals(Double.valueOf(0.5), ingredientList.get(2).getQuantity());
		assertEquals("salt", ingredientList.get(3).getIngredient());
	}
}