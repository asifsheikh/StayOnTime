package com.example.abhishek.firstiteration1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
 * Created by Abhishek on 9/8/2015.
 */
public class Login extends Activity{

    String user;
    String pass;
    MainChoice in= new MainChoice();
    AlertDialog.Builder alertDialog2;
    String line = "";
    ProgressDialog progress;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        final EditText username=(EditText) findViewById(R.id.userid);
        final EditText password=(EditText) findViewById(R.id.password);
        final Button login=(Button) findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                user=username.getText().toString();
                pass=password.getText().toString();


                new Connect().execute();
            }
        } );


    }

    class Connect extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(Login.this);
            progress.setTitle("Loggin in");
            progress.setMessage("Logging in...");
            progress.show();
             alertDialog2= new AlertDialog.Builder(Login.this);
        }
        @Override
        protected String doInBackground(String... params) {

            String link="http://firstiteration.net16.net/get.php";
            String data  = null;
            try {

                data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                String tag= String.valueOf(in.tag);
                Log.d("tag",tag);
                data += "&" + URLEncoder.encode("tag", "UTF-8") + "=" + URLEncoder.encode(tag, "UTF-8");
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


                line =reader.readLine();
                //Log.d("before",line);

                //Log.d("resp", sb.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

              return line;



        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if(result!=null)
            {
                // line.replaceAll("\\s+","")
                if (in.tag==1) {


                    final Intent choice = new Intent("com.example.abhishek.firstiteration1.CHOICE");
                    Bundle b = new Bundle();
                    b.putString("Prof_name",result);
                    choice.putExtras(b);
                    startActivity(choice);

                }
                if (in.tag==0)
                {
                    final Intent prof_choice = new Intent("com.example.abhishek.firstiteration1.PROF_CHOICE");
                    Bundle b = new Bundle();
                    b.putString("Prof_name",result);
                    prof_choice.putExtras(b);
                    startActivity(prof_choice);
                }

            }
            else{
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
            }
        }
    }

}


