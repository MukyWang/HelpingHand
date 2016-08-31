package com.muk.helpinghand.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.muk.helpinghand.R;
import com.muk.helpinghand.activity.LogInActivity;
import com.muk.helpinghand.activity.ViewInfoActivity;
import com.muk.helpinghand.data.HomelessInfo;

/**
 * Created by Zelta on 1/05/16.
 */
public class HandUpFragment extends Fragment implements LocationListener,View.OnClickListener{


    final static String DB_URL = "https://handupdb.firebaseio.com/homelessinfo";
    String location,need,note;
    double longitude,latitude;
    EditText etLocation, etNeed, etNote;
    Button btSubmit,btLocation;
    Firebase firebase;
    private static final String[] LOCATION_PERMS={Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int INITIAL_REQUEST=3;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+1;

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.handup_layout, container, false);
        getActivity().setTitle("Hand Up");

        //Initialize when create
        Firebase.setAndroidContext(this.getActivity());

        etLocation = (EditText) myView.findViewById(R.id.etLocation);
        etNeed = (EditText) myView.findViewById(R.id.etNeed);
        etNote = (EditText) myView.findViewById(R.id.etNote);
        btSubmit = (Button) myView.findViewById(R.id.btSubmit);
        btLocation = (Button) myView.findViewById(R.id.btLocation);

        //get firebase instance
        firebase = new Firebase(DB_URL);

        //Set submit button on click listener
        btSubmit.setOnClickListener(this);
        //Set get current location button on click lister
        btLocation.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            //When submit button click
            case R.id.btSubmit:
                location = etLocation.getText().toString();
                need = etNeed.getText().toString();
                note = etNote.getText().toString();
                //Validate input can not be empty
                if (validateUserInput(location)|| validateUserInput(need)||validateUserInput(note)) {
                    alertDialog("Please fill in every fields :) ");
                } else if(validateLocation(latitude) && validateLocation(longitude)) //validate current location
                {
                    alertDialog("Please get your current location");
                }else {
                    //Save instance
                    HomelessInfo h = new HomelessInfo(LogInActivity.uid,location,need,note,latitude,longitude);
                    //Save value into firebase
                    firebase.push().setValue(h);
                    Toast.makeText(getContext(), "Thanks for your help :) ", Toast.LENGTH_SHORT).show();// display toast
                    cleanFields();
                    //Get intent and pass value
                    Intent intent = new Intent(getActivity(),ViewInfoActivity.class);
                    intent.putExtra("select", h);
                    startActivity(intent);
                }
                break;

            case R.id.btLocation:
                //Permission check
                if (ContextCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            android.Manifest.permission.READ_CONTACTS)) {
                    } else {
                        //Request the permission
                        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
                        requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
                        //Set up location manager to get location
                        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, HandUpFragment.this);
                    }
                }

                break;
        }
    }

    //Get current location
    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        Toast.makeText(getContext(), "Located Success", Toast.LENGTH_LONG).show();
        Log.v("HandUpFragment", "Latitude:" + latitude + " Longitude:" + longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    //Validate input can not be empty
    public boolean validateLocation(Double coordinate){
        if (coordinate == 0){
            return true;
        }
        return false;
    }

    //Validate input can not be empty
    public boolean validateUserInput(String input){
        if (input == null){
            return true;
        } else if (input.trim().equals("")){
            return true;
        } else
            return false;
    }


    private void cleanFields(){

        etLocation.setText("");
        etNeed.setText("");
        etNote.setText("");

    }

    //Pop up warning dialog
    private void alertDialog(String output){

        AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
        builder.setTitle("Hand Up");
        builder.setMessage(output);
        builder.setButton(AlertDialog.BUTTON_NEUTRAL, "Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

}
