package com.example.josele.trenespi;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.andexert.expandablelayout.library.ExpandableLayoutListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;

//import android.view.Menu;
//import android.support.v7.app.ActionBarActivity;


public class DisplayMessageActivity extends AppCompatActivity {
    //  private Socket socket;
    private static String SERVERPORT;
    private static String SERVER_IP;
    private clientThread conexionCL = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
/*        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.view_row, R.id.header_train, array);

        final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.listview);


        expandableLayoutListView.setAdapter(arrayAdapter);
*/
        TextView textView = (TextView) findViewById(R.id.info);
      //  Button boton = (Button) findViewById(R.id.sender);

        TextView expandableLayout =(TextView)findViewById(R.id.header_train);
        TextView expandableLayout2 =(TextView)findViewById(R.id.header_changer);
        TextView expandableLayout3 =(TextView)findViewById(R.id.header_other);
        expandableLayout.setOnClickListener(close_expa);
        expandableLayout2.setOnClickListener(close_expa);
        expandableLayout3.setOnClickListener(close_expa);
       SeekBar bar = (SeekBar)findViewById(R.id.seekBar); // make seekbar object
        bar.setMax(28);
        bar.setOnSeekBarChangeListener(Sender_seekbar);

        SeekBar bar2 = (SeekBar)findViewById(R.id.seekBar2); // make seekbar object
       bar2.setMax(28);
        bar2.setOnSeekBarChangeListener(Sender_seekbar);
        Button Barrier_chang = (Button) findViewById(R.id.slbarrier);

        Button Cross_chang = (Button) findViewById(R.id.slcross);
        Button Stop_t = (Button) findViewById(R.id.sls);
        Button Secure_mode = (Button) findViewById(R.id.slant);
        Intent intent = getIntent();
        SERVER_IP = intent.getStringExtra(MainActivity.IP_MESSAGE);
        SERVERPORT = intent.getStringExtra(MainActivity.PORT_MESSAGE);
        textView.setTextSize(25);
        textView.setText(SERVER_IP + ":" + SERVERPORT);

