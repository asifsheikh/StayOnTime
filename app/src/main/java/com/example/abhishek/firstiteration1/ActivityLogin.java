package com.example.abhishek.firstiteration1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.List;

import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBSettings;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.videochat.core.QBVideoChatController;


import org.jivesoftware.smack.XMPPException;

public class ActivityLogin extends Activity {

    private ProgressDialog progressDialog;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initChatService();

        // setup UI
        //
        setContentView(R.layout.login_layout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);

        findViewById(R.id.loginByFirstUserBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                createSession(SplashScreen.FIRST_USER_LOGIN, SplashScreen.FIRST_USER_PASSWORD);
            }
        });

        findViewById(R.id.loginBySecondUserBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                createSession(SplashScreen.SECOND_USER_LOGIN, SplashScreen.SECOND_USER_PASSWORD);
            }
        });
    }

    private void createSession(String login, final String password) {
        QBAuth.createSession(login, password, new QBEntityCallbackImpl<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {

                // Save current user
                //
               SplashScreen app = new SplashScreen();
                app.setCurrentUser(qbSession.getUserId(), password);
id=qbSession.getUserId();
                // Login to Chat
                //
                QBChatService.getInstance().login(app.getCurrentUser(), new QBEntityCallbackImpl() {
                    @Override
                    public void onSuccess() {
                        try {
                            QBVideoChatController.getInstance().initQBVideoChatMessageListener();
                        } catch (XMPPException e) {
                            e.printStackTrace();
                        }
                        // show next activity
                        showCallUserActivity();
                    }

                    @Override
                    public void onError(List errors) {
                        Toast.makeText(ActivityLogin.this, "Error when login", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onError(List<String> errors) {
                progressDialog.dismiss();
                Toast.makeText(ActivityLogin.this, "Error when login, check test users login and password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initChatService(){
        QBChatService.setDebugEnabled(true);

        if (!QBChatService.isInitialized()) {
            Log.d("ActivityLogin", "InitChat");
            QBChatService.init(this);
        }else{
            Log.d("ActivityLogin", "InitChat not needed");
        }
    }

    private void showCallUserActivity() {
        progressDialog.dismiss();

        Intent intent = new Intent(this, ActivityVideoChat.class);
        Bundle b = new Bundle();
        b.putInt("id",id);
       intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}