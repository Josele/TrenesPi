package com.example.josele.trenespi;


import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


//import android.view.Menu;
//import android.support.v7.app.ActionBarActivity;


public class DisplayMessageActivity extends AppCompatActivity {
    private static String SERVERPORT;
    private static String SERVER_IP;
    private static int Train_selected=0;


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

        bar.setMax(54);

        bar.setOnSeekBarChangeListener(Sender_seekbar);

        SeekBar bar2 = (SeekBar)findViewById(R.id.seekBar2); // make seekbar object
       bar2.setMax(54);
        bar2.setOnSeekBarChangeListener(Sender_seekbar);
        Button Barrier_chang = (Button) findViewById(R.id.slbarrier);

        Button Cross_chang = (Button) findViewById(R.id.slcross);
        Button Stop_t = (Button) findViewById(R.id.sls);
        Button Secure_mode = (Button) findViewById(R.id.slant);
        Button Abv = (Button) findViewById(R.id.abv);
        Button Sound = (Button) findViewById(R.id.sound);
        Intent intent = getIntent();
        SERVER_IP = intent.getStringExtra(MainActivity.IP_MESSAGE);
        SERVERPORT = intent.getStringExtra(MainActivity.PORT_MESSAGE);
        textView.setTextSize(25);
        textView.setText(SERVER_IP + ":" + SERVERPORT);

        Cross_chang.setOnClickListener(Sender_string);
        Barrier_chang.setOnClickListener(Sender_string);
        Stop_t.setOnClickListener(Sender_string);
        Secure_mode.setOnClickListener(Sender_string);
        Abv.setOnClickListener(Sender_string);
        Sound.setOnClickListener(Sender_string);












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
           // conexionCL = new clientThread();
             //conexionCL.execute(SERVER_IP, SERVERPORT, message);
              message="http://"+SERVER_IP+":"+SERVERPORT+"/answer/android?comando="+message;
              //RequestTask conexionHttp;
               new RequestTask(){
                  protected void onPostExecute(String result) {

                      Toast.makeText(getApplicationContext(),
                              "connected \n Server response: "+result, Toast.LENGTH_LONG).show();
                      // here you have access to the context in which execute was called in first place.
                      // You'll have to mark all the local variables final though..
                  }
              }.execute(message);
             // String answer;

           /* try {

                if ((answer=conexionHttp.get()) == null) {

                    Toast.makeText(getApplicationContext(),"Unconnected", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "connected \n Server response: "+answer, Toast.LENGTH_LONG).show();

                }
                //     textView2.setText(conexionCL.get());

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }*/
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
                    value = seekBar.getProgress()-28;
                    valueview=(TextView)findViewById(R.id.txrenfe);
                    valueview.setText(Integer.toString(value));

                    break;
                case R.id.seekBar2:
                    value = seekBar.getProgress()-28;
                    valueview=(TextView)findViewById(R.id.txdiesel);
                    valueview.setText(Integer.toString(value));


