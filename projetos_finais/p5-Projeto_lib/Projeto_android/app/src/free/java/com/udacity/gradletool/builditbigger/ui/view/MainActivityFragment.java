package com.udacity.gradletool.builditbigger.ui.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.gradletool.builditbigger.R;
import com.udacity.gradletool.builditbigger.ui.task.LoadJokeTask;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

	public MainActivityFragment() {
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_main, container, false);

		Button tellJokeBT = root.findViewById(R.id.btTellJoke);
		tellJokeBT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tellJoke(v);
			}
		});

		AdView mAdView = root.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.build();
		mAdView.loadAd(adRequest);

		return root;
	}

	public void tellJoke(View view) {
		Toast.makeText(requireContext(), getString(R.string.solicitation_jake_msg), Toast.LENGTH_SHORT).show();
		new LoadJokeTask(requireContext()).execute();
	}
}
