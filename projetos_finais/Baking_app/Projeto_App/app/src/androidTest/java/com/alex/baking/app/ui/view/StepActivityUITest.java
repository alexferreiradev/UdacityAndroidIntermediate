package com.alex.baking.app.ui.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class StepActivityUITest {

	@Rule
	public IntentsTestRule<MainActivity> intentMain = new IntentsTestRule<>(MainActivity.class, true, false); // Nao inicia a activity pois precisa criar intent antes
	@Rule
	public IntentsTestRule<RecipeActivity> intentRecipe = new IntentsTestRule<>(RecipeActivity.class, true, false); // Nao inicia a activity pois precisa criar intent antes
	@Rule
	public IntentsTestRule<StepActivity> intent = new IntentsTestRule<>(StepActivity.class, true, false); // Nao inicia a activity pois precisa criar intent antes

	@Test
	public void onSelectStep_showStepVideo() {
		intentMain.launchActivity(new Intent());
		MainActivity activity = intentMain.getActivity();
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		onView(withId(R.id.rvRecipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(
				2, ViewActions.click()
		));
		onView(withId(R.id.svRecipe)).perform(ViewActions.swipeUp());
		onView(withId(R.id.svRecipe)).perform(ViewActions.swipeUp());
		onView(withId(R.id.rvStep)).check(ViewAssertions.matches(isDisplayed()));
		onView(withId(R.id.rvStep)).perform(RecyclerViewActions.actionOnItemAtPosition(
				2, ViewActions.click()
		));
		onView(withId(R.id.pvStep)).check(ViewAssertions.matches(isDisplayed()));
	}

	@Test
	public void onStepActivity_selectNextStep_show() {

	}

	@Test
	public void onStepActivity_selectPlayVideo_show() {
	}

	@Test
	public void onStepActivity_selectPauseVideo_show() {
	}

	@Test
	public void onStepActivity_selectBack_showRecipeActivity() {
		intentMain.launchActivity(new Intent());
		onView(isRoot()).check(matches(isDisplayed()));
		onView(withId(R.id.rvRecipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
		onView(withId(R.id.svRecipe)).perform(ViewActions.swipeUp());
		onView(withId(R.id.flRecipeContainer)).perform(ViewActions.swipeUp());
		onView(withId(R.id.rvStep)).perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
		onView(withId(R.id.flStepContainer)).check(matches(isDisplayed()));
		onView(isRoot()).perform(ViewActions.pressBack());
		onView(withId(R.id.flRecipeContainer)).check(matches(isDisplayed()));
	}
}