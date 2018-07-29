package com.alex.baking.app.data.source.remote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.R;
import com.alex.baking.app.data.model.Step;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class) // Necessita de JsonObject e Uri do Android
public class StepSourceTest {

	@Mock
	private NetworkResource networkResource;

	@Mock
	private Step model;

	@Mock
	private QuerySpecification query;

	@InjectMocks
	private StepSource source;
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

		List<Step> stepList = source.recover(query);

		assertNotNull(stepList);
		assertEquals(7, stepList.size());
		assertNull(stepList.get(0).getId());
		assertEquals("0", stepList.get(0).getIdFromAPI());
		assertEquals("1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.", stepList.get(1).getDescription());
		assertEquals("Prep the cookie crust.", stepList.get(2).getShortDescription());
		assertEquals("", stepList.get(3).getThumbnailURL());
		assertEquals("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4", stepList.get(6).getVideoURL());
	}
}