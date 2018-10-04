package com.udacity.gradletool.builditbigger.ui.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.alex.example.jokeactivity.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class LoadJokeTask extends AsyncTask<String, Integer, String> {
	private static final String TAG = LoadJokeTask.class.getSimpleName();

	private static final String JOKE_ENDPOINT_URL = "http://localhost:80/sayJoke";
	private WeakReference<Context> context;
	private MyApi myApiService;

	public LoadJokeTask(Context context) {
		this.context = new WeakReference<>(context);
	}

	@Override
	protected String doInBackground(String... strings) {
		if (myApiService == null) {
			MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
					new AndroidJsonFactory(), null)
					// options for running against local devappserver
					// - 10.0.2.2 is localhost's IP address in Android emulator
					// - turn off compression when running against local devappserver
					.setRootUrl("http://10.0.2.2:8080/_ah/api/")
					.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
						@Override
						public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
							abstractGoogleClientRequest.setDisableGZipContent(true);
						}
					});

			myApiService = builder.build();
		}

		try {
			return myApiService.sayJoke().execute().getData();
		} catch (IOException e) {
			Log.e(TAG, "Erro ao solicitar joke para endpoint: " + e.getMessage());
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result == null || result.isEmpty()) {
			Toast.makeText(context.get(), "Não foi possivel carregar uma piada", Toast.LENGTH_SHORT).show();
			return;
		}

		Intent intent = new Intent(context.get(), JokeActivity.class);
		intent.putExtra(JokeActivity.JOKE_EXTRA_KEY, result);

		context.get().startActivity(intent);
	}
}
