package com.udacity.gradletool.builditbigger.ui.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.alex.example.jokeactivity.JokeActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.lang.ref.WeakReference;

public class LoadJokeTask extends AsyncTask<String, Integer, String> {
	private static final String TAG = LoadJokeTask.class.getSimpleName();

	private static final String JOKE_ENDPOINT_URL = "http://localhost:80/sayJoke";
	private WeakReference<Context> context;

	public LoadJokeTask(Context context) {
		this.context = new WeakReference<>(context);
	}

	@Override
	protected String doInBackground(String... strings) {
		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(JOKE_ENDPOINT_URL)
					.build();
			Response response = client.newCall(request).execute();
			ResponseBody body = response.body();
			assert body != null;

			return body.string();
		} catch (Exception e) {
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
