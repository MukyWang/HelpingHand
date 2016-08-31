package com.muk.helpinghand.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.muk.helpinghand.R;
import com.muk.helpinghand.activity.LogInActivity;

/**
 * Created by Zelta on 6/06/16.
 */
public class ChangePwdFragment extends Fragment implements View.OnClickListener {
    final static String DB_URL = "https://handupdb.firebaseio.com/homelessinfo";
    private Firebase firebase;
    private TextView etCPwd;
    private TextView etCNPwd;
    private TextView etCNCPwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.change_pwd_layout, container, false);
        getActivity().setTitle("Change Password");
        Firebase.setAndroidContext(getContext());
        //get firebase instance
        firebase = new Firebase(DB_URL);

        TextView tvAccount = (TextView) myView.findViewById(R.id.tvAccount);
        etCPwd = (TextView) myView.findViewById(R.id.etCPwd);
        etCNPwd = (TextView) myView.findViewById(R.id.etCNPwd);
        etCNCPwd = (TextView) myView.findViewById(R.id.etCNCPwd);
        Button btChange = (Button) myView.findViewById(R.id.btChange);

        //Current user email
        tvAccount.setText(LogInActivity.uEmail);
        //Set on click listener for change password button
        btChange.setOnClickListener(this);

        return myView;

    }

    @Override
    public void onClick(View v) {
        String cpwd = etCPwd.getText().toString();
        String cnpwd = etCNPwd.getText().toString();
        String cncpwd = etCNCPwd.getText().toString();
        //validate the two passwords should be the same
        if (validateSamePwd(cncpwd,cnpwd)){
            //validate the length of password
            if (validatePwdLength(cncpwd)){
                Toast.makeText(getContext(), "Password should be 6 - 15 characters", Toast.LENGTH_LONG).show();
            } else {
                //Change password for current user
                firebase.changePassword(LogInActivity.uEmail, cpwd, cnpwd, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        //When success, navigate to login activity
                        Toast.makeText(getContext(), "Reset Success Please Log In Again", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), LogInActivity.class));

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast toast = Toast.makeText(getContext(), firebaseError.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0 ,0);
                        toast.show();

                    }
                });
            }

        } else {
            Toast.makeText(getContext(), "Please confirm your passwords", Toast.LENGTH_LONG).show();
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
