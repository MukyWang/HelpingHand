package com.muk.helpinghand.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.muk.helpinghand.MainActivity;
import com.muk.helpinghand.R;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import 	com.google.android.gms.location.LocationServices;
import com.muk.helpinghand.activity.ViewInfoActivity;
import com.muk.helpinghand.activity.ViewServiceActivity;
import com.muk.helpinghand.adapter.InfoAdapter;
import com.muk.helpinghand.data.HomelessInfo;

import java.util.ArrayList;

/**
 * Created by Zelta on 1/05/16.
 */
public class TakeHandFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    View myView;
    GoogleApiClient mGoogleApiClient;

    GoogleMap mGoogleMap;
    LocationRequest mLocationRequest;

    final static String DB_URL = "https://handupdb.firebaseio.com/homelessinfo";
    private Firebase firebase;

    private ArrayList<HomelessInfo> homelessList;
    private InfoAdapter infoAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.takehand_layout, container, false);
        getActivity().setTitle("Take My Hand");

        homelessList = new ArrayList<>();
        infoAdapter = new InfoAdapter(getContext(),homelessList);

        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment mapFragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();

        }

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.v("TakeHandFragment", "Google client connected.");
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } catch (SecurityException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }



    @Override
    public void onMapReady(GoogleMap googleMap){
        mGoogleMap = googleMap;
        Log.v("TakeHandFragment", "Initialized Googlemap view..");
        LatLng googleMapLocation = new LatLng(-37.8140000, 144.9633200);
        //move camera and level
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(googleMapLocation, 15));
        mGoogleMap.addMarker(new MarkerOptions().title("Hello").position(googleMapLocation));

        //Get firebase instance
        Firebase.setAndroidContext(getContext());
        firebase = new Firebase(DB_URL);

        //Retrieve latitude and longitude from each post on firebase and add marker on map
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    Double latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                    Double longitude = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());
                    LatLng googleMapLocations = new LatLng(latitude, longitude);
                    mGoogleMap.addMarker(new MarkerOptions().title(String.valueOf(latitude)).snippet(String.valueOf(longitude)).position(googleMapLocations));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast toast = Toast.makeText(getContext(), firebaseError.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0 ,0);
                toast.show();
            }
        });

        //When marker click, navigate to view info activity and pass homelessInfo
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {

                firebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        //Retrieve data from firebase to get clicked marker
                        for (DataSnapshot dataSnapshot : ds.getChildren()) {
                            HomelessInfo homelessInfo = dataSnapshot.getValue(HomelessInfo.class);
                            if (String.valueOf(homelessInfo.getLatitude()).equals(marker.getTitle()) && String.valueOf(homelessInfo.getLongitude()).equals(marker.getSnippet())){
                                //Set up intent and pass homelessInfo
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), ViewInfoActivity.class);
                                intent.putExtra("select", homelessInfo);
                                startActivity(intent);
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
                return false;
            }
        });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("TakeHandFragment", "Location changed. Lat: " + location.getLatitude() + ", lng: " + location.getLongitude());
        if (mGoogleMap != null) {
            Log.v("TakeHandFragment", "Google map instance available for updating...");
            LatLng googleMapLocation = new LatLng(location.getLatitude(), location.getLongitude());
            //Move camera and level
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(googleMapLocation, 15));
            mGoogleMap.addMarker(new MarkerOptions().title("New").position(googleMapLocation));
        }
    }
}
