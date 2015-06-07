package com.example.josele.trenespi;


import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SettingsActivity extends ActionBarActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    private EditText serverip,port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button boton = (Button) findViewById(R.id.button);
        serverip = (EditText) findViewById(R.id.iprasp);
        serverip.setText( settings.getString("pip", ""));
        port = (EditText) findViewById(R.id.port);
        port.setText(settings.getString("port", ""));
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((port.getText().toString().trim().length() > 0) && (serverip.getText().toString().trim().length() > 0)) {
                    String message = serverip.getText().toString();
                    String message2 = port.getText().toString();

                    SharedPreferences settings = getApplicationContext().getSharedPreferences(
                            PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("pip", message);
                    editor.putString("port", message2);

                    // Commit the edits!
                    editor.apply();
                    NavUtils.navigateUpFromSameTask(SettingsActivity.this);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter Server IP and Port", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home)
            NavUtils.navigateUpFromSameTask(this);
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }
}
