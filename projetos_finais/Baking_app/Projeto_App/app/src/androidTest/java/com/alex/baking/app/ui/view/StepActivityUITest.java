package com.alex.baking.app.ui.view;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.alex.baking.app.R;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class StepActivityUITest {

	@Rule
	public IntentsTestRule<MainActivity> intentMain = new IntentsTestRule<>(MainActivity.class, true, false); // Nao inicia a activity pois precisa criar intent antes
	@Rule
	public IntentsTestRule<RecipeActivity> intentRecipe = new IntentsTestRule<>(RecipeActivity.class, true, false); // Nao inicia a activity pois precisa criar intent antes
	@Rule
	public IntentsTestRule<StepActivity> intent = new IntentsTestRule<>(StepActivity.class, true, true); // Nao inicia a activity pois precisa criar intent antes

	@Test
	public void onStepActivity_selectNextStep_show() {
		// TODO: 04/08/18 precisa de dados para testar se trocou os textos mostrados
	}

	@Test
	public void onStepActivity_selectPlayVideo_show() {
		// TODO: 04/08/18  
	}

	@Test
	public void onStepActivity_selectPauseVideo_show() {
		// TODO: 04/08/18
	}

	@Ignore // Adicionar chamada de recipe e clicar em step para depois fazer o teste
	@Test
	public void onStepActivity_selectBack_showRecipeActivity() {
		onView(withId(R.id.flStepContainer))
				.perform(ViewActions.pressBack());
		onView(withId(R.id.flRecipeContainer)).check(matches(isDisplayed()));
	}
}