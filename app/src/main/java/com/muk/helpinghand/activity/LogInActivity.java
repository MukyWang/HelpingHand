package com.muk.helpinghand.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.muk.helpinghand.MainActivity;
import com.muk.helpinghand.R;
import com.muk.helpinghand.utils.ProgressGenerator;

/**
 * Created by Zelta on 31/05/16.
 */
public class LogInActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {
    private TextView etEmail, etPasswrod,tvReset;
    public static String uid, uEmail;
    private Firebase firebase;
    final static String DB_URL = "https://handupdb.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_login);

        etEmail = (TextView) findViewById(R.id.etEmail);
        etPasswrod = (TextView) findViewById(R.id.etPd);
        tvReset = (TextView) findViewById(R.id.tvReset);
        Button btRegister = (Button) findViewById(R.id.btReg);

        //get firebase instance
        Firebase.setAndroidContext(this);

        //when register button clicked, navigate to Register activity
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
            }
        });

        //Use PorgressGenerator and android process generator library
        //Initial login button
        final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        final ActionProcessButton btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        //Set process model as endless modle
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = etEmail.getText().toString();
                String pwd = etPasswrod.getText().toString();

                firebase = new Firebase(DB_URL);
                //Before log in, unauthorized current first incase when user would like to log out or relogin
                firebase.unauth();
                //User auth with email and password
                firebase.authWithPassword(email, pwd, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        //When auth success, start progress bar with sign in button
                        progressGenerator.start(btnSignIn);
                        btnSignIn.setEnabled(true);
                        //Get user UID and Email
                        uid = authData.getUid();
                        uEmail = email;
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast toast = Toast.makeText(LogInActivity.this, firebaseError.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0 ,0);
                        toast.show();
                    }
                });



            }
        });

        //Navigate to reset password activity
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, ResetPwdActivity.class));
            }
        });


    }

    @Override
    public void onComplete() {
        //After auth success, navigate to main fragment
        Toast.makeText(this, "Welcome Back", Toast.LENGTH_LONG).show();
        startActivity(new Intent(LogInActivity.this, MainActivity.class));
        finish();


    }

}
