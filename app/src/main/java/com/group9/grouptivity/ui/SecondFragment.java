package com.group9.grouptivity.ui;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;

public class SecondFragment extends Fragment
{
    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
                    @Override
                    public void onPoiClick(PointOfInterest poi) {
                        //open fragment for voting

                    }
                });
            }
        });
        return view;
    }


    private void updateCurrentLoginInfo(View view){
        // Checks if user is logged in, if not send user to login page
        view.getRootView().findViewById(R.id.loginButton).setOnClickListener((View v) -> {
            if (!FirebaseRTDBHelper.getInstance().isLoggedIn()){
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.loginFragment);
            } else {
                FirebaseRTDBHelper.getInstance().logout();
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.loginFragment);
            }
        });
        if (!FirebaseRTDBHelper.getInstance().isLoggedIn()){
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.loginFragment);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.activities_fragment_groups_button).setOnClickListener( (View v) ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );
        updateCurrentLoginInfo(view);
    }


}