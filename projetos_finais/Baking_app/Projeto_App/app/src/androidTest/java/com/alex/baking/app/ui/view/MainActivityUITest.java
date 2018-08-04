package com.alex.baking.app.ui.view;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;

@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {

	@Rule
	public ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);
//	@Rule
//	public IntentsTestRule<MainActivity> intent = new IntentsTestRule<>(MainActivity.class);

	@Test
	public void onMainActivity_selectRecipe2_showRecipe2() {
		// seleciona view ou dado
		// realiza acao ou verificacao
		// verifica resultado
		// Recycle view é diferente de listView. ListView usa onData() e recycle onView
		onView(ViewMatchers.withId(R.id.rvRecipeList))
				.perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
		onView(ViewMatchers.withId(R.id.flRecipeContainer)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
	}
}