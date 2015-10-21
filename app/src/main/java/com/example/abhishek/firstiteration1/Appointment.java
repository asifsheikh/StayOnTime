package com.example.abhishek.firstiteration1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek on 9/9/2015.
 */
public class Appointment extends Activity {
    Login log=new Login();

    String[] branch={"Choose...","Computer Science","Mechanical","Electrical","Pharma","MBA"};

    final List<String> list=new ArrayList<String>();
    Spinner s,s1,s2;
    ArrayAdapter<String> adp1,adp2,adp3;
    String text="";
    String selected="";
    String booked="";
    int in=0;
    ProgressDialog progress;
    AlertDialog.Builder alertDialog2;
    String line="" ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("bef", "hey0");
        setContentView(R.layout.appointment);
        final Button refresh=(Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
               refresh();



            }
        } );

        s1 = (Spinner) findViewById(R.id.Branch);

        adp2=new ArrayAdapter<String>(Appointment.this,
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
              else{
                  new Connect1().execute();
              }

          }

          @Override
          public void onNothingSelected(AdapterView<?> parentView) {
              // your code here
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
                in=0;
                if(selected.equals("Choose..."))
                {

                }
                else{
                new Connect().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    public void refresh()
    {
        new Connect().execute();
    }
    public void booked(){
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Object item = parentView.getItemAtPosition(position);
                booked = s.getSelectedItem().toString();
                if(booked.equals("Choose..."))
                {

                }
                else{
                    new Connect2().execute();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    class Connect1 extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("bef", "heypre");
            progress = new ProgressDialog(Appointment.this);
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

                s2= (Spinner) findViewById(R.id.professor);

                adp3=new ArrayAdapter<String>(Appointment.this,
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
    class Connect2 extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("inside", "i am");
            progress = new ProgressDialog(Appointment.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.show();
            //alertDialog2= new AlertDialog.Builder(Appointment.this);
        }
        @Override
        protected String doInBackground(String... params) {


          //  String[] professor = {"Empty","Empty","Empty"};
            String link="http://firstiteration.net16.net/booked.php";
            String data  = null;
            try {
                Log.d("inside", "i am");
                data = URLEncoder.encode("slot", "UTF-8") + "=" + URLEncoder.encode(booked, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                data += "&" + URLEncoder.encode("prof_id", "UTF-8") + "=" + URLEncoder.encode(selected, "UTF-8");
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
                Log.d("inside", "i am");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


                line =reader.readLine();

             //   int i=0;
             ///   Log.d("bef","hey3");
              //  for ( line = reader.readLine(); line != null; line = reader.readLine()) {
                    //System.out.println(line);

                  Log.d("test1","the input is:"+line);
               // }

             /*   while( line=reader.readLine()!=null&&i<3)
                {
                    slot[i++]=line;
                    Log.d("resp","the slot is:"+line);
                  //  line=reader.readLine();



                    // line.replaceAll("\\s+","")

                }*/
                Log.d("resp","out");




            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("bef","hey2");
            return line;




        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            alertDialog2 = new AlertDialog.Builder(
                    Appointment.this);


           // slots();
            progress.dismiss();
            if(line.equals("1"))
            {
                alertDialog2.setTitle("Booked");


                alertDialog2.setMessage("Your Slot has been booked!");





                alertDialog2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                refresh();

                            }
                        });
                alertDialog2.create().show();
            }
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
    class Connect extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("bef", "heypre");
            progress = new ProgressDialog(Appointment.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.show();
            //alertDialog2= new AlertDialog.Builder(Appointment.this);
        }
        @Override
        protected String doInBackground(String... params) {
            String[] slot = {"Choose...","Full Or Not Alloted","Full Or Not Alloted","Full or Not Alloted"};
            String link="http://firstiteration.net16.net/slot.php";
            String data  = null;
            try {
                Log.d("bef","hey1");
                data = URLEncoder.encode("prof_id", "UTF-8") + "=" + URLEncoder.encode(selected, "UTF-8");
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
                    slot[i++]=line;
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

               s = (Spinner) findViewById(R.id.select_slots_student);

                 adp1=new ArrayAdapter<String>(Appointment.this,
                        android.R.layout.simple_spinner_item,slot);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("bef","hey2");

            return line;




        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            s.setAdapter(adp1);
            booked();
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
}
