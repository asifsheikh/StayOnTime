package com.example.abhishek.firstiteration1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.quickblox.core.QBSettings;
import com.quickblox.users.model.QBUser;

public class SplashScreen extends AppCompatActivity {
    public static final int FIRST_USER_ID = 5525099;
    public static final String FIRST_USER_LOGIN = "abithakur";
    public static final String FIRST_USER_PASSWORD = "aquacasa123!";
    //
    public static final int SECOND_USER_ID = 5548037;
    public static final String SECOND_USER_LOGIN = "Prof";
    public static final String SECOND_USER_PASSWORD = "password123!";
    private QBUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        QBSettings.getInstance().fastConfigInit("28635", "JmTSGKN2pf9Uh4O", "GHJZQ6MYwkXF864");
       // System.out.println("hey");
        Thread timer =new Thread(){
            public void run()
            {
                try{

                      sleep(3000);

                    Intent main=new Intent("com.example.abhishek.firstiteration1.MAINCHOICE");
                    startActivity(main);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                            finish();
                }
            }
        };
        timer.start();

    }
    public void setCurrentUser(int userId, String userPassword) {
        this.currentUser = new QBUser(userId);
        this.currentUser.setPassword(userPassword);
    }

    public QBUser getCurrentUser() {
        return currentUser;
    }


}
