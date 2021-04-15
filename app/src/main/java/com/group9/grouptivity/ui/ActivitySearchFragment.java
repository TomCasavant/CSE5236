package com.group9.grouptivity.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.navigation.NavigationView;
import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;
import com.group9.grouptivity.firebase.models.GroupActivity;
import com.group9.grouptivity.firebase.models.GroupMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivitySearchFragment extends Fragment {
    private MapView mMapView;
    private DrawerLayout mDrawerLayout;
    private GoogleMap googleMap;
    private static String PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&maxheight=100&&photoreference=%s&key=%s";
    private boolean mLocationPermissionGranted = false;
    private static final int PERMISSION_REQUEST_CODE = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "ActivitySearchFragment";


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Initialize the SDK
        Places.initialize(getActivity().getApplicationContext(), "AIzaSyAepdUXTrbdi5C1yUygQ1Nf0ksyYbXAO6w");

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(getContext());
        View view = inflater.inflate(R.layout.fragment_activity_search, container, false);

        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }

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
                if (mLocationPermissionGranted) {
                    getDeviceLocation();
                    if (ActivityCompat.checkSelfPermission(ActivitySearchFragment.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivitySearchFragment.this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }  else {
                    ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }

    private void updateCurrentLoginInfo(View view){
        // Checks if user is logged in, if not send user to login page
        view.getRootView().findViewById(R.id.loginButton).setOnClickListener((View v) -> {
            if (!FirebaseRTDBHelper.getInstance().isLoggedIn()){
                NavHostFragment.findNavController(ActivitySearchFragment.this)
                        .navigate(R.id.loginFragment);
            } else {
                FirebaseRTDBHelper.getInstance().logout();
                NavHostFragment.findNavController(ActivitySearchFragment.this)
                        .navigate(R.id.loginFragment);
            }
        });
        if (!FirebaseRTDBHelper.getInstance().isLoggedIn()){
            NavHostFragment.findNavController(ActivitySearchFragment.this)
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

        mDrawerLayout = view.findViewById(R.id.main_drawerLayout);
        setupMainNavigationView(view);
        updateCurrentLoginInfo(view);
    }

    private void getDeviceLocation() {
        Log.d(TAG,"getDeviceLocation: getting the device's current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

        try{
            if(mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLatitude()), 15f);
                        } else{
                            Log.d(TAG, "onComplete: location is null");
                            Toast.makeText(ActivitySearchFragment.this.getContext(), "Unable to get current location.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }

    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    /** Sets up the main navigation view for this fragment. */
    private void setupMainNavigationView(View view) {
        NavigationView navigationView = view.findViewById(R.id.main_navView);
        View mainNavViewHeader = navigationView.getHeaderView(0); //Should only be one header view
        TextView headerDisplayNameTextView = mainNavViewHeader.findViewById(R.id.main_navview_header_displayName);
        if (FirebaseRTDBHelper.getInstance().isLoggedIn()) {
            headerDisplayNameTextView.setText(FirebaseRTDBHelper.getInstance().getCurrentUser().getDisplayName());
        }

        //Need to find a way to use id and not int literal
        navigationView.getMenu().getItem(1).setChecked(true); //Set activities menu item to checked

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.main_navview_groups_item):
                    NavHostFragment.findNavController(ActivitySearchFragment.this).navigate(R.id.action_ActivitySearchFragment_to_GroupsFragment);
                    break;
                case (R.id.main_navview_activities_item):
                    //Do nothing. We are already here
                    break;
                case (R.id.main_navview_invites_item):
                    NavHostFragment.findNavController(ActivitySearchFragment.this).navigate(R.id.action_ActivitySearchFragment_to_GroupInvitesFragment);
                    break;
                default:
                    //error?
                    break;
            }
            mDrawerLayout.closeDrawers();
            return false; //don't change the current item in case the user comes back
        });
    }

    /** Creates a dialog box and displays it to the user to share to a group */
    private void createShareDialog(Place place){
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
        String url = getIconUrl(type_str);
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
            String selected_group = String.valueOf(spinner.getSelectedItem());
            String groupId = groupIds.get(groupNames.indexOf(selected_group));
            GroupActivity activity = new GroupActivity(type_str, address_str, url, name_str);
            FirebaseRTDBHelper.getInstance().addMessage(activity, groupId);
            Toast.makeText(getActivity(), String.format("Shared %s to %s", name_str, selected_group), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        cancelButton.setOnClickListener((View v) ->
                dialog.dismiss()
        );

        dialog.show();
    }

    private String getIconUrl(String type){
        String url;
        switch(type){
            case "AIRPORT":
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/airport-71.png";
                break;
            case "AQUARIUM":
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/v1/harbor-71.png";
                break;
            case "RESTAURANT":
            case "FOOD":
            case "BAKERY":
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/restaurant-71.png";
                break;
            case "ART_GALLERY":
            case "BOOK_STORE":
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/v1/library-71.png";
                break;
            case "AMUSEMENT_PARK":
            case "BOWLING_ALLEY":
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/golf-71.png";
                break;
            case "CAFE":
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/cafe-71.png";
                break;
            case "PARK":
            case "CAMPGROUND":
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/cemetery_grave-71.png";
                break;
            case "BANK":
            case "CASINO":
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/bank_dollar-71.png";
                break;
            case "GROCERY_OR_SUPERMARKET":
            case "CLOTHING_STORE":
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/v1/shopping-71.png";
                break;
            case "MOVIE_THEATER":
            case "MOVIE_RENTAL":
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/movies-71.png";
                break;
            default:
                url = "https://developers.google.com/maps/documentation/places/web-service/icons/v1/geocode-71.png";
                break;
        }

        return url;
    }

}