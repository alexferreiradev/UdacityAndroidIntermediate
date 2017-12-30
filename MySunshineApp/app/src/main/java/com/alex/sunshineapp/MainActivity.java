package com.alex.sunshineapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, true);

        if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new ForecastFragment())
                        .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_view_map:
                String location = PreferenceManager.getDefaultSharedPreferences(this)
                        .getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
                Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                        .appendQueryParameter("q", location)
                        .build();
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(geoLocation);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }else{
                    Log.d(LOG_TAG, "Não existe um app para abrir mapas para a localizacao: "+ location);
                    Toast.makeText(this, "Nenhum app instalado abriu a localização: "+location, Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return true;
    }
}
