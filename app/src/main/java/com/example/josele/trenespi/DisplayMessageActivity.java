package com.example.josele.trenespi;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.view.Menu;
//import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import java.util.concurrent.ExecutionException;

import javax.xml.transform.Result;


public class DisplayMessageActivity extends ActionBarActivity {
  //  private Socket socket;
    private static  String SERVERPORT ;
    private static  String SERVER_IP;
    private clientThread conexionCL=null;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView =(TextView) findViewById(R.id.info);


       Button boton = (Button) findViewById(R.id.sender);
    //    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // con este no peta :S pero funciona igual que sin ello.
       // getActionBar().setDisplayHomeAsUpEnabled(true); //peta y funciona sin ello. Tal vez por que no cambiamos de layout
        Intent intent = getIntent();
        SERVER_IP = intent.getStringExtra(MainActivity.IP_MESSAGE);
        SERVERPORT = intent.getStringExtra(MainActivity.PORT_MESSAGE);

        textView.setTextSize(25);
        textView.setText(SERVER_IP + ":" + SERVERPORT);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText boxsend= (EditText) findViewById(R.id.boxsend);
                TextView textView2= (TextView) findViewById(R.id.receive);
                textView2.setTextSize(10);
                Toast.makeText(getApplicationContext(),
                        "Sent", Toast.LENGTH_LONG).show();

                if (boxsend.getText().toString().trim().length() > 0) {
                    conexionCL = new clientThread();
                    conexionCL.execute(SERVER_IP, SERVERPORT, boxsend.getText().toString());
                    boxsend.setText("");

                    try {
                        textView2.setText(conexionCL.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter Server IP and Port", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    @Override
    protected void onPause(){
    super.onPause();
        try {
            if( (conexionCL!=null)&&conexionCL.close())
                Toast.makeText(getApplicationContext(),
                        "The Socket died", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
        conexionCL = new clientThread(SERVER_IP,SERVERPORT);
        Thread t=new Thread(conexionCL);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textView2.setTextSize(40);
        textView2.setText(conexionCL.getRecivido());
*/




    @Override
    protected void onStop() {
        super.onStop();
        try {
           if( (conexionCL!=null)&&conexionCL.close())
            Toast.makeText(getApplicationContext(),
                    "The Socket died", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_display_message, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==android.R.id.home)
            NavUtils.navigateUpFromSameTask(this);
        return super.onOptionsItemSelected(item);
    }
}


  class clientThread extends AsyncTask< String,Integer , String> {
      /*private  int SERVERPORT ;
      private  String SERVER_IP;
  */    private Socket socket = null;


      protected String mensaje;
      private String recivido;

/*
      public String getRecivido() {
          return recivido;
      }

      public void setEnviado(String enviado) {
          this.mensaje = enviado;
      }*/



      public boolean close() throws IOException {
          if (!(socket.isConnected()&socket.isConnected())) {
              return false;
          }
          socket.close();
          return true;
      }

      @Override
      protected String doInBackground(String... params) {
            if(!params[0].isEmpty()&&!params[1].isEmpty()&&!params[2].isEmpty()) {
                this.mensaje = params[2];
                try {
                    InetAddress serverAddr = InetAddress.getByName(params[0]);
                    socket = new Socket(serverAddr, Integer.parseInt(params[1]));
                    PrintWriter salida = new PrintWriter(
                            new OutputStreamWriter(socket.getOutputStream()), true);
                    BufferedReader entrada = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    salida.print((char) mensaje.length());


                    salida.println(mensaje);
                    recivido = entrada.readLine();

                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();

                }
            }
          return recivido;
      }
@Override
protected void onPostExecute(String parm){

}

}
