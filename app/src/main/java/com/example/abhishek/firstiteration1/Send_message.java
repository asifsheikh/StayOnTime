package com.example.abhishek.firstiteration1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Abhishek on 9/21/2015.
 */
public class Send_message extends Activity {
    String[] branch={"Choose...","Computer Science","Mechanical","Electrical","Pharma","MBA"};
    Spinner s,s1,s2;
    ArrayAdapter<String> adp1,adp2,adp3;
    String text="";
    ProgressDialog progress;
    String selected="";
    String stu_name="";
    String message="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        stu_name = b.getString("Prof_name");


        Log.d("bef", "hey0");
        setContentView(R.layout.send_message);
       s1 = (Spinner) findViewById(R.id.branch1);
        adp2=new ArrayAdapter<String>(Send_message.this,
                android.R.layout.simple_spinner_item,branch);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adp2);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Object item = parentView.getItemAtPosition(position);

                text = s1.getSelectedItem().toString();
               if(text.equals("Choose..."))
               {

               }
                else {


                   new Connect1().execute();
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        final Button send_message=(Button) findViewById(R.id.send1_message);
        final EditText Message=(EditText) findViewById(R.id.editText);

        send_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                message = Message.getText().toString();


                new Connect().execute();
            }
        });

    }
    public void slots()
    {
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Object item = parentView.getItemAtPosition(position);
                selected = s2.getSelectedItem().toString();

             //   if(selected.equals("Choose..."))
              //  {

             //   }
                //else{
                //    new Connect().execute();
               // }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    class Connect extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            progress = new ProgressDialog(Send_message.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.show();
            //alertDialog2= new AlertDialog.Builder(Appointment.this);
        }
        @Override
        protected String doInBackground(String... params) {
            //String[] slot = {"Choose...","Full Or Not Alloted","Full Or Not Alloted","Full or Not Alloted"};
            String link="http://firstiteration.net16.net/send_message.php";
            String data  = null;
            try {
                Log.d("phew",selected);
                data = URLEncoder.encode("prof_id", "UTF-8") + "=" + URLEncoder.encode(selected, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                data += "&" + URLEncoder.encode("stu_name", "UTF-8") + "=" + URLEncoder.encode(stu_name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            StringBuilder sb = new StringBuilder();
            URL url = null;
            try {
                url = new URL(link);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            URLConnection conn = null;
            try {
                conn = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            conn.setDoOutput(true);
            try {
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String line="" ;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


                 line =reader.readLine();

                int i=1;
                Log.d("bef","hey3");
              //  for ( line = reader.readLine(); line != null; line = reader.readLine()) {
                    //System.out.println(line);
                  //  slot[i++]=line;
                 //   Log.d("resp","the slot is:"+line);
                //}

             /*   while( line=reader.readLine()!=null&&i<3)
                {
                    slot[i++]=line;
                    Log.d("resp","the slot is:"+line);
                  //  line=reader.readLine();



                    // line.replaceAll("\\s+","")

                }*/
                Log.d("resp","out");

               // s = (Spinner) findViewById(R.id.select_slots_student);

                //adp1=new ArrayAdapter<String>(Send_message.this,
                 //       android.R.layout.simple_spinner_item,slot);
                //adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("bef","hey2");

            return line;




        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);


           // s.setAdapter(adp1);
            //booked();
            progress.dismiss();
            /*else{
                Log.d("notsame", "Not there");
                alertDialog2.setTitle("Wrong credentials");


                alertDialog2.setMessage("Wrong Username or Password.Try again!");





                alertDialog2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog


                            }
                        });
                alertDialog2.create().show();
            }*/
        }
    }
    class Connect1 extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("bef", "heypre");
            progress = new ProgressDialog(Send_message.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.show();
// To dismiss the dialog

            //alertDialog2= new AlertDialog.Builder(Appointment.this);
        }
        @Override
        protected String doInBackground(String... params) {


            String[] professor = {"Choose...","Empty","Empty","Empty"};
            String link="http://firstiteration.net16.net/name.php";
            String data  = null;
            try {
                Log.d("bef","hey1");
                data = URLEncoder.encode("branch", "UTF-8") + "=" + URLEncoder.encode(text, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            StringBuilder sb = new StringBuilder();
            URL url = null;
            try {
                url = new URL(link);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            URLConnection conn = null;
            try {
                conn = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            conn.setDoOutput(true);
            try {
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String line="" ;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


                // line =reader.readLine();

                int i=1;
                Log.d("bef","hey3");
                for ( line = reader.readLine(); line != null; line = reader.readLine()) {
                    //System.out.println(line);
                    professor[i++]=line;
                    Log.d("resp","the slot is:"+line);
                }

             /*   while( line=reader.readLine()!=null&&i<3)
                {
                    slot[i++]=line;
                    Log.d("resp","the slot is:"+line);
                  //  line=reader.readLine();



                    // line.replaceAll("\\s+","")

                }*/
                Log.d("resp","out");

                s2= (Spinner) findViewById(R.id.professor1);

                adp3=new ArrayAdapter<String>(Send_message.this,
                        android.R.layout.simple_spinner_item,professor);
                adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("bef","hey2");
            return line;




        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            s2.setAdapter(adp3);
            progress.dismiss();
            slots();
            /*else{
                Log.d("notsame", "Not there");
                alertDialog2.setTitle("Wrong credentials");


                alertDialog2.setMessage("Wrong Username or Password.Try again!");





                alertDialog2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog


                            }
                        });
                alertDialog2.create().show();
            }*/
        }
    }
}
