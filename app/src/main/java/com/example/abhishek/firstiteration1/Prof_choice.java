package com.example.abhishek.firstiteration1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.abhishek.firstiteration1.Activity.AboutActivity;
import com.example.abhishek.firstiteration1.Activity.HelpActivity;
import com.example.abhishek.firstiteration1.Activity.MapsActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;

/**
 * Created by Abhishek on 9/17/2015.
 */
public class Prof_choice extends Activity {
    private DatePicker dpResult;
    private Button btnChangeDate;

    private int year;
    private int month;
    private int day;
    private int new_year;
    private int new_month;
    private int new_day;
String date="";
    String prof_name="";
    static final int DATE_DIALOG_ID = 999;
    AlertDialog.Builder alertDialog2;
    ProgressDialog progress;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_choice);
        final Button slot=(Button) findViewById(R.id.provide_slots);
        final Button view_message=(Button) findViewById(R.id.view_message);
        final Button view_slot=(Button) findViewById(R.id.view_slot);
        final Button video=(Button) findViewById(R.id.video_p);
        final Button maps = (Button) findViewById(R.id.maps_p);
        final Button help = (Button) findViewById(R.id.help_p);
        final Button about = (Button) findViewById(R.id.about_p);
        Bundle b = getIntent().getExtras();
         prof_name = b.getString("Prof_name");
        Log.d("proof", prof_name);
        slot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);

            }
        });


        maps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent maps = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(maps);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent maps = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(maps);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent maps = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(maps);
            }
        });

       view_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent view_message = new Intent("com.example.abhishek.firstiteration1.PROF_VIEW_MESSAGE");
                Bundle b1 = new Bundle();
                b1.putString("Prof_name",prof_name);
                view_message.putExtras(b1);
                startActivity(view_message);
                // final Intent prof_choice = new Intent("com.example.abhishek.firstiteration.PROF_SLOT");
                //startActivity(prof_choice);
            }
        });
        view_slot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent view_message = new Intent("com.example.abhishek.firstiteration1.VIEW_BOOKED");
                Bundle b1 = new Bundle();
                b1.putString("Prof_name",prof_name);
                view_message.putExtras(b1);
                startActivity(view_message);
                // final Intent prof_choice = new Intent("com.example.abhishek.firstiteration.PROF_SLOT");
                //startActivity(prof_choice);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent view_message = new Intent("com.example.abhishek.firstiteration1.ActivityLogin");
                Bundle b1 = new Bundle();
                b1.putString("Prof_name",prof_name);
                view_message.putExtras(b1);
                startActivity(view_message);
                // final Intent prof_choice = new Intent("com.example.abhishek.firstiteration.PROF_SLOT");
                //startActivity(prof_choice);
            }
        });



    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                Calendar c1 = Calendar.getInstance();
                // set date picker as current date
                Calendar c2 = Calendar.getInstance();
                c1.add(Calendar.DATE, 7);

                DatePickerDialog d= new DatePickerDialog(this, datePickerListener,
                        year, month,day);
                d.getDatePicker().setMaxDate(c1.getTimeInMillis());
                d.getDatePicker().setMinDate(c2.getTimeInMillis());
                return d;
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            new_year = selectedYear;
            new_month = selectedMonth+1;
            new_day = selectedDay;
            new Connect().execute();
            StringBuilder sb = new StringBuilder();
            sb.append(new_year+"-"+new_month+"-"+new_day);
            // set selected date into textview
            date=  sb.toString();

            // set selected date into datepicker also
           // dpResult.init(year, month, day, null);

        }
    };
    class Connect extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("bef", "heypre");
            progress = new ProgressDialog(Prof_choice.this);
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.show();
            //alertDialog2= new AlertDialog.Builder(Appointment.this);
        }
        @Override
        protected String doInBackground(String... params) {
            //String[] slot = {"Empty","Empty","Empty"};
            String link="http://firstiteration.net16.net/add_slot.php";
            String data  = null;
            try {
                Log.d("bef","hey1");
                data = URLEncoder.encode("prof_id", "UTF-8") + "=" + URLEncoder.encode(prof_name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


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

                int i=0;
                Log.d("bef","hey3");
               // for ( line = reader.readLine(); line != null; line = reader.readLine()) {
                    //System.out.println(line);
                    //slot[i++]=line;
                   Log.d("thakur","the output is:"+line);
              //  }

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
            progress.dismiss();
            alertDialog2 = new AlertDialog.Builder(
                    Prof_choice.this);
            if(result.equals("0"))
            {
                alertDialog2.setTitle("Full");


                alertDialog2.setMessage("Only Three Slots Allowed!");





                alertDialog2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog


                            }
                        });
                alertDialog2.create().show();
            }
            if(result.equals("1")){
                //Log.d("notsame", "Not there");
                alertDialog2.setTitle("Added");


                alertDialog2.setMessage("Slot Added!");





                alertDialog2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog


                            }
                        });
                alertDialog2.create().show();
            }
            if(result.equals("2")){
                //Log.d("notsame", "Not there");
                alertDialog2.setTitle("Already Added");


                alertDialog2.setMessage("Please select another slot!");





                alertDialog2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog


                            }
                        });
                alertDialog2.create().show();
            }
        }
    }
}
