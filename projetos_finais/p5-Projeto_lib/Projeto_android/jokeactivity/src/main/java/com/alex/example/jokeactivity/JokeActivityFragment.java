package com.alex.example.jokeactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class JokeActivityFragment extends Fragment {

	TextView jokeText;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_joke, container, false);

		jokeText = view.findViewById(R.id.tvJokeText);

		String jokeByArgument = readJokeFromArguments();

		jokeText.setText(jokeByArgument);

		return view;
	}

	private String readJokeFromArguments() {
		FragmentActivity activity = getActivity();
		assert activity != null;
		Bundle arguments = activity.getIntent().getExtras();
		assert arguments != null;

		return arguments.getString(JokeActivity.JOKE_EXTRA_KEY, getString(R.string.joke_error_found));
	}
}
