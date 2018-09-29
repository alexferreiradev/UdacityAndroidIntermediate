package com.alex.baking.app.ui.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.R;
import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityUITest {

	@Rule
	public IntentsTestRule<MainActivity> intentMain = new IntentsTestRule<>(MainActivity.class, true, false); // Nao inicia a activity pois precisa criar intent antes
	@Rule
	public IntentsTestRule<RecipeActivity> intent = new IntentsTestRule<>(RecipeActivity.class, true, false); // Nao inicia a activity pois precisa criar intent antes

	@Test
	public void onloadActivity_showIngredientListAndStepList() {
		intentMain.launchActivity(new Intent());
		onView(withId(R.id.rvRecipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(
				2, ViewActions.click()
		));
		onView(withId(R.id.rvIngredient)).check(matches(isDisplayed()));
		onView(withId(R.id.rvStep)).check(matches(isDisplayed()));
	}

	@Test
	public void onRecipeActivity_selectStep2_showStep2() {
		// seleciona view ou dado
		// realiza acao ou verificacao
		// verifica resultado
		// Recycle view é diferente de listView. ListView usa onData() e recycle onView
		intentMain.launchActivity(new Intent());
		MainActivity activity = intentMain.getActivity();
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		onView(isRoot()).check(matches(isDisplayed()));
		onView(withId(R.id.rvRecipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
		onView(withId(R.id.svRecipe)).perform(ViewActions.swipeUp());
		onView(withId(R.id.flRecipeContainer)).perform(ViewActions.swipeUp());
		onView(withId(R.id.rvStep)).perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
		onView(withId(R.id.flStepContainer)).check(matches(isDisplayed()));
		onView(AllOf.allOf(withId(R.id.tvShortDescription), isDescendantOfA(withId(R.id.flStepContainer))))
				.check(matches(withText("Combine dry ingredients.")));
	}

	@Test
	public void onRecipeActivity_nextStepIsNotshowing() {
		intentMain.launchActivity(new Intent());
		onView(withId(R.id.rvRecipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(
				2, ViewActions.click()
		));
		onView(withId(R.id.btNextStep)).check(ViewAssertions.doesNotExist());
	}

	@Test
	public void onRecipeActivity_selectPlayVideo_show() {
		intentMain.launchActivity(new Intent());
		MainActivity activity = intentMain.getActivity();
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		onView(isRoot()).check(matches(isDisplayed()));
		onView(withId(R.id.rvRecipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
		onView(withId(R.id.svRecipe)).perform(ViewActions.swipeUp());
		onView(withId(R.id.flRecipeContainer)).perform(ViewActions.swipeUp());
		onView(withId(R.id.rvStep)).perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
		onView(withId(R.id.flStepContainer)).check(matches(isDisplayed()));
	}

	@Test
	public void onRecipeActivity_selectPauseVideo_show() {
		intentMain.launchActivity(new Intent());
		MainActivity activity = intentMain.getActivity();
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		onView(isRoot()).check(matches(isDisplayed()));
		onView(withId(R.id.rvRecipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(
				2, ViewActions.click()
		));
		onView(withId(R.id.svRecipe)).perform(ViewActions.swipeUp());
		onView(withId(R.id.flRecipeContainer)).perform(ViewActions.swipeUp());
		onView(withId(R.id.rvStep))
				.perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
		onView(withId(R.id.flStepContainer)).check(matches(isDisplayed()));
		onView(withId(R.id.exo_content_frame)).perform(ViewActions.click());
		onView(withId(R.id.exo_pause)).perform(ViewActions.click());
	}

	@Test
	public void onRecipeActivity_selectBack_showMainActivity() {
		intentMain.launchActivity(new Intent());
		onView(withId(R.id.rvRecipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(
				2, ViewActions.click()
		));
		onView(isRoot()).perform(ViewActions.pressBack());
		onView(withId(R.id.rvRecipeList)).check(matches(isDisplayed()));
	}
}