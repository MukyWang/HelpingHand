package com.muk.helpinghand.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.muk.helpinghand.MainActivity;
import com.muk.helpinghand.R;
import com.muk.helpinghand.activity.ViewInfoActivity;
import com.muk.helpinghand.activity.ViewServiceActivity;


/**
 * Created by Zelta on 1/05/16.
 */
public class HandyFeedFragment extends Fragment{


    private View myView;
    private TextView foodresource,shelter,medical,legal,mental,other;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.handyfeed_layout, container, false);
        getActivity().setTitle("Handy Feed");

        foodresource = (TextView) myView.findViewById(R.id.FoodResource);
        shelter = (TextView) myView.findViewById(R.id.Shelter);
        medical = (TextView) myView.findViewById(R.id.Medical);
        legal = (TextView) myView.findViewById(R.id.Legal);
        mental = (TextView) myView.findViewById(R.id.Mental);
        other = (TextView) myView.findViewById(R.id.Other);


        //Set on click listener for each catalogue
        foodresource.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ViewServiceActivity.class);
                //Pass catalogue into intent
                String x = "FoodResources";
                intent.putExtra("firebaseurl",x);
                startActivity(intent);

            }
        });

        shelter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ViewServiceActivity.class);
                String x = "Shelter";
                intent.putExtra("firebaseurl", x);
                startActivity(intent);

            }
        });

        medical.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ViewServiceActivity.class);
                String x = "MedicalCare";
                intent.putExtra("firebaseurl",x);
                startActivity(intent);

            }
        });

        legal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ViewServiceActivity.class);
                String x = "LegalService";
                intent.putExtra("firebaseurl", x);
                startActivity(intent);

            }
        });

        mental.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ViewServiceActivity.class);
                String x = "MentalSupport";
                intent.putExtra("firebaseurl",x);
                startActivity(intent);

            }
        });

        other.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ViewServiceActivity.class);
                String x = "Others";
                intent.putExtra("firebaseurl", x);
                startActivity(intent);

            }
        });

        return myView;
    }

}
