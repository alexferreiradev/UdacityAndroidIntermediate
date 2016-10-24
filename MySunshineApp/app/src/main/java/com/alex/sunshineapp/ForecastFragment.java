package com.alex.sunshineapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 24/10/2016.
 */

public class ForecastFragment extends Fragment {

    private static final String LOG_TAG = ForecastFragment.class.getSimpleName();
    public static final String POST_CODE = "94043";

    private ForecastListAdapter forecastListAdapter;
    private ListView listView;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        List<String> forecastItems = new ArrayList<>();
        forecastItems.add("Today - Sunny - 88/90");
        forecastItems.add("Tomorrow - Foggy - 70/46");
        forecastItems.add("Weds - Cloudy - 72/63");
        forecastItems.add("Thurs - Cloudy - 72/63");
        forecastItems.add("Fri - Rain - 64/51");

        listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setEmptyView(rootView.findViewById(R.id.empty));
        forecastListAdapter = new ForecastListAdapter(getActivity(), R.layout.list_item_forecast, forecastItems);
        listView.setAdapter(forecastListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getAdapter().getItem(position);
                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
            }
        });
        refreshList();

        return rootView;
    }

    /**
     * Refresh data to ListView
     */
    public void refreshList(){
        ((TextView)listView.getEmptyView()).setText("Carregando dados");
        new FetchWeatherTask().execute(POST_CODE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh_op:
                refreshList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        public static final String QUERY_KEY_PARAM = "q";
        public static final String UNITS_KEY_PARAM = "units";
        public static final String CNT_KEY_PARAM = "cnt";
        public static final String APPID_KEY_PARAM = "appid";
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
        private final String HTTP_API_OPENWEATHERMAP_DAILY_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily";

        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0)
                return null;

            String postcode = params[0];
            if (postcode == null || postcode.isEmpty() || Integer.valueOf(postcode) == null) {
                String msg = "Parâmetro para task está nulo, vazio ou não é um inteiro";
                Log.e(LOG_TAG, msg);
                throw new NullPointerException(msg);
            }

            String mode = "json";
            String units = "metric";
            int numberDays = 7;
            String appID = BuildConfig.OPEN_WEATHER_MAP_API_KEY;

            Uri builtUri = Uri.parse(HTTP_API_OPENWEATHERMAP_DAILY_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_KEY_PARAM, postcode)
                    .appendQueryParameter(UNITS_KEY_PARAM, units)
                    .appendQueryParameter(CNT_KEY_PARAM, String.valueOf(numberDays))
                    .appendQueryParameter(APPID_KEY_PARAM,appID)
                    .build();

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                Log.v(LOG_TAG, "URL: " + builtUri.toString());
                URL url =  new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
                Log.i(LOG_TAG, forecastJsonStr);
                return getWeatherDataFromJson(forecastJsonStr, numberDays);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error when retrieve the JSON", e);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Erro when parse the JSON", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        /* The date/time conversion code is going to be moved outside the asynctask later,
         * so for convenience we're breaking it out into its own method now.
         */
        private String getReadableDateString(long time){
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }

        /**
         * Prepare the weather high/lows for presentation.
         */
        private String formatHighLows(double high, double low) {
            // For presentation, assume the user doesn't care about tenths of a degree.
            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.

            Time dayTime = new Time();
            dayTime.setToNow();

            // we start at the day returned by local time. Otherwise this is a mess.
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

            // now we work exclusively in UTC
            dayTime = new Time();

            String[] resultStrs = new String[numDays];
            for(int i = 0; i < weatherArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String day;
                String description;
                String highAndLow;

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);

                // The date/time is returned as a long.  We need to convert that
                // into something human-readable, since most people won't read "1400356800" as
                // "this saturday".
                long dateTime;
                // Cheating to convert this to UTC time, which is what we want anyhow
                dateTime = dayTime.setJulianDay(julianStartDay+i);
                day = getReadableDateString(dateTime);

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                double high = temperatureObject.getDouble(OWM_MAX);
                double low = temperatureObject.getDouble(OWM_MIN);

                highAndLow = formatHighLows(high, low);
                resultStrs[i] = day + " - " + description + " - " + highAndLow;
            }

            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
            }
            return resultStrs;

        }

        @Override
        protected void onPostExecute(String results[]) {
            super.onPostExecute(results);

            if (results == null || results.length == 0){
                TextView emptyView = (TextView) listView.getEmptyView();
                String msg = "Não foi encontrado nenhum resultado";
                emptyView.setText(msg);
                return ;
            }

            List<String> forecastItems = Arrays.asList(results);
            forecastListAdapter.clear();
            forecastListAdapter.addAll(forecastItems);
        }
    }
}
