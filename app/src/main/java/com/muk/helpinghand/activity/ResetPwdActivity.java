package com.muk.helpinghand.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.muk.helpinghand.R;

/**
 * Created by Zelta on 6/06/16.
 */
public class ResetPwdActivity extends AppCompatActivity {
    private TextView etReset,tvBack;
    private Button btReset;
    private Firebase firebase;

    final static String DB_URL = "https://handupdb.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Reset Password");
        setContentView(R.layout.activity_reset);
        etReset = (TextView) findViewById(R.id.etResetEmail);
        tvBack = (TextView) findViewById(R.id.tvBackToLogIn);
        btReset = (Button) findViewById(R.id.btReset);


        Firebase.setAndroidContext(this);

        //when reset passsword button click
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase = new Firebase(DB_URL);
                String email = etReset.getText().toString();
                //Reset password with valid email
                firebase.resetPassword(email, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        //After reset new password to email, navigate to login page
                        Toast.makeText(ResetPwdActivity.this,"New Password Has Sent To Your Email", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ResetPwdActivity.this, LogInActivity.class));
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast toast = Toast.makeText(ResetPwdActivity.this, firebaseError.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0 ,0);
                        toast.show();
                    }
                });
            }
        });

        //when login button click, navigate to login page
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResetPwdActivity.this,"Please Log In Again", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ResetPwdActivity.this, LogInActivity.class));
            }
        });



    }
}
