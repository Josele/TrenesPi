package com.example.josele.trenespi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";
    public final static String IP_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String PORT_MESSAGE = "com.mycompany.myfirstapp.MESSAGE2";
    private Button boton;
    private EditText serverip,port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boton = (Button) findViewById(R.id.button);
        serverip = (EditText) findViewById(R.id.iprasp);
        serverip.setText( settings.getString("pip", ""));
        port = (EditText) findViewById(R.id.port);
        port.setText( settings.getString("port", ""));
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((port.getText().toString().trim().length() > 0) && (serverip.getText().toString().trim().length() > 0)) {
                    Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                    String message = serverip.getText().toString();
                    String message2 = port.getText().toString();
                    intent.putExtra(IP_MESSAGE, message);
                    intent.putExtra(PORT_MESSAGE, message2);
                    SharedPreferences settings = getApplicationContext().getSharedPreferences(
                            PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("pip", message);
                    editor.putString("port", message2);

                    // Commit the edits!
                    editor.apply();

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
