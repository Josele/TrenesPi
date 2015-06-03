package com.example.josele.trenespi;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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


public class DisplayMessageActivity extends ActionBarActivity {
  //  private Socket socket;
    private static  int SERVERPORT ;
    private static  String SERVER_IP;
    private clientThread conexionCL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView =(EditText) findViewById(R.id.info);
        TextView textView2=(EditText) findViewById(R.id.receive);
    //    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // con este no peta :S pero funciona igual que sin ello.
       // getActionBar().setDisplayHomeAsUpEnabled(true); //peta y funciona sin ello. Tal vez por que no cambiamos de layout
        Intent intent = getIntent();
        SERVER_IP = intent.getStringExtra(MainActivity.IP_MESSAGE);
        SERVERPORT = Integer.parseInt(intent.getStringExtra(MainActivity.PORT_MESSAGE));

        textView.setTextSize(40);
        textView.setText(SERVER_IP + ":" + SERVERPORT);
        setContentView(textView);

        conexionCL = new clientThread(SERVER_IP,SERVERPORT);
        Thread t=new Thread(conexionCL);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textView.setTextSize(40);
        textView.setText(conexionCL.getRecivido());
        setContentView(textView2);


    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            conexionCL.close();
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


class clientThread implements Runnable {
    private static  int SERVERPORT ;
    private static  String SERVER_IP;
    private static Socket socket = null;
    private static String recivido;
    public clientThread(String SERVER_IP,int SERVERPORT){
        this.SERVER_IP=SERVER_IP;
        this.SERVERPORT=SERVERPORT;


    }

    public static String getRecivido() {
        return recivido;
    }

    public void close() throws IOException {
if (socket.isConnected()) {

    socket.close();
}

 }
    public void run() {


        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, SERVERPORT);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()),true);
            salida.println("train speed 10");

            recivido=entrada.readLine();
        } catch (UnknownHostException e1) {

            e1.printStackTrace();

        } catch (IOException e1) {
            e1.printStackTrace();

        }



    }


}
