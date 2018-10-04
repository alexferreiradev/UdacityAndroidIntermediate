package com.udacity.gradletool.builditbigger.ui.view;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import com.udacity.gradletool.builditbigger.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityFragmentUITest {

	@Rule
	public IntentsTestRule<MainActivity> intentMain = new IntentsTestRule<>(MainActivity.class, true, true);

	@Test
	public void onMain_clickJokeBt_showJokeTextInFragment() {
		onView(withId(R.id.btTellJoke)).check(ViewAssertions.matches(isDisplayed()));
		onView(withId(R.id.btTellJoke)).perform(ViewActions.click());
		onView(withId(R.id.tvJokeText)).check(ViewAssertions.matches(isDisplayed()));
		onView(withId(R.id.tvJokeText)).check(ViewAssertions.matches(ViewMatchers.withText("Joker: This is totally a funny joke")));
	}
}