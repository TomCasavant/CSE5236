package com.group9.grouptivity.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group9.grouptivity.R;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (FirebaseRTDBHelper.getInstance().isLoggedIn()){
            ((Button)toolbar.findViewById(R.id.loginButton)).setText("Logout");
        } else {
            ((Button)toolbar.findViewById(R.id.loginButton)).setText("Login");
        }
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    // User is logged in, update accordingly
                    ((Button)toolbar.findViewById(R.id.loginButton)).setText("Logout");
                } else {
                    // User is not logged in update accordingly
                    ((Button)toolbar.findViewById(R.id.loginButton)).setText("Login");
                }
            }
        };
        FirebaseRTDBHelper.getInstance().createListener(mAuthListener);
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        Log.d("OnCreate()", "Successfully ran Activity OnCreate()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("OnStart()", "Successfully ran Activity OnStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("OnPause()", "Successfully ran Activity OnPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("OnResume()", "Successfully ran Activity OnResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("OnStop()", "Successfully ran Activity OnStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("OnDestroy()", "Successfully ran Activity OnDestroy()");
    }
}