        Cross_chang.setOnClickListener(Sender_string);
        Barrier_chang.setOnClickListener(Sender_string);
        Stop_t.setOnClickListener(Sender_string);
        Secure_mode.setOnClickListener(Sender_string);

/*

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText boxsend = (EditText) findViewById(R.id.boxsend);
                TextView textView2 = (TextView) findViewById(R.id.receive);
                textView2.setTextSize(10);


                if (boxsend.getText().toString().trim().length() > 0) {
                    conexionCL = new clientThread();
                    conexionCL.execute(SERVER_IP, SERVERPORT, boxsend.getText().toString());
                    boxsend.setText("");

                    try {
                        if (conexionCL.get() == null) {

                            Toast.makeText(getApplicationContext(),
                                    "Unconnected", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Sent", Toast.LENGTH_LONG).show();

                        }
                        textView2.setText(conexionCL.get());

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "None Command", Toast.LENGTH_LONG).show();

                }
            }
        });
*/
    }
    public void sendCmd(String message){
        if (message!=null&&message.trim().length() > 0) {
            conexionCL = new clientThread();
            conexionCL.execute(SERVER_IP, SERVERPORT, message);


            try {
                if (conexionCL.get() == null) {

                    Toast.makeText(getApplicationContext(),
                            "Unconnected", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Sent", Toast.LENGTH_LONG).show();

                }
                //     textView2.setText(conexionCL.get());

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "None Command", Toast.LENGTH_LONG).show();

        }


    }
    final SeekBar.OnSeekBarChangeListener Sender_seekbar = new SeekBar.OnSeekBarChangeListener() {


        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value;
            TextView valueview;
            switch ( seekBar.getId()){
                case R.id.seekBar:
                    value = seekBar.getProgress();
                    valueview=(TextView)findViewById(R.id.txrenfe);
                    valueview.setText(Integer.toString(value));

                    break;
                case R.id.seekBar2:
                    value = seekBar.getProgress();
                    valueview=(TextView)findViewById(R.id.txdiesel);
                    valueview.setText(Integer.toString(value));


                    break;}

        }


        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
           int value;
            String message=null;
            TextView colorTrain;
            switch ( seekBar.getId()){
            case R.id.seekBar:
                value = seekBar.getProgress();

                sendCmd("train select 3");
                message="train speed "+ Integer.toString(value);
                 colorTrain = (TextView) findViewById(R.id.idtrain);


                colorTrain.setBackgroundResource(R.color.yellow_train);

                Toast.makeText(getApplicationContext(),"New speed for renfe", Toast.LENGTH_SHORT).show();
            break;
            case R.id.seekBar2:
                value = seekBar.getProgress();

                sendCmd("train select 4");
                message="train speed "+ Integer.toString(value);
                 colorTrain = (TextView) findViewById(R.id.idtrain);


                colorTrain.setBackgroundResource(R.color.blue_train);

                Toast.makeText(getApplicationContext(),"New speed for diesel" +
                        "", Toast.LENGTH_SHORT).show();

            break;}
            if (message!=null)
            sendCmd(message);
        }
    };
    final View.OnClickListener Sender_string = new View.OnClickListener() {
        public void onClick(final View v) {
            String message=null;
            EditText boxsend=null;
            TextView boxstatus;
            //Inform the user the button has been clicked
            switch(v.getId()) {

                case R.id.slcross:
                    boxstatus = (TextView) findViewById(R.id.Crossview);

                    if(boxstatus.getText().toString()=="L")
                    {  message="changer set 0";
                        boxstatus.setText("R");
                    }
                        else {
                        boxstatus.setText("L");

                        message = "changer set 1";
                    }
                        Toast.makeText(getApplicationContext(), "Cross changed", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.slbarrier:
                    boxstatus = (TextView) findViewById(R.id.Barrierview);

                        if(boxstatus.getText().toString()=="UP")
                        {  message="barrier set 0";
                            boxstatus.setText("DOWN");
                        }
                        else {
                            boxstatus.setText("UP");
                            message = "barrier set 1";
                        }
                    Toast.makeText(getApplicationContext(), "Barrier changed", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.slant:
                    boxstatus = (TextView) findViewById(R.id.txanti);

                        if(boxstatus.getText().toString()=="Enable")
                        {  message="anti enable 0";
                            sendCmd("anti cancel");
                            boxstatus.setText("Disable");
                        }
                        else {
                            boxstatus.setText("Enable");
                            message = "anti enable 1";
                        }


                    break;
                case R.id.sls:



                            message = "s";

                    Toast.makeText(getApplicationContext(), "emergency stop", Toast.LENGTH_SHORT).show();

                    break;
            }
            if (boxsend!=null)
               boxsend.setText("");
            sendCmd(message);
                }

    };


    final View.OnClickListener close_expa = new View.OnClickListener() {
        public void onClick(final View v) {
            ExpandableLayout closeLayout;
            ExpandableLayout expandableLayout;
            switch (v.getId()) {
                case R.id.header_train:
                    closeLayout = (ExpandableLayout) findViewById(R.id.second);
                    closeLayout.hide();
                    closeLayout = (ExpandableLayout) findViewById(R.id.third);
                    closeLayout.hide();
                    expandableLayout = (ExpandableLayout) findViewById(R.id.first);
                    if(expandableLayout.isOpened())
                        expandableLayout.hide();
                    else
                        expandableLayout.show();


                    break;
                case R.id.header_changer:

                    closeLayout = (ExpandableLayout) findViewById(R.id.first);
                    closeLayout.hide();
                    closeLayout = (ExpandableLayout) findViewById(R.id.third);
                    closeLayout.hide();
                 expandableLayout = (ExpandableLayout) findViewById(R.id.second);
                    if(expandableLayout.isOpened())
                        expandableLayout.hide();
                    else
                        expandableLayout.show();



                    break;
                case R.id.header_other:
                    closeLayout = (ExpandableLayout) findViewById(R.id.first);
                    closeLayout.hide();
                    closeLayout = (ExpandableLayout) findViewById(R.id.second);
                    closeLayout.hide();
                    expandableLayout = (ExpandableLayout) findViewById(R.id.third);
                    if(expandableLayout.isOpened())
                        expandableLayout.hide();
                    else
                        expandableLayout.show();



                    break;
            }

        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        try {
            if ((conexionCL != null) && conexionCL.close())
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
            if ((conexionCL != null) && conexionCL.close())
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

        if (id == android.R.id.home)
            NavUtils.navigateUpFromSameTask(this);
        return super.onOptionsItemSelected(item);
    }
}


class clientThread extends AsyncTask<String, Integer, String> {
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
        if ((socket==null)||  (!socket.isConnected())) {
            return false;
        }
        socket.close();
        return true;
    }

    @Override
    protected String doInBackground(String... params) {
        if (!params[0].isEmpty() && !params[1].isEmpty() && !params[2].isEmpty()) {
            this.mensaje = params[2];
            try {recivido="Reception isn't working";
                //InetAddress serverAddr = InetAddress.getByName(params[0]);
                SocketAddress sockaddr = new InetSocketAddress(params[0], Integer.parseInt(params[1]));
                Socket socket = new Socket();
                socket.connect(sockaddr, 1000);
                //socket = new Socket(serverAddr, Integer.parseInt(params[1]));
                PrintWriter salida = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream()), true);
                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                salida.print((char) mensaje.length());


                salida.println(mensaje);
                recivido = entrada.readLine();

                socket.close();
            } catch (IOException e1) {

                return null;
            }
        }
        return recivido;
    }

    @Override
    protected void onPostExecute(String parm) {

    }

}
