package com.example.abhishek.firstiteration1;

import android.app.Application;

import com.quickblox.core.QBSettings;
import com.quickblox.users.model.QBUser;

public class VideoChatApplication extends Application {

    public static final int FIRST_USER_ID = 65421;
    public static final String FIRST_USER_LOGIN = "abithakur";
    public static final String FIRST_USER_PASSWORD = "";
    //
    public static final int SECOND_USER_ID = 65422;
    public static final String SECOND_USER_LOGIN = "Prof";
    public static final String SECOND_USER_PASSWORD = "";

    private QBUser currentUser;

    @Override
    public void onCreate() {
        super.onCreate();

        // Set QuickBlox credentials here
        //
        QBSettings.getInstance().fastConfigInit("28635", "JmTSGKN2pf9Uh4O", "GHJZQ6MYwkXF864");
    }

    public void setCurrentUser(int userId, String userPassword) {
        this.currentUser = new QBUser(userId);
        this.currentUser.setPassword(userPassword);
    }

    public QBUser getCurrentUser() {
        return currentUser;
    }
}
