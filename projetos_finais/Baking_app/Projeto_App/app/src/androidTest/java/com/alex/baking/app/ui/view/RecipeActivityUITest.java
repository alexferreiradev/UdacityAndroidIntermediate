package com.alex.baking.app.ui.view;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.R;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
		onView(withId(R.id.rvIngredient)).check(ViewAssertions.matches(isDisplayed()));
		onView(withId(R.id.rvStep)).check(ViewAssertions.matches(isDisplayed()));
	}

	@Ignore // Não esta carregando steps e ingredients no repo
	@Test
	public void onRecipeActivity_selectStep2_showStep2() {
		// seleciona view ou dado
		// realiza acao ou verificacao
		// verifica resultado
		// Recycle view é diferente de listView. ListView usa onData() e recycle onView
		intentMain.launchActivity(new Intent());
		onView(withId(R.id.rvRecipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(
				2, ViewActions.click()
		));
		onView(withId(R.id.rvStep))
				.perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
		onView(withId(R.id.flStepContainer)).check(ViewAssertions.matches(isDisplayed()));
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
		// TODO: 04/08/18  
	}

	@Test
	public void onRecipeActivity_selectPauseVideo_show() {
		// TODO: 04/08/18
	}

	@Test
	public void onRecipeActivity_selectBack_showMainActivity() {
		intentMain.launchActivity(new Intent());
		onView(withId(R.id.rvRecipeList)).perform(RecyclerViewActions.actionOnItemAtPosition(
				2, ViewActions.click()
		));
		onView(withId(R.id.flRecipeContainer)).perform(ViewActions.pressBack());
		onView(withId(R.id.rvRecipeList)).check(ViewAssertions.matches(isDisplayed()));
	}
}