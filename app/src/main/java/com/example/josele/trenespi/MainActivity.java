package com.example.josele.trenespi;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


public class MainActivity extends ActionBarActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    public final static String IP_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String PORT_MESSAGE = "com.mycompany.myfirstapp.MESSAGE2";
    private Button boton;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        boton=(Button)findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                String message = settings.getString("pip", null);
                String message2 =  settings.getString("port", null);
                if ((message!=null)&&(message2!=null)) {

                    intent.putExtra(IP_MESSAGE, message);
                    intent.putExtra(PORT_MESSAGE, message2);


                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter Server IP and Port", Toast.LENGTH_LONG).show();

             }
         }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                //action of search
                return true;
            case R.id.action_settings:
                //action of settings
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }/*
    public void sendMessage(View view) {
        //The Intent represents an app's "intent to do something."
        // You can use intents for a wide variety of tasks, but most often they're used to start another activity
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/


}
