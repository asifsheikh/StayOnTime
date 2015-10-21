package com.example.abhishek.firstiteration1;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
 * Created by Abhishek on 9/22/2015.
 */
public class View_booked extends ListActivity {
    AlertDialog.Builder alertDialog2;
    String line = "";
    String prof_name="";
    ProgressDialog progress;
    String[] message={"empty","empty","empty"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.prof_view_message);
        Bundle b = getIntent().getExtras();
        prof_name = b.getString("Prof_name");

        new Connect().execute();
    }
    class Connect extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(View_booked.this);
            progress.setTitle("Retreiving Info");
            progress.setMessage("Please wait...");
            progress.show();
            alertDialog2= new AlertDialog.Builder(View_booked.this);
        }
        @Override
        protected String doInBackground(String... params) {

            String link="http://firstiteration.net16.net/view_Booked.php";
            String data  = null;
            try {

                data = URLEncoder.encode("prof_name", "UTF-8") + "=" + URLEncoder.encode(prof_name, "UTF-8");
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

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


                // line =reader.readLine();
                Log.d("before", line);
                int i=0;
                for ( line = reader.readLine(); line != null; line = reader.readLine()) {
                    //System.out.println(line);
                    message[i++]=line;
                    Log.d("resp","the slot is:"+line);
                }
                //Log.d("resp", sb.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return line;



        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            setListAdapter(new ArrayAdapter<String>(View_booked.this, R.layout.professor_view_message, message));

            ListView listView = getListView();

            listView.setTextFilterEnabled(true);
            progress.dismiss();

        }
    }
}
