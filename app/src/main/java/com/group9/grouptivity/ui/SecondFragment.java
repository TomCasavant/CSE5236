package com.group9.grouptivity.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.common.api.ApiException;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.DataRetrievalListener;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.firebase.models.ActivityPollMessage;
import com.group9.grouptivity.firebase.models.GroupActivity;
import com.group9.grouptivity.firebase.models.GroupMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondFragment extends Fragment
{
    MapView mMapView;
    private GoogleMap googleMap;
    private static String PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&maxheight=100&&photoreference=%s&key=%s";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Initialize the SDK
        Places.initialize(getActivity().getApplicationContext(), "AIzaSyAepdUXTrbdi5C1yUygQ1Nf0ksyYbXAO6w");

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(getContext());
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
                        // Define a Place ID.
                        String placeId = poi.placeId;

                        // Specify the fields to return.
                        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.TYPES);

                        // Construct a request object, passing the place ID and fields array.
                        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

                        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                            Place place = response.getPlace();
                            createShareDialog(place);
                            Log.i("MAPPING: ", "Place found: " + place.getName());
                        }).addOnFailureListener((exception) -> {
                                Log.e("MAPPING: ", "Place not found: " + exception);
                        });
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

    /** Creates a dialog box and displays it to the user to share to a group */
    public void createShareDialog(Place place){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.share_activity_dialog); // Assign to xml dialog layout
        TextView type = (TextView) dialog.findViewById(R.id.place_type);
        TextView name = (TextView) dialog.findViewById(R.id.place_name);
        TextView address = (TextView) dialog.findViewById(R.id.place_address);
        ImageView icon = dialog.findViewById(R.id.icon);
        final Spinner spinner = (Spinner) dialog.findViewById(R.id.group_selector);

        // Get Values
        String type_str = place.getTypes().get(1).toString();
        String name_str = place.getName();
        String address_str = place.getAddress();
        // Assign values
        type.setText(type_str);
        name.setText(name_str);
        address.setText(address_str);
        //String meta = place.getPhotoMetadatas().get(0).zza();
        //String height = String.valueOf(place.getPhotoMetadatas().get(0).getHeight());
        //String width = String.valueOf(place.getPhotoMetadatas().get(0).getWidth());
        String url = "https://developers.google.com/maps/documentation/places/web-service/icons/restaurant-71.png";
        Log.d("Mappings:", url);
        Picasso.get().load(url).into(icon);
        List<GroupMessage> groups = FirebaseRTDBHelper.getInstance().getCurrentUser().getGroups();
        List<String> groupNames = new ArrayList<>();
        List<String> groupIds = new ArrayList<>();
        for (GroupMessage group : groups){
            Log.d("Groups:", group.getName());
            groupNames.add(group.getName());
            groupIds.add(group.retrieveKey());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, groupNames);
        spinner.setAdapter(dataAdapter);
        // Create button handlers */
        Button shareButton = (Button) dialog.findViewById(R.id.dialogButtonShare);
        Button cancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        shareButton.setOnClickListener((View v) -> {
            // Create new Activity
            String groupId = groupIds.get(groupNames.indexOf(String.valueOf(spinner.getSelectedItem())));
            GroupActivity activity = new GroupActivity(type_str, address_str, url, name_str);
            FirebaseRTDBHelper.getInstance().addMessage(activity, groupId);
            dialog.dismiss();
        });
        cancelButton.setOnClickListener((View v) ->
                dialog.dismiss()
        );

        dialog.show();
    }

}