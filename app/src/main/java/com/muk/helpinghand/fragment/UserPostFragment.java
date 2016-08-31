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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.muk.helpinghand.R;
import com.muk.helpinghand.activity.LogInActivity;
import com.muk.helpinghand.activity.ViewInfoActivity;
import com.muk.helpinghand.adapter.InfoAdapter;
import com.muk.helpinghand.data.HomelessInfo;

import java.util.ArrayList;

/**
 * Created by Zelta on 31/05/16.
 */
public class UserPostFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    final static String DB_URL = "https://handupdb.firebaseio.com/homelessinfo";
    private Firebase firebase;
    private ArrayList<HomelessInfo> homelessList;
    private InfoAdapter infoAdapter;
    private ListView lv;
    private View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.main_fragment, container, false);
        //Initialize when create
        Firebase.setAndroidContext(getContext());
        //get firebase instance
        firebase = new Firebase(DB_URL);
        //Get the listview associated with layout and setup adapter
        lv = (ListView) myView.findViewById(R.id.listView);
        homelessList = new ArrayList<>();
        infoAdapter = new InfoAdapter(getContext(),homelessList);

        //Set up on item click listener for listview
        lv.setOnItemClickListener(this);
        //Set up delete function as on long item click for listview
        lv.setOnItemLongClickListener(this);
        //Retrieve all current user post
        retrieveUserPost();

        return myView;
    }

    //When item on list view clicked, navigate to ViewInfoActivity
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),ViewInfoActivity.class);
        HomelessInfo homelessInfo = homelessList.get(position);
        intent.putExtra("select", homelessInfo);
        startActivity(intent);
    }

    //Delete function for list
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //Get current click item
        final HomelessInfo homelessInfo = homelessList.get(position);
        //Pop up warning dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Delete this information?");
        builder.setMessage("Are you sure you want to delete this?");
        //When select yes, delect current post
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot ds) {
                                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                                    //Retrieve data from database
                                    HomelessInfo homelessInfo1 = dataSnapshot.getValue(HomelessInfo.class);
                                    //Get the current post data
                                    if(homelessInfo.getLocation().equals(homelessInfo1.getLocation()) && homelessInfo.getUid().equals(homelessInfo1.getUid()) &&
                                            homelessInfo.getNeed().equals(homelessInfo1.getNeed()) && homelessInfo.getNote().equals(homelessInfo1.getNote())){
                                        //Delete item from firebase
                                        firebase.child(dataSnapshot.getKey()).removeValue();
                                        //Update list and adapter
                                        homelessList.clear();
                                        infoAdapter.notifyDataSetChanged();
                                        Toast.makeText(getContext(), "Delete Success", Toast.LENGTH_LONG).show();
                                    }
                                }
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
        );
        //When cancel selecdt, dismiss dialog
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Close the dialog
                        dialogInterface.cancel();
                    }
                }
        );

        builder.create().show();
        return true;
    }

    //Retrieve all current user post from firebase
    private void retrieveUserPost() {

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                    for (DataSnapshot dataSnapshot : ds.getChildren()) {
                        String uid = dataSnapshot.child("uid").getValue().toString();
                        if (uid.equals(LogInActivity.uid)) {
                            //Get data from firebase
                            String need = dataSnapshot.child("need").getValue().toString();
                            String location = dataSnapshot.child("location").getValue().toString();
                            String note = dataSnapshot.child("note").getValue().toString();
                            Double latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                            Double longitude = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());
                            //Save intense
                            HomelessInfo homelessInfo = new HomelessInfo(LogInActivity.uid, location, need, note, latitude, longitude);
                            //Add into array list
                            homelessList.add(homelessInfo);
                        }
                        //list view set adapter
                        lv.setAdapter(infoAdapter);
                    }


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
