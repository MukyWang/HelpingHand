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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.muk.helpinghand.MainActivity;
import com.muk.helpinghand.R;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Zelta on 31/05/16.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView etREmail, etRPassword,etRRPassword,tvRLogin;
    private Button btRRegister;
    private Firebase firebase;

    final static String DB_URL = "https://handupdb.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Register");
        setContentView(R.layout.activity_register);
        etREmail = (TextView) findViewById(R.id.etREmail);
        etRPassword = (TextView) findViewById(R.id.etRPassword);
        etRRPassword = (TextView) findViewById(R.id.etRRPassword);
        tvRLogin = (TextView) findViewById(R.id.tvRLogin);
        btRRegister = (Button) findViewById(R.id.btRRegister);
        //get firebase instance
        Firebase.setAndroidContext(this);
        //Set on click listener for button and textview
        btRRegister.setOnClickListener(this);
        tvRLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //When register button click
            case R.id.btRRegister:
                //validate the two passwords should be the same
                if (validateSamePwd(etRRPassword.getText().toString(), etRPassword.getText().toString())){
                    //validate the length of password
                    if (validatePwdLength(etRRPassword.getText().toString()) && validatePwdLength(etRPassword.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Password should be 6 - 15 characters", Toast.LENGTH_LONG).show();
                    } else {
                        String email = etREmail.getText().toString();
                        String pwd = etRPassword.getText().toString();
                        firebase = new Firebase(DB_URL);
                        //Create new user with valid email and password
                        firebase.createUser(email, pwd, new Firebase.ResultHandler() {
                            @Override
                            public void onSuccess() {
                                //When create successful, navigate to login activity
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                            }
                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast toast = Toast.makeText(RegisterActivity.this, firebaseError.toString(), Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0 ,0);
                                toast.show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please confirm your passwords", Toast.LENGTH_LONG).show();
                }
                break;
            //when user click, navigate to login activity
            case R.id.tvRLogin:
                startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                break;
        }

    }

    //validate the same string
    public boolean validateSamePwd (String pwd, String pwd1){
        if (pwd.equals(pwd1))
            return true;
        return false;
    }

    //validate the length of string
    public boolean validatePwdLength(String pwd){
        if (pwd.length()<6 || pwd.length()>15)
            return true;
        return false;
    }

}
