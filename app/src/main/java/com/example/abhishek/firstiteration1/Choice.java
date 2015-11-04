package com.example.abhishek.firstiteration1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.abhishek.firstiteration1.Activity.AboutActivity;
import com.example.abhishek.firstiteration1.Activity.HelpActivity;
import com.example.abhishek.firstiteration1.Activity.MapsActivity;

/**
 * Created by Abhishek on 9/21/2015.
 */
public class Choice extends Activity {
    String stu_name="";
    Bundle b1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
       stu_name = b.getString("Prof_name");
        setContentView(R.layout.choice);
        final Button appointment=(Button) findViewById(R.id.appointments);
        final Button maps_button = (Button) findViewById(R.id.Maps);
        final Button message=(Button) findViewById(R.id.message_student);
        final Button help = (Button) findViewById(R.id.help);
        final Button about = (Button) findViewById(R.id.about);
        final Button video=(Button) findViewById(R.id.video);
         b1 = new Bundle();
        b1.putString("Prof_name",stu_name);
        maps_button.setOnClickListener(new View.OnClickListener() {
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
        appointment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent appointment = new Intent("com.example.abhishek.firstiteration1.APPOINTMENT");

                startActivity(appointment);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent send_message = new Intent("com.example.abhishek.firstiteration1.SEND_MESSAGE");

                send_message.putExtras(b1);
                startActivity(send_message);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent send_message = new Intent("com.example.abhishek.firstiteration1.ActivityLogin");

                send_message.putExtras(b1);
                startActivity(send_message);
            }
        });


    }
}
