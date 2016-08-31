package com.muk.helpinghand.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.muk.helpinghand.MainActivity;
import com.muk.helpinghand.activity.LogInActivity;
import com.muk.helpinghand.adapter.InfoAdapter;
import com.muk.helpinghand.R;
import com.muk.helpinghand.activity.ViewInfoActivity;
import com.muk.helpinghand.data.HomelessInfo;

import java.util.ArrayList;

/**
 * Created by Zelta on 1/05/16.
 */
public class MainFragment extends Fragment implements
        AdapterView.OnItemClickListener{

    final static String DB_URL = "https://handupdb.firebaseio.com/homelessinfo";
    private Firebase firebase;
    private ArrayList<HomelessInfo> homelessList;
    private InfoAdapter infoAdapter;
    private ListView lv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.main_fragment, container, false);
        //Initialize when create
        Firebase.setAndroidContext(getContext());
        //get firebase instance
        firebase = new Firebase(DB_URL);

        lv = (ListView) myView.findViewById(R.id.listView);
        //Get the listview associated with layout and setup adapter
        homelessList = new ArrayList<>();
        infoAdapter = new InfoAdapter(getContext(),homelessList);

        lv.setOnItemClickListener(this);
        //retrieve all data from firebase
        retrieveAll();

        return myView;
    }

    //When item on list view clicked, navigate to ViewInfoActivity
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),ViewInfoActivity.class);
        //pass homeless information into intent
        HomelessInfo homelessInfo = homelessList.get(position);
        intent.putExtra("select", homelessInfo);
        //Navigate to View info activity
        startActivity(intent);
    }

    //Retrieve all data from
    private void retrieveAll() {
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot: ds.getChildren()){
                    //Get data from firebase
                    String need  = dataSnapshot.child("need").getValue().toString();
                    String location = dataSnapshot.child("location").getValue().toString();
                    String note = dataSnapshot.child("note").getValue().toString();
                    Double latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                    Double longitude = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());
                    //Save intense
                    HomelessInfo homelessInfo = new HomelessInfo(LogInActivity.uid,location,need,note,latitude,longitude);
                    //Add into array list
                    homelessList.add(homelessInfo);
                }
                //list view set adapter
                lv.setAdapter(infoAdapter);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast toast = Toast.makeText(getContext(), firebaseError.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0 ,0);
                toast.show();

            }
        });
    }

}

