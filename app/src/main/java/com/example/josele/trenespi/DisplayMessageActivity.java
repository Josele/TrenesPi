package com.example.josele.trenespi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;


public class DisplayMessageActivity extends ActionBarActivity {
  //  private Socket socket;
    private static  String SERVERPORT ;
    private static  String SERVER_IP;
    private clientThread conexionCL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
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
                Toast.makeText(getApplicationContext(),
                        "Sent", Toast.LENGTH_LONG).show();

                if (boxsend.getText().toString().trim().length() > 0) {
                    conexionCL = new clientThread();
                    conexionCL.execute(SERVER_IP,SERVERPORT,boxsend.getText().toString());

                    textView2.setTextSize(10);
                    try {
                        textView2.setText(conexionCL.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter Server IP and Port", Toast.LENGTH_LONG).show();

                }
            }
        });

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
           if( conexionCL.close())
            Toast.makeText(getApplicationContext(),
                    "The Socket died", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        return super.onOptionsItemSelected(item);
    }
}


  class clientThread extends AsyncTask< String,Integer , String> {
      /*private  int SERVERPORT ;
      private  String SERVER_IP;
  */    private Socket socket = null;


      private String mensaje;
      private String recivido;

/*
      public String getRecivido() {
          return recivido;
      }

      public void setEnviado(String enviado) {
          this.mensaje = enviado;
      }*/

      /**
       * Try to close a socket of the thread
       * @return True when the sockets has been closed
       * @throws IOException
       */
      public boolean close() throws IOException {
          if (socket.isConnected()) {
              socket.close();
              return true;
          }
return false;
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
                } catch (UnknownHostException e1) {

                    e1.printStackTrace();

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
