package com.example.abhishek.firstiteration1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by Abhishek on 9/8/2015.
 */
public class MainChoice extends Activity{

    public static int tag=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_choice);
       final RadioButton prof=(RadioButton) findViewById(R.id.professor);
       final RadioButton student=(RadioButton) findViewById(R.id.student);
        final Intent login=new Intent("com.example.abhishek.firstiteration1.LOGIN");
        prof.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                tag=0;
                startActivity(login);
                finish();
               student.setChecked(false);

            }
        } );
       student.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                tag=1;
                startActivity(login);

                finish();
               prof.setChecked(false);

            }
        } );
    }



}