                    break;}

        }


        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
           int value;
            String message=null;
            TextView colorTrain;
            Button sonido;
            Button abv;
            int sdk = android.os.Build.VERSION.SDK_INT;
            switch ( seekBar.getId()){
            case R.id.seekBar:
                value = seekBar.getProgress()-28;

                sendCmd("train+select+3");
                message="train+speed+"+ Integer.toString(value);
                colorTrain = (TextView) findViewById(R.id.idtrain);
                sonido =(Button) findViewById(R.id.sound);
                abv =(Button) findViewById(R.id.abv);

                colorTrain.setBackgroundResource(R.color.yellow_train);

                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    sonido.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshapeyellow));
                    abv.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshapeyellow));
                } else {
                    sonido.setBackground(getResources().getDrawable(R.drawable.buttonshapeyellow));
                    abv.setBackground(getResources().getDrawable(R.drawable.buttonshapeyellow));
                }

          Train_selected=3;
               // Toast.makeText(getApplicationContext(),"New speed for renfe", Toast.LENGTH_SHORT).show();
            break;
            case R.id.seekBar2:
                value = seekBar.getProgress()-28;

                sendCmd("train+select+4");
                message="train+speed+"+ Integer.toString(value);
                colorTrain = (TextView) findViewById(R.id.idtrain);
                sonido =(Button) findViewById(R.id.sound);
                abv =(Button) findViewById(R.id.abv);
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    sonido.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshapeblue));
                    abv.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshapeblue));

                } else {
                    sonido.setBackground(getResources().getDrawable(R.drawable.buttonshapeblue));
                    abv.setBackground(getResources().getDrawable(R.drawable.buttonshapeblue));
                }
                colorTrain.setBackgroundResource(R.color.blue_train);
                Train_selected=4;

              //  Toast.makeText(getApplicationContext(),"New speed for diesel" +"", Toast.LENGTH_SHORT).show();

            break;}
            if (message!=null)
            sendCmd(message);
        }
    };
    final View.OnClickListener Sender_string = new View.OnClickListener() {
        public void onClick(final View v) {
            String message=null;

            TextView boxstatus;
            Button temp;
            //Inform the user the button has been clicked
            switch(v.getId()) {

                case R.id.slcross:
                    boxstatus = (TextView) findViewById(R.id.Crossview);

                    if(boxstatus.getText().toString()=="L")
                    {  message="changer+set+0";
                        boxstatus.setText("R");
                    }
                        else {
                        boxstatus.setText("L");

                        message = "changer+set+1";
                    }
                       // Toast.makeText(getApplicationContext(), "Cross changed", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.slbarrier:
                    boxstatus = (TextView) findViewById(R.id.Barrierview);

                        if(boxstatus.getText().toString()=="UP")
                        {  message="barrier+set+0";
                            boxstatus.setText("DOWN");
                        }
                        else {
                            boxstatus.setText("UP");
                            message = "barrier+set+1";
                        }
                    //Toast.makeText(getApplicationContext(), "Barrier changed", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.slant:
                    boxstatus = (TextView) findViewById(R.id.txanti);

                        if(boxstatus.getText().toString()=="Enable")
                        {  message="anti+enable+0";
                           // sendCmd("anti+cancel");
                            boxstatus.setText("Disable");
                        }
                        else {
                            boxstatus.setText("Enable");
                            message = "anti+enable+1";
                        }


                    break;
                case R.id.sls:



                            message = "s";

                    //Toast.makeText(getApplicationContext(), "emergency stop", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.abv:

                    if(Train_selected==4||Train_selected==3) {
                        sendCmd("train+select+"+Train_selected);
                        message = "function+set+4";
                    }else

                     Toast.makeText(getApplicationContext(), "There is not train selected", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.sound:
                    boxstatus = (TextView) findViewById(R.id.Crossview);

                    if(Train_selected==4||Train_selected==3)
                    {
                        sendCmd("train+select+"+Train_selected);
                        message = "function+set+3";
                    }
                    else
                     Toast.makeText(getApplicationContext(), "There is not train selected", Toast.LENGTH_SHORT).show();

                    break;
            }

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
        /*try {
            if ((conexionCL != null) && conexionCL.close())
                Toast.makeText(getApplicationContext(),
                        "The Socket died", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
protected void onResume() {

    super.onResume();
    TextView colorTrain;
    Button sonido;
    Button abv;
    if(Train_selected==3){
        colorTrain = (TextView) findViewById(R.id.idtrain);
        sonido =(Button) findViewById(R.id.sound);
        abv =(Button) findViewById(R.id.abv);
        colorTrain.setBackgroundResource(R.color.yellow_train);
        sonido.setBackgroundResource(R.color.yellow_button);
        abv.setBackgroundResource(R.color.yellow_button);

    }else if(Train_selected==4){
        colorTrain = (TextView) findViewById(R.id.idtrain);
        sonido =(Button) findViewById(R.id.sound);
        abv =(Button) findViewById(R.id.abv);
        colorTrain.setBackgroundResource(R.color.blue_train);
        sonido.setBackgroundResource(R.color.blue_button);
        abv.setBackgroundResource(R.color.blue_button);


    }
        /*try {
            if ((conexionCL != null) && conexionCL.close())
                Toast.makeText(getApplicationContext(),
                        "The Socket died", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
}

    @Override
    protected void onStop() {
        super.onStop();
      /*  try {
            if ((conexionCL != null) && conexionCL.close())
                Toast.makeText(getApplicationContext(),
                        "The Socket died", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
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




class RequestTask extends AsyncTask<String, String, String>{

    @Override
    protected String doInBackground(String... uri) {

        URL url ;
        HttpURLConnection urlConnection = null;
        String myString=null;
        try {
            url = new URL(uri[0]);
             urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        myString= IOUtils.toString(in, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        } finally
             {

                 if (urlConnection != null) {
                     urlConnection.disconnect();
                 }


             }
         return myString;
        /**
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }   */

    }


}